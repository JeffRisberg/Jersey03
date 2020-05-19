package com.company.jersey03.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.company.common.base.config.AppConfig;
import com.company.common.base.config.DatabaseConfig;
import com.company.common.base.services.config.ArchaiusAppConfig;
import com.company.common.base.services.config.MySQLDatabaseConfig;
import com.company.jersey03.MainModule;
import com.company.jersey03.models.DonorEntity;
import com.company.jersey03.services.DAO.DonorDAO;
import com.google.inject.AbstractModule;
import java.util.List;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jeff Risberg
 * @since 11/03/17
 */
public class DonorServiceTest {

  private DonorService donorService;

  @Before
  public void setUp() throws Exception {
    AbstractModule baseModule = new MainModule();

    AppConfig appConfig = new ArchaiusAppConfig();

    DatabaseConfig databaseConfig = new MySQLDatabaseConfig(appConfig);

    MyEntityManagerFactory xyz2 = new MyEntityManagerFactory(databaseConfig);

    DonorDAO donorDao = new DonorDAO();

    donorService = new DonorService(xyz2, donorDao);

    donorService.doWork(em -> {
      DonorEntity donor;

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
    });
  }

  @Test
  public void testGetList() {
    List<DonorEntity> donorList = donorService.getAll(50, 0);

    assertNotNull(donorList);
    assertEquals(3, donorList.size());
  }

  @Test
  public void testGetOne() {
    List<DonorEntity> donorList = donorService.getAll(50, 0);

    for (DonorEntity donor : donorList) {
      long id = donor.getId();

      assertNotNull(donorService.getById(id));
    }
  }
}
