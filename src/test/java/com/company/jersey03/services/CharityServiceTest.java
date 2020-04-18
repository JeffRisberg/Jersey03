package com.company.jersey03.services;

import com.company.common.base.config.AppConfig;
import com.company.common.base.config.DatabaseConfig;
import com.company.common.base.services.config.ArchaiusAppConfig;
import com.company.common.base.services.config.MySQLDatabaseConfig;
import com.company.jersey03.MainModule;
import com.company.jersey03.models.CharityEntity;
import com.company.jersey03.services.DAO.CharityDAO;
import com.google.inject.AbstractModule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jeff Risberg
 * @since 11/03/17
 */
public class CharityServiceTest {

  private static CharityService charityService;

  @BeforeClass
  public static void setUp() throws Exception {
    AbstractModule baseModule = new MainModule();

    AppConfig appConfig = new ArchaiusAppConfig();

    DatabaseConfig databaseConfig = new MySQLDatabaseConfig(appConfig);

    MyEntityManagerFactory xyz2 = new MyEntityManagerFactory(databaseConfig);

    CharityDAO charityDao = new CharityDAO();

    charityService = new CharityService(xyz2, charityDao);

    charityService.doWork(em -> {
      CharityEntity charity;

      Query q = em.createNativeQuery("DELETE FROM Charities");
      q.executeUpdate();

      charity = new CharityEntity();
      charity.setName("Red Cross");
      em.persist(charity);

      charity = new CharityEntity();
      charity.setName("ASPCA");
      em.persist(charity);

      charity = new CharityEntity();
      charity.setName("United Way");
      em.persist(charity);
    });
  }

  @Test
  public void testGetList() {
    List<CharityEntity> charityList = charityService.getAll(50, 0);

    assertNotNull(charityList);
    assertEquals(3, charityList.size());
  }

  @Test
  public void testGetOne() {
    List<CharityEntity> charityList = charityService.getAll(50, 0);

    for (CharityEntity charity : charityList) {
      long id = charity.getId();

      assertNotNull(charityService.getById(id));
    }
  }
}
