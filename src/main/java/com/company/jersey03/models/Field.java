package com.company.jersey03.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "fields")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Field extends AbstractDatedEntity {

  @Column(name = "content_type_name")
  @NotNull
  private String contentTypeName;

  @Column(name = "field_name")
  @NotNull
  private String fieldName;

  @Column(name = "description")
  private String description;

  @Column(name = "field_path")
  private String fieldPath;

  @Column(name = "field_type")
  private String fieldType;

  @Column(name = "field_values")
  private String fieldValues;

  @Column(name = "db_column_name")
  private String dbColumnName;

  @Column(name = "is_required")
  private Boolean isRequired;

  @Column(name = "seq_num")
  @NotNull
  private Integer seqNum;

  @Override
  public FieldDTO toDTO() {
    FieldDTO dto = new FieldDTO();
    update(dto);

    dto.setDescription(this.description);
    dto.setContentTypeName(this.contentTypeName);
    dto.setFieldName(this.fieldName);
    dto.setFieldPath(this.fieldPath);
    dto.setFieldType(this.fieldType);
    dto.setFieldValues(this.fieldValues);
    dto.setDbColumnName(this.dbColumnName);
    dto.setIsRequired(this.isRequired);
    dto.setSeqNum(this.seqNum);
    return dto;
  }

  public static Field toEntity(FieldDTO dto) {
    if (dto == null) {
      return null;
    }
    Field entity = new Field();
    //entity.initialize(dto);
    entity.setDescription(dto.getDescription());
    entity.setContentTypeName(dto.getContentTypeName());
    entity.setFieldName(dto.getFieldName());
    entity.setFieldPath(dto.getFieldPath());
    entity.setFieldType(dto.getFieldType());
    entity.setFieldValues(dto.getFieldValues());
    entity.setDbColumnName(dto.getDbColumnName());
    entity.setIsRequired(dto.getIsRequired());
    entity.setSeqNum(dto.getSeqNum());
    return entity;
  }
}
