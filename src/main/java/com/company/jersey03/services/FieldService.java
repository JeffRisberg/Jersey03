package com.company.jersey03.services;

import com.company.common.*;
import com.company.jersey03.models.Field;
import com.company.jersey03.services.DAO.FieldDAO;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FieldService extends AbstractService<Field> {

  private final FieldDAO dao;

  @Inject
  public FieldService(final MyEntityManagerFactory myEntityManagerFactory,
      final FieldDAO fieldDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = fieldDAO;
  }

  public Field create(Field field) {
    final AtomicReference<Field> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(field, em)));
    return created.get();
  }

  public Field getById(Long id) {
    final AtomicReference<Field> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getById(id, em)));
    return td.get();
  }

  public List<Field> getAll(int limit, int offset) {
    final AtomicReference<List<Field>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.listAll(Field.class, limit, offset, em)));
    return td.get();
  }

  public List<Field> getByCriteria
      (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit,
          int offset) {
    final AtomicReference<List<Field>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em)));
    return td.get();
  }

  public Field getByContentTypeFieldName(String contentTypeName, String fieldName) {
    List<FilterDescription> filterDescs = new ArrayList<FilterDescription>();
    filterDescs.add(new FilterDescription("contentTypeName", FilterOperator.eq, contentTypeName));
    filterDescs.add(new FilterDescription("fieldName", FilterOperator.eq, fieldName));

    final AtomicReference<List<Field>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescs, null, 50, 0, em)));
    if (td.get().size() > 0) {
      return td.get().get(0);
    } else {
      return null;
    }
  }

  public boolean update(Field updatedEntity) {
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

