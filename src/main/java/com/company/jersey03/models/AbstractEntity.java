package com.company.jersey03.models;

import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(value = "Provided by the framework. Set to 0 for create")
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;

  public abstract Object toDTO();

  protected boolean update(AbstractDTO dto) {
    dto.setId(this.id);
    return true;
  }
}
