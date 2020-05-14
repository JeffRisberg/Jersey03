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

  public boolean update(AbstractDatedDTO dto) {
    if (dto != null) {
      super.update(dto);

      dto.setDateCreated(this.dateCreated);
      dto.setLastUpdated(this.lastUpdated);
      return true;
    }
    return false;
  }

  protected boolean initialize(AbstractDatedDTO dto) {
    if (dto != null) {
      super.initialize(dto);

      this.setLastUpdated(dto.getLastUpdated());
      this.setDateCreated(dto.getDateCreated());
      return true;
    }
    return false;
  }
}
