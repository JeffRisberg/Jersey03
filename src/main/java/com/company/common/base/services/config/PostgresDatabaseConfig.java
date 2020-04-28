package com.company.common.base.services.config;

import com.company.common.base.config.AppConfig;
import com.google.inject.Inject;

/**
 *
 */
public class PostgresDatabaseConfig extends AbstractDatabaseConfig {

  @Inject
  public PostgresDatabaseConfig(AppConfig appConfig) {
    super(appConfig, "postgres");
  }

  @Override
  public String getUrl() {
    return buildPostgresUrl(getServer(), getDb());
  }

  static String buildPostgresUrl(String server, String db) {
    return String.format("jdbc:mysql://%s/%s?zeroDateTimeBehavior=ConvertToNull", server, db);
  }
}
