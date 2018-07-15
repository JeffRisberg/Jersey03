package com.company.jersey03.common;

/**
 * @author Jeff Risberg
 * @since 11/24/16
 */
public class FilterDesc {
  protected String field;
  protected FilterOperator operator;
  protected Object value;

  public FilterDesc(String field, FilterOperator operator, Object value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public String getField() {
    return field;
  }

  public FilterOperator getOperator() {
    return operator;
  }

  public Object getValue() {
    return value;
  }
}
