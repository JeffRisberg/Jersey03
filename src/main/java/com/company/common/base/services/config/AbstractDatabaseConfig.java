package com.company.common.base.services.config;

import com.company.common.base.config.AppConfig;
import com.company.common.base.config.DatabaseConfig;

/**
 * Database configuration.  This fetches fields from the AppConfig which is injected.
 */
abstract class AbstractDatabaseConfig implements DatabaseConfig {
  private final AppConfig appConfig;
  private final String prefix;

  AbstractDatabaseConfig(final AppConfig appConfig, final String prefix) {
    this.appConfig = appConfig;
    this.prefix = prefix;
  }

  @Override
  public String getDialect() {
    return appConfig.getString(prefix + ".dialect", null);
  }

  @Override
  public String getDriverClass() {
    return appConfig.getString(prefix + ".driverClass", null);
  }

  @Override
  public String getUsername() {
    return appConfig.getString(prefix + ".username", null);
  }

  @Override
  public String getPassword() {
    return appConfig.getString(prefix + ".password", null);
  }

  @Override
  public String getServer() {
    return appConfig.getString(prefix + ".server", null);
  }

  @Override
  public String getDb() {
    return appConfig.getString(prefix + ".db", null);
  }

  @Override
  public int getConnectionMin() {
    return appConfig.getInt(prefix + ".connection.min", 1);
  }

  @Override
  public int getConnectionMax() {
    return appConfig.getInt(prefix + ".connection.max", 2);
  }

  @Override
  public long getConnectionTimeoutMs() {
    return appConfig.getLong(prefix + ".connection.timeout.ms", 30000);
  }

  @Override
  public long getConnectionIdleTimeoutMs() {
    return appConfig.getLong(prefix + ".connection.idle.timeout.ms", 600000);
  }

  @Override
  public long getConnectionMaxLifetimeMs() {
    return appConfig.getLong(prefix + ".connection.max.lifetime.ms", 1800000);
  }

  @Override
  public String getConnectionTestQuery() {
    return appConfig.getString(prefix + ".connection.test.query", "SELECT 1");
  }
}
