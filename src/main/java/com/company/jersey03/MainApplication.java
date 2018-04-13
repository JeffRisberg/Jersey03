package com.company.jersey03;

import com.google.inject.Guice;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

public class MainApplication extends ResourceConfig {
    @Inject
    public MainApplication(ServiceLocator serviceLocator) {

        packages(MainApplication.class.getPackage().getName())
                .register(JacksonFeature.class) // enable Jackson
                .register(ApiListingResource.class)
                .register(SwaggerSerializers.class);

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Guice.createInjector(new MainModule()));

        // This will cause all results to be gzipped.  This example is from
        // http://www.codingpedia.org/ama/how-to-compress-responses-in-java-rest-api-with-gzip-and-jersey/
        //
        // That document also describes how to make gzipping be scoped to specific handlers
        // if needed.
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}