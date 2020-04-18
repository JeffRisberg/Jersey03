package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "custom_fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "entity_type")
  private String entityType;

  @Column(name = "entity_id", nullable = false)
  private Long entityId;

  @Column(name = "key")
  private String key;

  @Column(name = "value")
  private String value;

  public CustomFieldDTO toDTO() {
    CustomFieldDTO result = new CustomFieldDTO();
    result.setId(getId());
    result.setKey(getKey());
    result.setValue(getValue());
    return result;
  }
}
