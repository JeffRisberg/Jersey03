package com.company.common.base.services.config;

import com.company.common.base.config.DatabaseConfig;

import static com.company.common.base.services.constants.EnvironmentKeys.*;

public class EnvironmentBasedMySQLConfig implements DatabaseConfig {

  private String getFromEnv(String field) {
    String result = System.getenv(field);
    if (result == null) {
      throw new RuntimeException("Environment variable: '" + field + "' does not exist");
    }
    return result;
  }

  @Override
  public String getDialect() {
    return this.getFromEnv(MYSQL_DIALECT);
  }

  @Override
  public String getDriverClass() {
    return this.getFromEnv(MYSQL_DRIVER_CLASS);
  }

  @Override
  public String getUsername() {
    return this.getFromEnv(MYSQL_USER);
  }

  @Override
  public String getPassword() {
    return this.getFromEnv(MYSQL_PASSWORD);
  }

  @Override
  public String getServer() {
    return this.getFromEnv(MYSQL_URL);
  }

  @Override
  public String getDb() {
    String x = this.getFromEnv(MYSQL_DEFAULT_DATABASE);
    return this.getFromEnv(MYSQL_DEFAULT_DATABASE);
  }

  @Override
  public String getUrl() {
    if (this.getDb().equals("")) {
      return this.getFromEnv(MYSQL_URL);
    } else {
      return this.getFromEnv(MYSQL_URL) + "/" + this.getDb();
    }
  }

  @Override
  public int getConnectionMin() {
    String connectionMin = this.getFromEnv(MYSQL_CONNECTION_MINIMUM_IDLE);
    return Integer.parseInt(connectionMin);
  }

  @Override
  public int getConnectionMax() {
    String connectionMax = this.getFromEnv(MYSQL_CONNECTION_MAXIMUM_CONNECTIONS);
    return Integer.parseInt(connectionMax);
  }

  @Override
  public long getConnectionTimeoutMs() {
    String connectionTimeout = this.getFromEnv(MYSQL_CONNECTION_TIMEOUT);
    return Integer.parseInt(connectionTimeout);
  }

  @Override
  public long getConnectionIdleTimeoutMs() {
    String connectionTimeout = this.getFromEnv(MYSQL_IDLE_TIMEOUT);
    return Integer.parseInt(connectionTimeout);
  }

  @Override
  public long getConnectionMaxLifetimeMs() {
    return Long.parseLong(this.getFromEnv(MYSQL_CONNECTION_MAX_LIFETIME));
  }

  @Override
  public String getConnectionTestQuery() {
    return "SELECT 1";
  }
}
