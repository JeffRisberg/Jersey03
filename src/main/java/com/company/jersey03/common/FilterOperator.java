package com.company.jersey03.common;

/**
 * @author Jeff Risberg
 * @since 12/11/15
 */

public enum FilterOperator {
  none,
  eq,
  neq,
  like,
  gt,
  lt,
  gte,
  lte,
  timestamp_gte,
  timestamp_lte
}
