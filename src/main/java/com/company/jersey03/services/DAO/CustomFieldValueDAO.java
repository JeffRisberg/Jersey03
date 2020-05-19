package com.company.jersey03.services.DAO;

import com.company.jersey03.models.CustomFieldValue;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class CustomFieldValueDAO extends BaseTemplateDAOImpl<CustomFieldValue> {

  public CustomFieldValueDAO() {
    super(CustomFieldValue.class);
  }

  public CustomFieldValue create(CustomFieldValue obj, @NonNull EntityManager em) {
    return super.create(obj, em);
  }

  public CustomFieldValue getById(Long id, @NonNull EntityManager em) {
    return super.getById(id, em);
  }

  public Boolean delete(Long id, @NonNull EntityManager em) {
    return super.deleteById(id, em);
  }
}

