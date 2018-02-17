package com.company.jersey03;

import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ServletContextHandler sch = new ServletContextHandler(server, "/");

        // this makes a servletholder and then adds it to the contexHaandler
        ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
        jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MainApplication.class.getCanonicalName());
        sch.addServlet(jerseyServletHolder, "/*");

        //ServletContextHandler context = new ServletContextHandler(server, "/j3-server", ServletContextHandler.SESSIONS);

        // this makes a servlet within the contextHandler, then configures it.
        //ServletHolder swaggerServlet = sch.addServlet(/*Swagger*/DefaultJaxrsConfig.class, "/swagger-core");
        //swaggerServlet.setInitParameter("api.version", "1.0.0");
        //swaggerServlet.setInitParameter("swagger.api.basepath", "/j3-server");
        //swaggerServlet.setInitOrder(2);

        ServletHolder swaggerServlet = sch.addServlet(DefaultJaxrsConfig.class, "/swagger-core");
        swaggerServlet.setInitParameter("api.version", "1.0.0");
        swaggerServlet.setInitParameter("swagger.api.basepath", "/model-server");
        swaggerServlet.setInitOrder(2);

        //context.addServlet(holder, "/*");
        sch.addServlet(swaggerServlet, "/swagger");

        server.start();
        server.join();
    }
}
