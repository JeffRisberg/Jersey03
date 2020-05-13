package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO extends AbstractDatedDTO {

  private String description;
  private String contentTypeName;
  private String fieldName;
  private String fieldPath;
  private String fieldType;
  private String fieldValues;
  private String dbColumnName;
  private Boolean isRequired;
  private Integer seqNum;
}
