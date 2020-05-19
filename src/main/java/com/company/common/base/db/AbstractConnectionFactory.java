package com.company.common.base.db;

import com.company.common.base.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

/**
 * Enables the creation of a DataSource from a DatabaseConfig.
 */
abstract class AbstractConnectionFactory {

  /**
   * Hikari hangs when a JVM application tries to open 2 connection pools at exactly the same time.
   * This lock is used to make sure that nasty race condition does not happen.
   */
  private final static Object LOCK = new Object();

  /**
   * Create a new DataSource with the specified db config and Jdbc url.
   *
   * @param config db config
   * @param url Jdbc url
   * @return DataSource.
   */
  protected DataSource createDataSource(final DatabaseConfig config, final String url) {
    final HikariDataSource ds;
    synchronized (LOCK) {
      final HikariConfig hcfg = new HikariConfig();
      hcfg.setDriverClassName(config.getDriverClass());
      hcfg.setJdbcUrl(url);
      hcfg.setUsername(config.getUsername());
      hcfg.setPassword(config.getPassword());
      hcfg.setMinimumIdle(config.getConnectionMin());
      hcfg.setMaximumPoolSize(config.getConnectionMax());
      hcfg.setConnectionTimeout(config.getConnectionTimeoutMs());
      hcfg.setIdleTimeout(config.getConnectionIdleTimeoutMs());
      hcfg.setMaxLifetime(config.getConnectionMaxLifetimeMs());
      hcfg.setConnectionTestQuery(config.getConnectionTestQuery());

      // used to monitor the DataSource
      hcfg.setRegisterMbeans(true);

      ds = new HikariDataSource(hcfg);
      //ShutdownHook.getInstance().addCloseableResource(ds);
    }
    return ds;
  }
}
