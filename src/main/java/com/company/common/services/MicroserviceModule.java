package com.company.common.services;

import com.company.common.services.jersey.Jersey2App;
import com.company.common.services.jersey.ProgramName;
import com.company.common.services.jersey.ResourceList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.List;

/**
 * This class defines all of the dependencies for the microservice.
 */
public abstract class MicroserviceModule extends AbstractModule {
    /**
     * Configure any dependencies for the implementing microservice.
     */
    protected abstract void configureDependencies();

    /**
     * @return A list of Resource (Controller) classes (i.e. those with @Path annotations)
     */
    protected abstract List<Class<?>> resources();

    /**
     * @return The name of this program
     */
    protected abstract String programName();

    @Override
    protected final void configure() {
        configureDependencies();

        for (Class<?> resource : resources()) {
            bind(resource);
        }
    }

    /**
     * This provides Resources (Controllers) to {@link Jersey2App} for registration with Jersey.
     */
    @Provides
    @ResourceList
    public final List<Class<?>> resourceProvider() {
        return resources();
    }

    @Provides
    @ProgramName
    public final String programNameProvider() {
        return programName();
    }
}
