package com.company.common.base.config;

import com.netflix.config.*;
import com.netflix.config.sources.URLConfigurationSource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Config initializer that needs to be called at the beginning of your application.
 * Usage: ConfigInitializer.init();
 * <p>
 * <p>
 * JVM parameters:
 * <li>-Darchaius.deployment.environment=test (mandatory)</li>
 * <li>-Dqm.config.external.path=/etc/quanticmind/config (optional)</li>
 * <p>
 * <p>
 * You can also specify any other system property like this:
 * -D${key}=${value}
 * <p>
 * <p>
 * Files that will be loaded:
 * <li>override.properties (can be dynamically updated)</li>
 * <li>server-${environment}.properties (can be dynamically updated)</li>
 * <li>server.properties (can be dynamically updated)</li>
 * <li>application-${environment}.properties</li>
 * <li>application.properties</li>
 * <li>commons-${environment}.properties</li>
 * <li>commons.properties</li>
 * <p>
 * override.properties, server-${environment}.properties & server.properties by default
 * should be placed in /etc/company/config.
 * other properties should be available in the classpath.
 */
public class ConfigInitializer {
  private static final Logger log = LoggerFactory.getLogger(ConfigInitializer.class);
  private static final int OVERRIDE_CONFIG_INITIAL_DELAY_MS = 10000;
  private static final int OVERRIDE_CONFIG_DELAY_MS = 10000;
  public static final String CONFIG_EXTERNAL_PATH_KEY = "qm.config.external.path";

  static {
    // prevent Archaius from doing its default out of the box config
    System.setProperty("archaius.dynamicProperty.disableDefaultConfig", "true");
    // prevent default property splitting behavior.  Unfortunately nothing beside below will work!
    AbstractConfiguration.setDefaultListDelimiter((char) 0);
  }

  private static final ConfigInitializer instance = new ConfigInitializer();

  /**
   * Get the ConfigInitializer instance.
   *
   * @return ConfigInitializer instance.
   */
  public static ConfigInitializer getInstance() {
    return instance;
  }

  /**
   * This method must be called at the beginning of the application before pulling any Config property value.
   */
  public void init() {
    if (ConfigurationManager.isConfigurationInstalled()) {
      return;
    }

    log.info("Initializing configuration..");
    // init logging configuration
    LoggingConfig.init();

    // config from system properties
    final ConcurrentCompositeConfiguration compositeConfig = new ConcurrentCompositeConfiguration();

    compositeConfig.addConfiguration(new ConcurrentMapConfiguration(new SystemConfiguration()));
    ConfigurationManager.install(compositeConfig);

    String environment = Environment.forceGetEnvironment();
    // fallback mechanism
    if (environment == null) {
      log.info("Config Environment not found!! Falling back to 'dev' environment.");
      environment = System.getProperty("environment", "dev");
      ConfigurationManager.getDeploymentContext().setDeploymentEnvironment(environment);
    }
    log.info("Config Environment: " + environment);

    // cascade load properties (x.properties and x-${environment}.properties)
    cascadeLoadConfigFromClassPath("application");
    cascadeLoadConfigFromClassPath("commons");

    // config properties that can be dynamically refreshed
    String externalPath = getConfigExternalPath(compositeConfig);
    loadExternalConfigAtFront(externalPath, "server.properties");
    loadExternalConfigAtFront(externalPath, "server-" + environment + ".properties");
    loadExternalConfigAtFront(externalPath, "override.properties");

    // update log level to reflect config values that just got loaded
    LoggingConfig.updateLoggingConfig(getBackingConfigurationSource());

    log.info("..Configuration Initialized");
  }

  /**
   * Cascade load config files with the specified file name from the classpath, i.e.: if name = "x",
   * this will load "x-${environment}.properties" and "x.properties".
   * These files can't be dynamically updated.
   *
   * @param baseFileName the specified config file name.
   */
  public static void cascadeLoadConfigFromClassPath(String baseFileName) {
    try {
      ConfigurationManager.loadCascadedPropertiesFromResources(baseFileName);
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }

  /**
   * Load a config file with the specified name from the specified path.
   * The external config file can be dynamically updated.
   *
   * @param externalPath the specified path.
   * @param baseFileName the specified config file name.
   */
  public static void loadExternalConfigAtFront(String externalPath, String baseFileName) {
    if (!externalPath.endsWith("/")) {
      externalPath = externalPath + "/";
    }
    if (new File(externalPath + baseFileName).exists()) {
      try {
        final String overridePropertiesFile = "file://" + externalPath + baseFileName;

        loadExternalConfigAtFront(new URL(overridePropertiesFile), baseFileName);
      } catch (Exception e) {
        log.warn(e.getMessage());
      }
    }
  }

  /**
   * Load a config file with the specified name from the specified path.
   * The external config file can be dynamically updated.
   *
   * @param configUrl  the specified path.
   * @param configName the specified config file name.
   */
  public static void loadExternalConfigAtFront(URL configUrl, String configName) {
    try {
      final DynamicConfiguration configOverride = new DynamicConfiguration(
        new URLConfigurationSource(configUrl),
        new FixedDelayPollingScheduler(OVERRIDE_CONFIG_INITIAL_DELAY_MS, OVERRIDE_CONFIG_DELAY_MS, true));
      configOverride.addConfigurationListener(LoggingConfig.CONFIG_CHANGE_LISTENER);

      getBackingConfigurationSource().addConfigurationAtFront(configOverride, configName);
      log.info("Loaded External Config: " + configName);
    } catch (Exception e) {
      log.warn(e.getMessage());
    }
  }

  /**
   * Get the config external path.
   *
   * @return the config external path (where we can find override.properties and credential.properties).
   */
  public static String getConfigExternalPath(Configuration config) {
    String configOverridePath = config.getString(CONFIG_EXTERNAL_PATH_KEY, "/etc/company/config");
    if (!configOverridePath.endsWith("/")) {
      configOverridePath = configOverridePath + "/";
    }
    return configOverridePath;
  }

  public static ConcurrentCompositeConfiguration getBackingConfigurationSource() {
    return (ConcurrentCompositeConfiguration) DynamicPropertyFactory.getBackingConfigurationSource();
  }

  /**
   * Remove all property values from the current Configuration. This does not 'uninstall' Archaius Context
   * (calling ConfigInitializer.init() after clear() will still give you an IllegalStateException).
   * <p>
   * NOTE: This should only be used in test environment.
   */
  public void clear() {
    if (!Environment.isTest()) {
      log.warn("Clearing configuration not in the test environment!!");
    }

    Optional.ofNullable(((Configuration) DynamicPropertyFactory.getBackingConfigurationSource()))
      .ifPresent(c -> {
        c.clear();
        log.info("Cleared config");
      });
  }
}
