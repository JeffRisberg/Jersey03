package com.company.jersey03.models;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

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

  protected AbstractEntity applyDTO(AbstractDTO dto) {
    if (dto != null) {
      this.setId(dto.getId());
    }
    return this;
  }
}
