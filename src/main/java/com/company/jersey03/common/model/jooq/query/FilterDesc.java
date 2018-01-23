package com.company.jersey03.common.model.jooq.query;

/**
 * @author Jeff Risberg
 * @since 11/24/16
 */
public class FilterDesc {
    private FieldDesc field;
    private FilterOperator operator;
    private Object value;

    public FilterDesc(FieldDesc field, FilterOperator operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public FieldDesc getField() {
        return field;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}
