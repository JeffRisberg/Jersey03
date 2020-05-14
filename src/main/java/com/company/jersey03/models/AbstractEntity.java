package com.company.jersey03.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Jeff Risberg
 * @since 10/22/17
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  public abstract Object toDTO();

  protected boolean update(AbstractDTO dto) {
    if (dto != null) {
      dto.setId(this.id);
      return true;
    }
    return false;
  }

  protected boolean initialize(AbstractDTO dto) {
    if (dto != null) {
      this.setId(dto.getId());
      return true;
    }
    return false;
  }
}
