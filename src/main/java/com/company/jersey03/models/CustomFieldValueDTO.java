package com.company.jersey03.models;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomFieldValueDTO extends AbstractDTO {

  private Field field;
  private Long fieldId;
  private String entityType;
  private Long entityId;
  private String fieldValue;
}
