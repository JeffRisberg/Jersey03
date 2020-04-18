package com.company.jersey03.services;

import com.company.common.base.binding.MySQL;
import com.company.common.base.config.DatabaseConfig;
import com.company.common.base.db.ConnectionFactory;
import com.company.common.base.db.MySQLConnectionFactory;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * This object is created through Guice, and is a wrapper for the EntityManagerFactory.
 */
@Slf4j
public class MyEntityManagerFactory {

  private EntityManagerFactory emf;
  private DatabaseConfig databaseConfig;

  @Inject
  MyEntityManagerFactory(@MySQL DatabaseConfig databaseConfig) {
    log.info("Creating MyEntityManagerFactory");

    this.databaseConfig = databaseConfig;

    ConnectionFactory cf = new MySQLConnectionFactory(databaseConfig);
    DataSource dataSource = cf.getOrCreateDataSource();

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.connection.datasource", dataSource);
    this.emf = Persistence.createEntityManagerFactory("jersey03", jpaProperties);
  }

  public EntityManager createEntityManager() {
    return emf.createEntityManager();
  }

  public void close() {
    emf.close();
  }
}
