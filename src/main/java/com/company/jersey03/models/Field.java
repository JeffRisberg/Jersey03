package com.company.jersey03.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

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

  @Column(name = "is_custom")
  private Boolean isCustom;

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

    dto.setFieldName(this.fieldName);
    dto.setDescription(this.description);
    dto.setContentTypeName(this.contentTypeName);
    dto.setFieldPath(this.fieldPath);
    dto.setFieldType(this.fieldType);
    dto.setFieldValues(this.fieldValues);
    dto.setIsCustom(this.isCustom);
    dto.setDbColumnName(this.dbColumnName);
    dto.setIsRequired(this.isRequired);
    dto.setSeqNum(this.seqNum);
    return dto;
  }

  public Field applyDTO(FieldDTO dto) {
    if (dto != null) {
      super.applyDTO(dto);

      if (dto.getFieldName() != null) {
        this.setFieldName(dto.getFieldName());
      }
      if (dto.getDescription() != null) {
        this.setDescription(dto.getDescription());
      }
      if (dto.getContentTypeName() != null) {
        this.setContentTypeName(dto.getContentTypeName());
      }
      if (dto.getFieldPath() != null) {
        this.setFieldPath(dto.getFieldPath());
      }
      if (dto.getFieldType() != null) {
        this.setFieldType(dto.getFieldType());
      }
      if (dto.getFieldValues() != null) {
        this.setFieldValues(dto.getFieldValues());
      }
      if (dto.getIsCustom() != null) {
        this.setIsCustom(dto.getIsCustom());
      }
      if (dto.getDbColumnName() != null) {
        this.setDbColumnName(dto.getDbColumnName());
      }
      if (dto.getIsRequired() != null) {
        this.setIsRequired(dto.getIsRequired());
      }
      if (dto.getSeqNum() != null) {
        this.setSeqNum(dto.getSeqNum());
      }
    }
    return this;
  }
}
