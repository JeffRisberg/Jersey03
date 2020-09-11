package com.company.jersey03.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Envelope for responses.
 *
 * @author Jeff Risberg
 * @since 10/26/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Envelope {

  protected Object data;
  protected Long totalCount;
  protected Integer limit;
  protected Integer offset;
  protected List<Error> errors;

  public Envelope(Object data, Long totalCount, Integer limit, Integer offset, List<Error> errors) {
    this.data = data;
    this.totalCount = totalCount;
    this.limit = limit;
    this.offset = offset;
    this.errors = errors;
  }

  public Envelope(Object data, List<Error> errors) {
    this.data = data;
    this.errors = errors;
  }

  public Envelope(List<Error> errors) {
    this.errors = errors;
  }

  public Envelope() {
  }

  public Object getData() {
    return data;
  }

  public List<Error> getErrors() {
    return errors;
  }

  public Long getTotalCount() {
    return totalCount;
  }

  public Integer getLimit() {
    return limit;
  }

  public Integer getOffset() {
    return offset;
  }
}
