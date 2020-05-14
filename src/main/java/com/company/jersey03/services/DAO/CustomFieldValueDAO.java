package com.company.jersey03.services.DAO;

import com.company.jersey03.models.CustomFieldValueEntity;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class CustomFieldValueDAO extends BaseTemplateDAOImpl<CustomFieldValueEntity> {

  public CustomFieldValueDAO() {
    super(CustomFieldValueEntity.class);
  }

  public CustomFieldValueEntity create(CustomFieldValueEntity obj, @NonNull EntityManager em) {
    return super.create(obj, em);
  }

  public CustomFieldValueEntity getById(Long id, @NonNull EntityManager em) {
    return super.getById(id, em);
  }

  public Boolean delete(Long id, @NonNull EntityManager em) {
    return super.deleteById(id, em);
  }
}

