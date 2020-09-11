package com.company.jersey03.services.DAO;

import com.company.common.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class BaseTemplateDAOImpl<T> implements BaseTemplateDAO<T> {

  private Class<T> type;

  public BaseTemplateDAOImpl(Class<T> type) {
    this.type = type;
  }

  @Override
  public T create(T obj, @NonNull EntityManager em) {
    em.persist(obj);
    return obj;
  }

  @Override
  public T getById(Serializable id, @NonNull EntityManager em) {
    AtomicReference<T> reference = new AtomicReference<>();
    reference.set(em.find(type, id));
    return reference.get();
  }

  @Override
  public T getById(String id, @NonNull EntityManager em) {
    AtomicReference<T> reference = new AtomicReference<>();
    reference.set(em.find(type, id));
    return reference.get();
  }

  @Override
  public T getByName(String name, @NonNull EntityManager em) {
    final AtomicReference<T> ref = new AtomicReference<>();
    List<FilterDescription> filterDescs = new ArrayList<>();
    filterDescs.add(new FilterDescription("name", FilterOperator.eq, name));
    List<T> list = getByCriteria(filterDescs, null, 0, 0, em);
    if (list.isEmpty()) {
      ref.set(null);
    } else {
      ref.set(list.get(0));
    }
    return ref.get();
  }

  @Override
  public List<T> listAll(Class<T> type, int limit, int offset, @NonNull EntityManager em) {
    try {
      CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(type);
      criteria.select(criteria.from(type));
      Query query = em.createQuery(criteria);

      if (limit > 0) {
        query.setMaxResults(limit);
      }
      if (offset > 0) {
        query.setFirstResult(offset);
      }

      return query.getResultList();
    } catch (Exception e) {
      log.error("listAll:: exception", e);
      return new ArrayList();
    }
  }

  /**
   * Execute Custom SQL with Map of parameters
   */
  @Override
  public List<T> getBySQL(@NonNull String sql, @NonNull Map<String, Object> params,
      @NonNull EntityManager em) {
    try {
      Query query = em.createNativeQuery(sql);
      params.entrySet().stream()
          .forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
      List<T> result = query.getResultList();

      if (CollectionUtils.isEmpty(result)) {
        return Collections.emptyList();
      }

      return result;
    } catch (Exception e) {
      log.error("getBySQL:: exception", e);
      return null;
    }
  }

  /**
   * Execute Custom SQL with Map of parameters
   */
  @Override
  public int updateBySQL(@NonNull String sql, @NonNull Map<String, Object> params,
      @NonNull EntityManager em) {
    try {
      Query query = em.createNativeQuery(sql);
      params.entrySet().stream()
          .forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
      int updated = query.executeUpdate();
      log.info("Updated/Deleted by SQL: {}", updated);
      return updated;
    } catch (Exception e) {
      log.error("updateBySQL:: exception", e);
      return 0;
    }
  }

  /**
   * Use Criteria Builder to execute a SQL By Parameters.
   *
   * @return List of results
   */
  @Override
  public List<T> getByCriteria
  (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit, int offset,
      @NonNull EntityManager em) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    Root<T> root = criteriaQuery.from(type);
    criteriaQuery.select(root);
    Predicate p = criteriaBuilder.conjunction();

    if (filterDescs != null) {
      for (FilterDescription fd : filterDescs) {
        String key = fd.getField();
        Object value = fd.getValue();

        if (value == null) {
          continue;
        }
        switch (fd.getOperator()) {
          case eq:
            p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get(key), value));
            break;
          case like:
            p = criteriaBuilder
                .and(p, criteriaBuilder.like(root.get(key), "%" + (String) value + "%"));
            break;
          case gt:
            p = criteriaBuilder
                .and(p, criteriaBuilder.greaterThan(root.get(key), (Comparable) value));
            break;
          case lt:
            p = criteriaBuilder.and(p, criteriaBuilder.lessThan(root.get(key), (Comparable) value));
            break;
          case gte:
            p = criteriaBuilder
                .and(p, criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Comparable) value));
            break;
          case lte:
            p = criteriaBuilder
                .and(p, criteriaBuilder.lessThanOrEqualTo(root.get(key), (Comparable) value));
            break;
          case timestamp_gte:
            p = criteriaBuilder
                .and(p, criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Timestamp) value));
            break;
          case timestamp_lte:
            p = criteriaBuilder
                .and(p, criteriaBuilder.lessThanOrEqualTo(root.get(key), (Timestamp) value));
            break;
        }
      }
      criteriaQuery.where(p);
    }

    if (sortDescs != null) {
      List<Order> orderList = new ArrayList<Order>();

      for (SortDescription sd : sortDescs) {
        String fieldName = sd.getField();
        SortDirection sortDir = sd.getDirection();

        switch (sd.getDirection()) {
          case asc:
            orderList.add(criteriaBuilder.asc(root.get(fieldName)));
            break;
          case desc:
            orderList.add(criteriaBuilder.desc(root.get(fieldName)));
            break;
        }
      }
      criteriaQuery.orderBy(orderList);
    }

    Query query = em.createQuery(criteriaQuery);
    if (offset > 0) {
      query.setFirstResult(offset);
    }
    if (limit > 0) {
      query.setMaxResults(limit);
    }

    return query.getResultList();
  }

  /**
   * Use Criteria Builder to execute a SQL By Parameters.
   *
   * @return List of results
   */
  @Override
  public List<T> getByCriteria(@NonNull Map<String, Object> params, EntityManager em) {
    try {
      CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
      Root<T> root = criteriaQuery.from(type);
      criteriaQuery.select(root);
      AtomicReference<Predicate> p = new AtomicReference<>(criteriaBuilder.conjunction());
      params.entrySet().stream().forEach(entry -> {
        p.set(criteriaBuilder
            .and(p.get(), criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue())));
      });
      criteriaQuery.where(p.get());
      return em.createQuery(criteriaQuery).getResultList();
    } catch (Exception e) {
      log.error("getByCriteria:: exception", e);
      return null;
    }
  }

  @Override
  public Boolean update(T obj, @NonNull EntityManager em) {
    em.merge(obj);
    return true;
  }

  @Override
  public Boolean deleteById(Serializable id, @NonNull EntityManager em) {
    if (id != null) {
      log.info("Deleting object with Id=" + id);
      T obj = getById(id, em);
      if (obj != null) {
        em.remove(obj);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Boolean deleteById(String id, @NonNull EntityManager em) {
    if (id != null) {
      log.info("Deleting object with Id: {}" + id);
      T obj = getById(id, em);
      if (obj != null) {
        em.remove(obj);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public Boolean delete(T obj, @NonNull EntityManager em) {
    if (obj != null) {
      log.info("Deleting object");
      em.remove(obj);
      return true;
    } else {
      return false;
    }
  }
}
