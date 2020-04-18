package com.company.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterDescription {
  protected String field;
  protected FilterOperator operator;
  protected Object value;
}
