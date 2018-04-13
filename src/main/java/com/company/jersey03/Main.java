package com.company.jersey03;

import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

public class Main {
    public static void main(String[] args) throws Exception {

        ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);

        sch.setContextPath("/");
        Server server = new Server(8080);
        server.setHandler(sch);

        // This makes a servletHolder, configures it, and then adds it to the contextHandler
        ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
        jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MainApplication.class.getCanonicalName());
        jerseyServletHolder.setInitOrder(1);
        sch.addServlet(jerseyServletHolder, "/*");

        // This makes a servletHolder, configures it, and then adds it to the contextHandler
        ServletHolder swaggerServletHolder = new ServletHolder(new DefaultJaxrsConfig());
        swaggerServletHolder.setName("Jersey2Config");
        swaggerServletHolder.setInitParameter("api.version", "1.0.0");
        swaggerServletHolder.setInitParameter("swagger.api.basepath", "http://localhost:8080");
        swaggerServletHolder.setInitOrder(2);

        sch.addServlet(swaggerServletHolder, "/api-docs");

        server.start();
        server.join();
    }
}
