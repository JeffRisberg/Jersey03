package com.company.jersey03.services.DAO;

import com.company.jersey03.models.ClusterEntity;
import javax.persistence.EntityManager;
import lombok.NonNull;

public class ClusterDAO extends BaseTemplateDAOImpl<ClusterEntity> {

  public ClusterDAO() {
    super(ClusterEntity.class);
  }

  public ClusterEntity create(ClusterEntity obj, @NonNull EntityManager em) {
    return super.create(obj, em);
  }

  public ClusterEntity getById(Long id, @NonNull EntityManager em) {
    return super.getById(id, em);
  }

  public Boolean delete(Long id, @NonNull EntityManager em) {
    return super.deleteById(id, em);
  }
}

