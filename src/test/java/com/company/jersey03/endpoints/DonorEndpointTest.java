package com.company.jersey03.endpoints;

import com.company.jersey03.MainApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

/**
 * @author Jeff Risberg
 * @since 11/02/17
 */
public class DonorEndpointTest {
    private Server server;
    private WebTarget target;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        server = new Server(8080);

        ServletContextHandler sch = new ServletContextHandler(server, "/");
        ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
        jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MainApplication.class.getCanonicalName());
        sch.addServlet(jerseyServletHolder, "/*");

        server.start();

        Client c = ClientBuilder.newClient();

        c.register(JacksonJaxbJsonProvider.class);

        target = c.target("http://localhost:8080/");

        mapper = new ObjectMapper();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testFetchValid() {
        Invocation.Builder invocationBuilder = target.path("donors/1").request().accept(MediaType.APPLICATION_JSON);

        try {
            String responseMsg = invocationBuilder.get(String.class);

            JsonNode root = mapper.readTree(responseMsg);
            String firstName = root.at("/data/firstName").asText();
            assertEquals("John", firstName);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFetchMissing() {
        Invocation.Builder invocationBuilder = target.path("donors/999").request().accept(MediaType.APPLICATION_JSON);

        try {
            String responseMsg = invocationBuilder.get(String.class);

            assertNotNull(responseMsg);

            fail();
        } catch (NotFoundException e) {
            // success
        }
    }

    @Test
    public void testFetchList() {
        Invocation.Builder invocationBuilder = target.path("donors").request().accept(MediaType.APPLICATION_JSON);

        try {
            String responseMsg = invocationBuilder.get(String.class);

            JsonNode root = mapper.readTree(responseMsg);

            String firstName0 = root.at("/data/0/firstName").asText();
            assertEquals("John", firstName0);

            String lastName0 = root.at("/data/0/lastName").asText();
            assertEquals("Smith", lastName0);

            String firstName1 = root.at("/data/1/firstName").asText();
            assertEquals("Bill", firstName1);

            String lastName1 = root.at("/data/1/lastName").asText();
            assertEquals("Jones", lastName1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteValid() {
        Invocation.Builder invocationBuilder = target.path("donors/1").request().accept(MediaType.APPLICATION_JSON);

        try {
            String responseMsg = invocationBuilder.delete(String.class);

            JsonNode root = mapper.readTree(responseMsg);
            String errors = root.at("/errors/0").asText();

            assertEquals("", errors);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteMissing() {
        Invocation.Builder invocationBuilder = target.path("donors/999").request().accept(MediaType.APPLICATION_JSON);

        try {
            invocationBuilder.delete(String.class);

            fail();
        } catch (WebApplicationException wae) {
            Response response = wae.getResponse();

            assertEquals(404, response.getStatus());
        }
    }
}
