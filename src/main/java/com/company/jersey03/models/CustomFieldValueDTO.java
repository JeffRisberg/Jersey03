package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomFieldValueDTO extends AbstractDTO {

  private Field field;
  private Long fieldId;
  private String entityType;
  private Long entityId;
  private String fieldValue;
}
