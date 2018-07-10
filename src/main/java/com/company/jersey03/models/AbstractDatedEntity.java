package com.company.jersey03.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractDatedEntity extends AbstractEntity {

  @Column(name = "date_created")
  protected Date dateCreated;

  @Column(name = "last_updated")
  protected Date lastUpdated;

  @PrePersist
  protected void onCreate() {
    dateCreated = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    lastUpdated = new Date();
  }
}
