package com.company.jersey03.services.DAO;

import com.company.jersey03.common.FilterDescription;
import lombok.NonNull;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseTemplateDAO<T> {
  T create(T obj, @NonNull EntityManager em);

  T getById(Serializable id, @NonNull EntityManager em);

  T getById(String id, @NonNull EntityManager em);

  T getByName(String name, @NonNull EntityManager em);

  List<T> listAll(Class<T> type, int limit, int offset, @NonNull EntityManager em);

  List<T> getBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull EntityManager em);

  List<T> getByCriteria(List<FilterDescription> filterDescriptions, int limit, int offset, @NonNull EntityManager em);

  List<T> getByCriteria(@NonNull Map<String, Object> params, EntityManager em);

  int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params, @NonNull EntityManager em);

  Boolean update(T obj, @NonNull EntityManager em);

  Boolean deleteById(Serializable id, @NonNull EntityManager em);

  Boolean deleteById(String id, @NonNull EntityManager em);

  Boolean delete(T obj, @NonNull EntityManager em);
}
