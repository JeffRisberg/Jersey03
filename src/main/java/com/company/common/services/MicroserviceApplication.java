package com.company.common.services;

import com.company.common.services.jersey.JacksonModule;
import com.company.common.services.jersey.Jersey2Module;
import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class for implementing a microservice.  You must separately extend MicroserviceModule
 * and return the implementing class in MicroserviceModule().
 */
public abstract class MicroserviceApplication {

  private static final Logger logger = LoggerFactory.getLogger(MicroserviceApplication.class);

  /**
   * @return The {@link MicroserviceModule} Guice module (extending MicroserviceModule) for your
   * application
   */
  protected abstract MicroserviceModule qmMicroserviceModule();

  /**
   * The main method for microservices.
   */
  protected final void go(String name, int port) throws Exception {
        /*
        LoggingHelper.setProgramName(name);

        HttpServerWrapperConfig config = new HttpServerWrapperConfig()
                .withAccessLogConfigFileInClasspath("logback.xml")
                .withHttpServerConnectorConfig(HttpServerConnectorConfig.forHttp("0.0.0.0", port));

        Injector injector = Guice.createInjector(new ApplicationModule());
        JerseyGuiceUtils.install(new GuiceServiceLocatorGenerator(injector));

        injector.getInstance(HttpServerWrapperFactory.class)
                .getHttpServerWrapper(config)
                .start();
        logger.info(this.getClass().getName() + " listening on port " + port);
        */
  }

  /**
   * Load the global, shared modules and the app-specific module from {@link
   * #qmMicroserviceModule()}.
   */
  private final class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
      //install(new HttpServerWrapperModule());
      install(new JacksonModule());
      install(new Jersey2Module());

      install(qmMicroserviceModule());
    }
  }

  /**
   * @param portString A string with an integer number
   * @return Integer from given string
   */
  protected static final int parsePort(String portString) {
    if (portString == null) {
      throw new IllegalArgumentException("Must pass a port string");
    }

    int port;
    try {
      port = Integer.parseInt(portString);

      if (port < 1) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      throw new NumberFormatException(String
          .format("PORT environment variable must be a positive integer. \"%s\" was passed in.",
              portString));
    }

    return port;
  }
}
