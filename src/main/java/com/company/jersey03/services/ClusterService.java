package com.company.jersey03.services;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.ClusterEntity;
import com.company.jersey03.services.DAO.ClusterDAO;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClusterService extends AbstractService<ClusterEntity> {
  private final ClusterDAO dao;

  @Inject
  public ClusterService(final MyEntityManagerFactory myEntityManagerFactory,
                        final ClusterDAO clusterDAO) {
    this.myEntityManagerFactory = myEntityManagerFactory;
    this.dao = clusterDAO;
  }

  public ClusterEntity create(ClusterEntity cluster) {
    final AtomicReference<ClusterEntity> created = new AtomicReference<>();
    doWork(em -> created.set(dao.create(cluster, em)));
    return created.get();
  }

  public ClusterEntity getById(Long id) {
    final AtomicReference<ClusterEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getById(id, em)));
    return td.get();
  }

  public List<ClusterEntity> getAll(int limit, int offset) {
    final AtomicReference<List<ClusterEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.listAll(ClusterEntity.class, limit, offset, em)));
    return td.get();
  }

  public List<ClusterEntity> getByCriteria
    (List<FilterDescription> filterDescs, List<SortDescription> sortDescs, int limit, int offset) {
    final AtomicReference<List<ClusterEntity>> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByCriteria(filterDescs, sortDescs, limit, offset, em)));
    return td.get();
  }

  public boolean update(ClusterEntity updatedEntity) {
    final AtomicReference<Boolean> updated = new AtomicReference<>();
    boolean success = doWork(em -> updated.set(dao.update(updatedEntity, em)));
    return success && updated.get();
  }

  public boolean delete(Long id) {
    final AtomicReference<Boolean> deleted = new AtomicReference<>();
    boolean success = doWork(em -> deleted.set(dao.delete(id, em)));
    return success && deleted.get();
  }

  public ClusterEntity getByName(String name) {
    final AtomicReference<ClusterEntity> td = new AtomicReference<>();
    doWork(em -> td.set(dao.getByName(name, em)));
    return td.get();
  }
}
