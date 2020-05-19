package com.company.common.base.db;

import com.company.common.base.binding.Postgres;
import com.company.common.base.config.DatabaseConfig;
import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.util.Optional;
import javax.sql.DataSource;

public class PostgresConnectionFactory extends AbstractConnectionFactory implements
    ConnectionFactory {

  private final DatabaseConfig dbConfig;

  /**
   * Initialized when you call start().
   */
  private DataSource dataSource;

  @Inject
  PostgresConnectionFactory(@Postgres DatabaseConfig dbConfig) {
    //super(dataSourceMonitor);
    this.dbConfig = dbConfig;
  }

  /**
   * Get the DBType.
   *
   * @return DBType.
   */
  @Override
  public DBType getDBType() {
    return DBType.POSTGRES;
  }

  /**
   * Get or create the connection pool.
   *
   * @return DataSource
   */
  @Override
  public DataSource getOrCreateDataSource() {
    DataSource localDataSource = dataSource;
    if (localDataSource == null) {
      synchronized (this) {
        localDataSource = dataSource;
        if (localDataSource == null) {
          localDataSource = createDataSource(dbConfig, dbConfig.getUrl());
          dataSource = localDataSource;
        }
      }
    }
    return dataSource;
  }

  /**
   * Get a Postgres Connection from the connection pool. If the connection pool has not been
   * created, it will be created.
   *
   * @return Postgres Connection.
   */
  @Override
  public Connection getConnection() throws SQLException {
    return getConnection(true);
  }

  /**
   * Get an un-pooled Postgres Connection.
   *
   * @return Postgres Connection.
   */
  @Override
  public Connection getUnPooledConnection() throws SQLException {
    return getConnection(false);
  }

  /**
   * Get a Postgres Connection. If the specified useConnectionPool is true, the Connection will be
   * retrieved from the connection pool (if the connection pool has not been created, it will be
   * created). Otherwise, a fresh un-pooled Connection will be returned.
   *
   * @param useConnectionPool if true, the Connection is returned from the connection pool.
   * @return Postgres Connection.
   */
  @Override
  public Connection getConnection(boolean useConnectionPool) throws SQLException {
    if (useConnectionPool) {
      return getOrCreateDataSource().getConnection();
    } else {
      return DriverManager
          .getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }
  }

  /**
   * Close the connection factory.
   */
  @Override
  public void close() {
    Optional.ofNullable(((HikariDataSource) dataSource)).ifPresent(HikariDataSource::close);
  }
}
