package com.company.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortDescription {
  protected String field;
  protected SortDirection direction;
}
