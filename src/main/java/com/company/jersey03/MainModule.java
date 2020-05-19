package com.company.jersey03;

import com.company.common.base.binding.MySQL;
import com.company.common.base.config.AppConfig;
import com.company.common.base.config.DatabaseConfig;
import com.company.common.base.services.config.ArchaiusAppConfig;
import com.company.common.base.services.config.MySQLDatabaseConfig;
import com.company.jersey03.services.*;
import com.company.jersey03.services.DAO.*;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MainModule extends AbstractModule {

  protected void configure() {

    //app configs
    //bind(AppConfig.class).to(EnvironmentBasedAppConfig.class).in(Scopes.SINGLETON);
    bind(AppConfig.class).to(ArchaiusAppConfig.class).in(Scopes.SINGLETON);

    // database configs
    bind(DatabaseConfig.class).annotatedWith(MySQL.class).to(MySQLDatabaseConfig.class)
        .in(Scopes.SINGLETON);

    // entityManagerFactory
    bind(MyEntityManagerFactory.class).in(Scopes.SINGLETON);

    // daos
    bind(ClusterDAO.class).in(Scopes.SINGLETON);
    bind(CharityDAO.class).in(Scopes.SINGLETON);
    bind(DonationDAO.class).in(Scopes.SINGLETON);
    bind(DonorDAO.class).in(Scopes.SINGLETON);

    // services
    bind(HelloService.class).in(Scopes.SINGLETON);
    bind(ClusterService.class).in(Scopes.SINGLETON);
    bind(CharityService.class).in(Scopes.SINGLETON);
    bind(DonationService.class).in(Scopes.SINGLETON);
    bind(DonorService.class).in(Scopes.SINGLETON);
    bind(FieldService.class).in(Scopes.SINGLETON);
    bind(FieldService.class).in(Scopes.SINGLETON);
  }
}
