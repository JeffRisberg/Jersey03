package com.company.jersey03.services.DAO;

import com.company.jersey03.models.Field;
import javax.persistence.EntityManager;
import lombok.NonNull;

public class FieldDAO extends BaseTemplateDAOImpl<Field> {

  public FieldDAO() {
    super(Field.class);
  }

  public Field create(Field obj, @NonNull EntityManager em) {
    return super.create(obj, em);
  }

  public Field getById(Long id, @NonNull EntityManager em) {
    return super.getById(id, em);
  }

  public Boolean delete(Long id, @NonNull EntityManager em) {
    return super.deleteById(id, em);
  }
}

