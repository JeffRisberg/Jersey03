package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.jersey03.models.DonationEntity;
import com.company.jersey03.services.DAO.DonationDAO;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DonationService extends AbstractService<DonationEntity> {
  private final DonationDAO dao;

  @Inject
  public DonationService(final MyEntityManagerFactory myEntityManagerFactory,
                         final DonationDAO donationDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = donationDAO;
  }

  public DonationEntity create(DonationEntity donation) {
    final AtomicReference<DonationEntity> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(donation, em)));
    return created.get();
  }

  public DonationEntity getById(Long id) {
    final AtomicReference<DonationEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getById(id, em)));
    return td.get();
  }

  public List<DonationEntity> getAll(int limit, int offset) {
    final AtomicReference<List<DonationEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.listAll(DonationEntity.class, limit, offset, em)));
    return td.get();
  }

  public List<DonationEntity> getByCriteria(List<FilterDescription> filterDescriptions, int limit, int offset) {
    final AtomicReference<List<DonationEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescriptions, limit, offset, em)));
    return td.get();
  }

  public boolean update(DonationEntity updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }
}

