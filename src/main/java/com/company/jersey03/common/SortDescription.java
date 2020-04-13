package com.company.jersey03.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortDescription {
  protected String field;
  protected SortDirection direction;
}
