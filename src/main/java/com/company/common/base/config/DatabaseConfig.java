package com.company.common.base.config;

/**
 *
 */
public interface DatabaseConfig {
  /**
   * Hibernate dialect.
   *
   * @return JDBC dialect name.
   */
  String getDialect();

  /**
   * JDBC driver full class name.
   *
   * @return JDBC driver full class name.
   */
  String getDriverClass();

  /**
   * DB user name.
   *
   * @return db user name.
   */
  String getUsername();

  /**
   * DB password.
   *
   * @return db password.
   */
  String getPassword();

  /**
   * Location and port number of the db.
   *
   * @return location and port number of the db.
   */
  String getServer();

  /**
   * Default database name.
   *
   * @return default database name.
   */
  String getDb();

  /**
   * Full JDBC url to connect to the db.
   *
   * @return full JDBC url to connect to the db.
   */
  String getUrl();

  int getConnectionMin();

  int getConnectionMax();

  long getConnectionTimeoutMs();

  long getConnectionIdleTimeoutMs();

  long getConnectionMaxLifetimeMs();

  /**
   * 'Test' query fired periodically by the connection pool to keep the connection open.
   *
   * @return 'Test' query fired periodically by the connection pool to keep the connection open.
   */
  String getConnectionTestQuery();
}
