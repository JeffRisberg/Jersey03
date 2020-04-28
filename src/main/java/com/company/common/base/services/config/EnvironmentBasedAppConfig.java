package com.company.common.base.services.config;

import com.company.common.base.config.AppConfig;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class EnvironmentBasedAppConfig implements AppConfig {

  @Override
  public String getString(String key, String defaultValue) {
    return getAndWarn(key, defaultValue, String::new);
  }

  @Override
  public String getString(String key) {
    return get(key, String::new);
  }

  @Override
  public int getInt(String key, int defaultValue) {
    return getAndWarn(key, defaultValue, Integer::parseInt);
  }

  @Override
  public int getInt(String key) {
    return get(key, Integer::parseInt);
  }

  @Override
  public long getLong(String key, long defaultValue) {
    return getAndWarn(key, defaultValue, Long::parseLong);
  }

  @Override
  public long getLong(String key) {
    return get(key, Long::parseLong);
  }

  @Override
  public double getDouble(String key, double defaultValue) {
    return getAndWarn(key, defaultValue, Double::parseDouble);
  }

  @Override
  public double getDouble(String key) {
    return get(key, Double::parseDouble);
  }

  @Override
  public boolean getBoolean(String key, boolean defaultValue) {
    return getAndWarn(key, defaultValue, Boolean::parseBoolean);
  }

  @Override
  public boolean getBoolean(String key) {
    return get(key, Boolean::parseBoolean);
  }

  @Override
  public Iterator<String> getKeys(String prefix) {
    return System.getenv().keySet().stream().filter(k -> k.startsWith(prefix)).iterator();
  }

  @Override
  public Configuration getUnderlyingConfig() {
    Map<String, String> environmentConfig = System.getenv();
    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
    for (String k : environmentConfig.keySet()) {
      propertiesConfiguration.addProperty(k, environmentConfig.get(k));
    }

    return propertiesConfiguration;
  }

  @Override
  public void setOverrideProperty(String key, Object value) {
    throw new UnsupportedOperationException("setOverrideProperty method is not supported for environment configuration");
  }

  /**
   * As environment variables should be a word consisting only of alphanumeric characters and underscores,
   * and beginning with an alphabetic character or an underscore. This utility is translating our archaius
   * config keys, replacing dots by underscores and putting all the key in uppercase.
   *
   * @param key configuration key
   * @return config key with dots replaced by underscores and in uppercase
   */
  @VisibleForTesting
  String translateKey(String key) {
    String translatedKey;
    if (key.indexOf('.') != -1) {
      translatedKey = key.replace('.', '_').toUpperCase();
    } else {
      translatedKey = key;
    }
    return translatedKey;
  }

  /**
   * Utility to get a value from a specific type for a given key, warning if the default value is used.
   *
   * @param key          Key to retrieve
   * @param defaultValue default value to use when key not defined or conversion error
   * @param converter    function that converts from the string to type T
   * @param <T>          type of the expected value
   * @return a value of type T for the given key
   */
  private <T extends Object> T getAndWarn(String key, T defaultValue, Function<String, T> converter) {
    String value = System.getenv(translateKey(key));
    if (value == null) {
      log.warn("A default config value, <" + defaultValue + "> was provided for <" + key + ">");
      return defaultValue;
    }
    return converter.apply(value);
  }


  /**
   * Utility to get a value from a specific type for a given key, warning if the default value is used.
   *
   * @param key       Key to retrieve
   * @param converter function that converts from the string to type T
   * @param <T>       type of the expected value
   * @return a value of type T for the given key
   */
  private <T extends Object> T get(String key, Function<String, T> converter) {
    log.info("Retrieving value for <" + key + ">");
    return converter.apply(System.getenv(translateKey(key)));
  }
}
