package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.CharityEntity;
import com.company.jersey03.models.CustomFieldValue;
import com.company.jersey03.services.DAO.CharityDAO;
import com.google.inject.Inject;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.hibernate.Session;

public class CharityService extends AbstractService<CharityEntity> {

  private static final String entityType = "Charity";

  private final CharityDAO dao;

  @Inject
  public CharityService(final MyEntityManagerFactory myEntityManagerFactory,
      final CharityDAO charityDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = charityDAO;
  }

  public CharityEntity create(CharityEntity charity) {
    final AtomicReference<CharityEntity> created = new AtomicReference<>();
    doWork(em -> {
      created.set(dao.create(charity, em));

      for (CustomFieldValue cfv : charity.getNewCustomFieldValues()) {
        cfv.setEntityId(created.get().getId());
        em.persist(cfv);
      }
    });
    return created.get();
  }

  public CharityEntity getById(Long id) {
    final AtomicReference<CharityEntity> td = new AtomicReference<>();
    doWork(em ->
    {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
          .setParameter("entityType", entityType);
      td.set(dao.getById(id, em));
    });
    return td.get();
  }

  public List<CharityEntity> getAll(int limit, int offset) {
    final AtomicReference<List<CharityEntity>> td = new AtomicReference<>();
    doWork(em -> {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
          .setParameter("entityType", entityType);
      td.set(dao.listAll(CharityEntity.class, limit, offset, em));
    });
    return td.get();
  }

  public List<CharityEntity> getByCriteria
      (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit,
          int offset) {
    final AtomicReference<List<CharityEntity>> td = new AtomicReference<>();
    doWork(em -> {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
          .setParameter("entityType", entityType);
      td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em));
    });
    return td.get();
  }

  public boolean update(CharityEntity updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }

  public CharityEntity getByName(String name) {
    final AtomicReference<CharityEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByName(name, em)));
    return td.get();
  }
}
