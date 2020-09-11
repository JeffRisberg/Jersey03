package com.company.jersey03.services;

import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeff Risberg
 * @since 11/11/17
 */
@Slf4j
public abstract class AbstractService<T> {

  protected MyEntityManagerFactory myEntityManagerFactory;

  // true indicates success
  public boolean doWork(Consumer<EntityManager> consumer) {
    EntityManager em = myEntityManagerFactory.createEntityManager();

    try {
      EntityTransaction transaction = em.getTransaction();
      transaction.begin();
      try {
        // call consumer
        consumer.accept(em);
        transaction.commit();
        return true;
      } catch (Exception e) {
        log.error("Failed to execute transaction", e);
        transaction.rollback();
        return false;
      }
    } finally {
      em.close();
    }
  }

  public void close() {
    myEntityManagerFactory.close();
  }

  public abstract T getById(Long id);

  public abstract List<T> getAll(int limit, int offset);
}
