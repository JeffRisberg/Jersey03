package com.company.jersey03.models;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FieldDTO extends AbstractDatedDTO {

  private String description;
  private String contentTypeName;
  private String fieldName;
  private String fieldPath;
  private String fieldType;
  private String fieldValues;
  private Boolean isCustom;
  private String dbColumnName;
  private Boolean isRequired;
  private Integer seqNum;
}
