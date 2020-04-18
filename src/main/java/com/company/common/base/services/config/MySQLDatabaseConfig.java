package com.company.common.base.services.config;

import com.company.common.base.config.AppConfig;
import com.google.inject.Inject;

/**
 */
public class MySQLDatabaseConfig extends AbstractDatabaseConfig {

  @Inject
  public MySQLDatabaseConfig(AppConfig appConfig) {
    super(appConfig, "mysql");
  }

  @Override
  public String getUrl() {
    return buildMySQLUrl(getServer(), getDb());
  }

  static String buildMySQLUrl(String server, String db) {
    return String.format("jdbc:mysql://%s/%s?zeroDateTimeBehavior=ConvertToNull", server, db);
  }
}
