package com.company.common.base.services.constants;

/**
 *
 */
public class EnvironmentKeys {
  public EnvironmentKeys() {
    // Suppress default constructor for noninstantiability
    throw new UnsupportedOperationException();
  }

  // MySQL configuration environment keys
  public static final String MYSQL_DIALECT = "MYSQL_DIALECT";
  public static final String MYSQL_DRIVER_CLASS = "MYSQL_DRIVER_CLASS";
  public static final String MYSQL_USER = "MYSQL_USER";
  public static final String MYSQL_PASSWORD = "MYSQL_PASSWORD";
  public static final String MYSQL_CONNECTION_MINIMUM_IDLE = "MYSQL_CONNECTION_MINIMUM_IDLE";
  public static final String MYSQL_CONNECTION_MAXIMUM_CONNECTIONS = "MYSQL_CONNECTION_MAXIMUM_CONNECTIONS";
  public static final String MYSQL_CONNECTION_TIMEOUT = "MYSQL_CONNECTION_TIMEOUT";
  public static final String MYSQL_IDLE_TIMEOUT = "MYSQL_IDLE_TIMEOUT";
  public static final String MYSQL_URL = "MYSQL_URL";
  public static final String MYSQL_DEFAULT_DATABASE = "MYSQL_DEFAULT_DATABASE";
  public static final String MYSQL_CONNECTION_MAX_LIFETIME = "MYSQL_CONNECTION_MAX_LIFETIME";
}
