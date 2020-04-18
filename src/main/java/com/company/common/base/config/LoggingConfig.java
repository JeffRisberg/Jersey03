package com.company.common.base.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.event.ConfigurationListener;

import java.util.Optional;

/**
 * Logback Logging Settings.
 * <p>
 * We want to be able to make dynamic config changes and will have to add the actual SLF4J logging implementation
 * (Logback) in our compile scope. If we want to open source some of our codes, we will need to break
 * this logging dependency (and possibly config as well) into a separate module and add that module in
 * the runtime scope.
 */
public class LoggingConfig {

    /**
     * The prefix of log level property keys.
     * <p>
     * i.e.:
     * log.level = DEBUG
     * log.level.com.iv = DEBUG
     */
    private static final String LOG_LEVEL_PREFIX = "log.level";

    private static final String LOGSTASH_TCP_DESTINATION_KEY = "LOGSTASH_TCP_DESTINATION";
    private static final String DEFAULT_LOGSTASH_TCP_DESTINATION = "127.0.0.1:4560";

    static final String DISABLE_CONSOLE_LOGGING_KEY = "log.disable-console-logging";

    // sql log configuration
    static final String SQL_LOGGER_NAME = "sqlLog";
    static final String SQL_LOG_APPENDER_NAME = "sqlLog-appender";
    static final String SQL_LOG_APPENDER_FILE = "sql.log";
    static final String SQL_LOG_DEFAULT_MAX_SIZE = "20MB";
    static final String SQL_LOG_CONFIG_MAX_SIZE_KEY = "log.sqlLog.maxSize";

    // adwords log configuration
    static final String ADWORDS_LOGGER_NAME = "com.google.api.ads.adwords.lib.client.AdWordsServiceClient.soapXmlLogger";
    static final String ADWORDS_LOG_APPENDER_NAME = "adwordsLog-appender";
    static final String ADWORDS_LOG_APPENDER_FILE = "adwords.log";
    static final String ADWORDS_LOG_DEFAULT_MAX_SIZE = "20MB";
    static final String ADWORDS_LOG_CONFIG_MAX_SIZE_KEY = "log.adwordsLog.maxSize";

    // global logger references
    //static final Logger ROOT_LOGGER = getLogger(Logger.ROOT_LOGGER_NAME);
    //static final Logger SQL_LOGGER = getLogger(SQL_LOGGER_NAME);
    //static final Logger ADWORDS_LOGGER = getLogger(ADWORDS_LOGGER_NAME);

    public static final String CONSOLE_APPENDER_NAME = "consoleAppender";

    /**
     * Log files location. This code is invoked before AppConfig is loaded, so it has to find things
     * from system property or system environment.
     */

    private static final String LOG_PATH = Optional.ofNullable(System.getProperty("log.path"))
            .orElse(Optional.ofNullable(System.getenv("MESOS_SANDBOX")).orElse("/var/log"));

    /**
     * Listens to config change events and make necessary changes to the logging configuration.
     */
    public static final ConfigurationListener CONFIG_CHANGE_LISTENER = configurationEvent -> {
        String propKey = configurationEvent.getPropertyName();
        /*
        if (propKey.startsWith(LOG_LEVEL_PREFIX)) {
            LoggingConfig.setLogLevel(configurationEvent.getPropertyName(),
                    (String) configurationEvent.getPropertyValue());
        } else if (propKey.equals(SQL_LOG_CONFIG_MAX_SIZE_KEY)) {
            LoggingConfig.setMaxSize(SQL_LOGGER, SQL_LOG_APPENDER_NAME,
                    (String) configurationEvent.getPropertyValue());
        } else if (propKey.equals(ADWORDS_LOG_CONFIG_MAX_SIZE_KEY)) {
            LoggingConfig.setMaxSize(ADWORDS_LOGGER, ADWORDS_LOG_APPENDER_NAME,
                    (String) configurationEvent.getPropertyValue());
        }
        */
    };

    /**
     * This is the method that you want to call at the beginning of your application.
     */
    public static void init() {

    }

    /**
     * Called at the end of the ConfigInitializers to make sure we pick up new log config values that just got loaded.
     *
     * @param configuration where the new log config values are passed in.
     */
    public static void updateLoggingConfig(Configuration configuration) {
        updateLogLevel(configuration);

        if (configuration.containsKey(SQL_LOG_CONFIG_MAX_SIZE_KEY)) {
            //setMaxSize(SQL_LOGGER, SQL_LOG_APPENDER_NAME, configuration.getString(SQL_LOG_CONFIG_MAX_SIZE_KEY));
        }

        if (configuration.containsKey(ADWORDS_LOG_CONFIG_MAX_SIZE_KEY)) {
            //setMaxSize(ADWORDS_LOGGER, ADWORDS_LOG_APPENDER_NAME, configuration.getString(ADWORDS_LOG_CONFIG_MAX_SIZE_KEY));
        }

        if (configuration.containsKey(DISABLE_CONSOLE_LOGGING_KEY)) {
            boolean isConsoleLoggingDisabled = configuration.getBoolean(DISABLE_CONSOLE_LOGGING_KEY, false);

            if (isConsoleLoggingDisabled) {
                //ROOT_LOGGER.detachAppender(CONSOLE_APPENDER_NAME);
            }
        }

    }

    /**
     * Update the log level based on the configuration
     *
     * @param configuration the configuration
     */
    private static void updateLogLevel(Configuration configuration) {
        configuration.getKeys(LOG_LEVEL_PREFIX).forEachRemaining(propertyKey -> {
            String propertyValue = configuration.getString(propertyKey, "");
            if (!propertyValue.isEmpty()) {
                setLogLevel(propertyKey, propertyValue);
            }
        });
    }

    /**
     * Set the log level of the target (extracted from the property key) to the specified level.
     *
     * @param propertyKey the specified property key.
     * @param logLevel    the specified log level.
     */
    static void setLogLevel(String propertyKey, String logLevel) {
        if (!propertyKey.startsWith(LOG_LEVEL_PREFIX)) {
            throw new IllegalArgumentException("the property key must start with " + LOG_LEVEL_PREFIX + " prefix");
        }

        /*
        Level level = Level.toLevel(logLevel);
        if (LOG_LEVEL_PREFIX.equals(propertyKey)) {
            ROOT_LOGGER.setLevel(level);
        } else {
            String className = propertyKey.substring(LOG_LEVEL_PREFIX.length() + 1);
            getLogger(className).setLevel(level);
        }
        */
    }

    /**
     * Set the max size parameter on a logger.
     * <p>
     * Note this method will have no affect if appender does not exist
     *
     * @param logger       the logger to be configured
     * @param appenderName the appender to be adjusted
     * @param maxSize      the maximum size (assuming size based retention)
     */
    /*
    static void setMaxSize(Logger logger, String appenderName, String maxSize) {
        Appender<ILoggingEvent> appender = logger.getAppender(appenderName);
        logger.detachAppender(appenderName);

        if (appender != null && appender instanceof RollingFileAppender) {
            appender.stop();
            RollingFileAppender rfa = (RollingFileAppender) appender;
            TriggeringPolicy policy = rfa.getTriggeringPolicy();

            if (policy != null && policy instanceof SizeBasedTriggeringPolicy) {
                policy.stop();
                FileSize fileSize = FileSize.valueOf(maxSize);
                ((SizeBasedTriggeringPolicy) policy).setMaxFileSize(fileSize);
                policy.start();
            }

            appender.start();
        }

        logger.addAppender(appender);
    }
    */
}

