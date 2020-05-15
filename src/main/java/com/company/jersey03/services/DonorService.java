package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.DonorEntity;
import com.company.jersey03.services.DAO.DonorDAO;
import com.google.inject.Inject;
import org.hibernate.Session;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DonorService extends AbstractService<DonorEntity> {
  private static final String entityType = "Donor";

  private final DonorDAO dao;

  @Inject
  public DonorService(final MyEntityManagerFactory myEntityManagerFactory,
                      final DonorDAO donorDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = donorDAO;
  }

  public DonorEntity getById(Long id) {
    final AtomicReference<DonorEntity> td = new AtomicReference<>();
    doWork(em -> {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
        .setParameter("entityType", entityType);
      td.set(dao.getById(id, em));
    });
    return td.get();
  }

  public List<DonorEntity> getAll(int limit, int offset) {
    final AtomicReference<List<DonorEntity>> td = new AtomicReference<>();
    doWork(em -> {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
        .setParameter("entityType", entityType);
      td.set(dao.listAll(DonorEntity.class, limit, offset, em));
    });
    return td.get();
  }

  public List<DonorEntity> getByCriteria
    (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit, int offset) {
    final AtomicReference<List<DonorEntity>> td = new AtomicReference<>();
    doWork(em -> {
      Session session = em.unwrap(Session.class);
      session.enableFilter("entityTypeFilter")
        .setParameter("entityType", entityType);
      td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em));
    });
    return td.get();
  }

  public DonorEntity create(DonorEntity donor) {
    final AtomicReference<DonorEntity> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(donor, em)));
    return created.get();
  }

  public boolean update(DonorEntity updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }

  public DonorEntity getByName(String name) {
    final AtomicReference<DonorEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByName(name, em)));
    return td.get();
  }
}

