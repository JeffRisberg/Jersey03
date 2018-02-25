package com.company.jersey03.common;

/**
 * @author Jeff Risberg
 * @since 3/24/17
 */
public class SortDesc {
    private FieldDesc field;
    private SortDirection direction;

    public SortDesc(FieldDesc field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public SortDesc(FieldDesc field) {
        this.field = field;
        this.direction = SortDirection.Ascending;
    }

    public FieldDesc getField() {
        return field;
    }

    public SortDirection getDirection() {
        return direction;
    }
}
