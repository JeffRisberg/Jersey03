package com.company.jersey03.services.DAO;

import com.company.jersey02.models.DonationEntity;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class DonationDAO extends BaseTemplateDAOImpl<DonationEntity> {

  public DonationDAO() {
    super(DonationEntity.class);
  }

  public DonationEntity create(DonationEntity obj, @NonNull EntityManager em) {
    return super.create(obj, em);
  }

  public DonationEntity getById(Long id, @NonNull EntityManager em) {
    return super.getById(id, em);
  }

  public Boolean delete(Long id, @NonNull EntityManager em) {
    return super.deleteById(id, em);
  }
}
