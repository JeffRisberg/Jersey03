package com.company.common.services.jersey;

import com.google.inject.Injector;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extension.ServiceLocatorGenerator;

/**
 * This wires up Guice with Jersey.  Jersey uses HK2 internally, which is both not what
 * we use for dependency injection, but is also worthless trash.
 */
public class GuiceServiceLocatorGenerator implements ServiceLocatorGenerator {
  private final Injector injector;

  public GuiceServiceLocatorGenerator(Injector injector) {
    this.injector = injector;
  }

  @Override
  public ServiceLocator create(String name, ServiceLocator parent) {
    if (!name.startsWith("__HK2_Generated_")) {
      return null;
    }

    Injector childInjector = injector.createChildInjector(new JerseyGuiceModule(name));

    return childInjector.getInstance(ServiceLocator.class);
  }
}
