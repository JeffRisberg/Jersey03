package com.company.common.base.db;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Create AdHoc DB Connection. You are responsible for managing the connection lifecycle.
 */
public interface ConnectionFactory extends Closeable {
    /**
     * Get the DBType.
     *
     * @return DBType.
     */
    DBType getDBType();

    /**
     * Get or create the connection pool.
     *
     * @return DataSource
     */
    DataSource getOrCreateDataSource();

    /**
     * Get a Connection from the connection pool. The client is responsible for closing the Connection.
     *
     * @return DataSource.
     */
    Connection getConnection() throws SQLException;

    /**
     * Get an un-pooled Connection. The client is responsible for closing the Connection.
     *
     * @return Connection.
     * @throws SQLException
     */
    Connection getUnPooledConnection() throws SQLException;

    /**
     * Get a Connection. If the specified useConnectionPool is true, the Connection will be retrieved
     * from the connection pool (if the connection pool has not been created, it will be created).
     * Otherwise, a fresh un-pooled Connection will be returned.
     *
     * The client is responsible for closing the Connection.
     *
     * @param useConnectionPool if true, the Connection is returned from the connection pool.
     * @return Connection.
     * @throws SQLException
     */
    Connection getConnection(boolean useConnectionPool) throws SQLException;

    /**
     * Close the connection factory.
     */
    void close();
}
