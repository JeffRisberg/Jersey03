package com.company.common.services.util;

import com.zaxxer.hikari.HikariConfig;

/**
 */
public class DbUtils {
    public static HikariConfig getHikariConfig(String jdbcUrl,
                                               String driverClassName,
                                               String userName,
                                               String password,
                                               int minIdleConnections,
                                               int maxPoolSize,
                                               long connectionTimeoutMs,
                                               long idleTimeout,
                                               String connectionTestQuery) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.setConnectionTestQuery(connectionTestQuery);

        config.setMinimumIdle(minIdleConnections);
        config.setMaximumPoolSize(maxPoolSize);
        config.setConnectionTimeout(connectionTimeoutMs);
        config.setIdleTimeout(idleTimeout);

        return config;
    }

    public static HikariConfig getHikariConfig(String jdbcUrl,
                                               String driverClassName,
                                               String userName,
                                               String password,
                                               String connectionTestQuery) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.setConnectionTestQuery(connectionTestQuery);

        return config;
    }
}
