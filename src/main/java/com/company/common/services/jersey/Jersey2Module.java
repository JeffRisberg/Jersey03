package com.company.common.services.jersey;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import org.glassfish.jersey.servlet.ServletContainer;

public class Jersey2Module extends AbstractModule {
  private static final String PATH_GLOB = "/*";

  @Override
  protected final void configure() {
    bind(ObjectMapperContextResolver.class);
    bind(ServletContainer.class).toProvider(ServletContainerProvider.class).in(Scopes.SINGLETON);
    bind(Jersey2App.class);

    install(new ServletModule() {
      @Override
      protected void configureServlets() {
        serve(PATH_GLOB).with(ServletContainer.class);
        //filter(PATH_GLOB).through(MdcFilter.class);
      }
    });
  }

  static final class ServletContainerProvider implements Provider<ServletContainer> {
    private final Jersey2App app;

    @Inject
    ServletContainerProvider(Jersey2App app) {
      this.app = app;
    }

    @Override
    public final ServletContainer get() {
      return new ServletContainer(app);
    }
  }
}
