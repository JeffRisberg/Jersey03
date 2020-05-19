package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.CustomFieldValue;
import com.company.jersey03.services.DAO.CustomFieldValueDAO;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CustomFieldValueService extends AbstractService<CustomFieldValue> {
  private final CustomFieldValueDAO dao;

  @Inject
  public CustomFieldValueService(final MyEntityManagerFactory myEntityManagerFactory,
                                 final CustomFieldValueDAO customFieldValueDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = new CustomFieldValueDAO();
  }

  public CustomFieldValue create(CustomFieldValue customFieldValue) {
    final AtomicReference<CustomFieldValue> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(customFieldValue, em)));
    return created.get();
  }

  public CustomFieldValue getById(Long id) {
    final AtomicReference<CustomFieldValue> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getById(id, em)));
    return td.get();
  }

  public List<CustomFieldValue> getAll(int limit, int offset) {
    final AtomicReference<List<CustomFieldValue>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.listAll(CustomFieldValue.class, limit, offset, em)));
    return td.get();
  }

  public List<CustomFieldValue> getByCriteria
    (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit, int offset) {
    final AtomicReference<List<CustomFieldValue>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em)));
    return td.get();
  }

  public boolean update(CustomFieldValue updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }

  public CustomFieldValue getByName(String name) {
    final AtomicReference<CustomFieldValue> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByName(name, em)));
    return td.get();
  }
}
