package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true)
public class CustomFieldDTO {

  private static final long serialVersionUID = -8109216677471759709L;

  private Long id;
  private String key;
  private String value;
}
