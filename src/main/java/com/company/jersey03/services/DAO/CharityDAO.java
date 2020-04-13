package com.company.jersey03.services.DAO;

import com.company.jersey02.models.CharityEntity;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class CharityDAO extends BaseTemplateDAOImpl<CharityEntity> {

    public CharityDAO() {
        super(CharityEntity.class);
    }

    public CharityEntity create(CharityEntity obj, @NonNull EntityManager em) {
        return super.create(obj, em);
    }

    public CharityEntity getById(Long id, @NonNull EntityManager em) {
        return super.getById(id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(id, em);
    }
}

