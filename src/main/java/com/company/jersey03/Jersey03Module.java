package com.company.jersey03;

import com.company.common.services.MicroserviceModule;
import com.company.jersey03.endpoints.Charities;
import com.company.jersey03.endpoints.Donations;
import com.company.jersey03.endpoints.Donors;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Jersey03Module extends MicroserviceModule {

  @Override
  public void configureDependencies() {
      /*
        // configs
        bind(AppConfig.class).to(EnvironmentBasedAppConfig.class).in(Scopes.SINGLETON);
        bind(DatabaseConfig.class).annotatedWith(MySQL.class).to(EnvironmentBasedMySQLConfiguration.class).in(Scopes.SINGLETON);
        bind(DatabaseConfig.class).annotatedWith(RedshiftP.class).to(EnvironmentBasedRedshiftConfiguration.class).in(Scopes.SINGLETON);

        // connection factories
        bind(ConnectionFactory.class).annotatedWith(MySQL.class).to(MySQLConnectionFactory.class).in(Scopes.SINGLETON);
        */
  }

  @Override
  protected String programName() {
    return "jersey2-service";
  }

  @Override
  public List<Class<?>> resources() {
    return Arrays.asList(
      // SystemEndpoint.class  -- this should exist
      Charities.class,
      Donors.class,
      Donations.class
    );
  }

  //@Provides
  //@StartUpDelay
  //int getStartUpDelayInSec() {
  //    return 5;
  //}

  private String getEnvironmentValue(String key) {
    return Optional.ofNullable(System.getenv(key)).orElseThrow(() ->
      new RuntimeException(String.format("Environment variable: '%s' does not exist", key)));
  }
}
