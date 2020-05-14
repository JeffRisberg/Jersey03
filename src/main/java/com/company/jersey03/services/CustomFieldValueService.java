package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.CustomFieldValueEntity;
import com.company.jersey03.services.DAO.CustomFieldValueDAO;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CustomFieldValueService extends AbstractService<CustomFieldValueEntity> {
  private final CustomFieldValueDAO dao;

  @Inject
  public CustomFieldValueService(final MyEntityManagerFactory myEntityManagerFactory,
                                 final CustomFieldValueDAO customFieldValueDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = new CustomFieldValueDAO();
  }

  public CustomFieldValueEntity create(CustomFieldValueEntity customFieldValue) {
    final AtomicReference<CustomFieldValueEntity> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(customFieldValue, em)));
    return created.get();
  }

  public CustomFieldValueEntity getById(Long id) {
    final AtomicReference<CustomFieldValueEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getById(id, em)));
    return td.get();
  }

  public List<CustomFieldValueEntity> getAll(int limit, int offset) {
    final AtomicReference<List<CustomFieldValueEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.listAll(CustomFieldValueEntity.class, limit, offset, em)));
    return td.get();
  }

  public List<CustomFieldValueEntity> getByCriteria
    (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit, int offset) {
    final AtomicReference<List<CustomFieldValueEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em)));
    return td.get();
  }

  public boolean update(CustomFieldValueEntity updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }

  public CustomFieldValueEntity getByName(String name) {
    final AtomicReference<CustomFieldValueEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByName(name, em)));
    return td.get();
  }
}
