package com.company.jersey03.endpoints;

import com.company.jersey03.MainApplication;
import com.company.jersey03.models.DonorEntity;
import com.fasterxml.jackson.core.type.TypeReference;
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

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    EntityManager em = getEntityManager();
    DonorEntity donor;

    em.getTransaction().begin();

    Query q3 = em.createQuery("DELETE FROM Donor");
    q3.executeUpdate();

    donor = new DonorEntity();
    donor.setFirstName("John");
    donor.setLastName("Smith");
    em.persist(donor);

    donor = new DonorEntity();
    donor.setFirstName("Bill ");
    donor.setLastName("Jones");
    em.persist(donor);

    donor = new DonorEntity();
    donor.setFirstName("Reg");
    donor.setLastName("Hill");
    em.persist(donor);

    em.getTransaction().commit();
    em.close();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void testFetchList() {
    Invocation.Builder invocationBuilder = target.path("donors").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<DonorEntity> donors = mapper.readValue(responseMsg, new TypeReference<List<DonorEntity>>() {
      });

      for (DonorEntity donor : donors) {
        System.out.println(donor);
      }

      assertTrue(donors.size() > 0);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testFetchOne() {
    Invocation.Builder invocationBuilder = target.path("donors").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<DonorEntity> donors = mapper.readValue(responseMsg, new TypeReference<List<DonorEntity>>() {
      });

      for (DonorEntity donor : donors) {
        Long id = donor.getId();
        Invocation.Builder invocationBuilder2 = target.path("donors/" + id).request().accept(MediaType.APPLICATION_JSON);

        String responseMsg2 = invocationBuilder2.get(String.class);
        //DonorResponse donorResponse2 = mapper.readValue(responseMsg2, DonorResponse.class);

        //assertTrue(donorResponse2.getData().size() == 1);
        //Donor donor2 = donorResponse2.getData().get(0);

        //assertEquals(donor.getFirstName(), donor2.getFirstName());
        //assertEquals(donor.getLastName(), donor2.getLastName());
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  // @Test
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

  // @Test
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

  // @Test
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

  protected static EntityManager getEntityManager() throws NamingException {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jersey02");
    return emf.createEntityManager();
  }
}
