package com.company.jersey03.endpoints;

import com.company.jersey03.MainApplication;
import com.company.jersey03.models.CharityEntity;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class CharityEndpointTest {
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
    CharityEntity charity;

    em.getTransaction().begin();

    Query q3 = em.createQuery("DELETE FROM Charity");
    q3.executeUpdate();

    charity = new CharityEntity();
    charity.setName("Red Cross");
    em.persist(charity);

    charity = new CharityEntity();
    charity.setName("ASPCA");
    em.persist(charity);

    charity = new CharityEntity();
    charity.setName("United Way");
    em.persist(charity);

    em.getTransaction().commit();
    em.close();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void testFetchList() {
    Invocation.Builder invocationBuilder = target.path("charities").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<CharityEntity> charities = mapper.readValue(responseMsg, new TypeReference<List<CharityEntity>>() {
      });

      for (CharityEntity charity : charities) {
        System.out.println(charity);
      }

      assertTrue(charities.size() > 0);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testFetchOne() {
    Invocation.Builder invocationBuilder = target.path("charities").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<CharityEntity> charities = mapper.readValue(responseMsg, new TypeReference<List<CharityEntity>>() {
      });

      for (CharityEntity charity : charities) {
        System.out.println(charity);
      }

      assertTrue(charities.size() > 0);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void testFetchMissing() {
    Invocation.Builder invocationBuilder = target.path("charities/999").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<CharityEntity> charities = mapper.readValue(responseMsg, new TypeReference<List<CharityEntity>>() {
      });

      assertTrue(charities.size() == 0);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  //@Test
  public void testDeleteValid() {
    Invocation.Builder invocationBuilder = target.path("charities").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.get(String.class);

      List<CharityEntity> charities = mapper.readValue(responseMsg, new TypeReference<List<CharityEntity>>() {
      });

      for (CharityEntity charity : charities) {
        Long id = charity.getId();
        Invocation.Builder invocationBuilder2 = target.path("charities/" + id).request().accept(MediaType.APPLICATION_JSON);

        String responseMsg2 = invocationBuilder2.delete(String.class);
        //CharityResponse charityResponse2 = mapper.readValue(responseMsg2, CharityResponse.class);

        //assertTrue(charityResponse2.getErrors() == null);
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  //@Test
  public void testDeleteMissing() {
    Invocation.Builder invocationBuilder = target.path("charities/999").request().accept(MediaType.APPLICATION_JSON);

    try {
      String responseMsg = invocationBuilder.delete(String.class);

      System.out.println(responseMsg);

      // fail();
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
