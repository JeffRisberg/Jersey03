package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@Table(name = "custom_field_values")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomFieldValueEntity extends AbstractEntity {

  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotFound(action = NotFoundAction.IGNORE) // Ignore, in case fields and mappings are in different dbs
  @JoinColumn(name = "field_id", insertable = false, updatable = false)
  private Field field;

  @Column(name = "field_id")
  private Long fieldId;

  @Column(name = "entity_type")
  private String entityType;

  @Column(name = "entity_id", nullable = false)
  private Long entityId;

  @Column(name = "field_value")
  private String fieldValue;

  @Override
  public CustomFieldValueDTO toDTO() {
    CustomFieldValueDTO result = new CustomFieldValueDTO();
    update(result);

    result.setField(this.field);
    result.setFieldId(this.fieldId);
    result.setEntityType(this.entityType);
    result.setEntityId(this.entityId);
    result.setFieldValue(this.fieldValue);
    return result;
  }
}
