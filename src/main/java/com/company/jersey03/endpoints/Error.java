package com.company.jersey03.endpoints;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Provides the most important or commonly-used fields of the Error object.
 *
 * @author Jeff Risberg
 * @since 10/26/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

  protected Integer id;
  protected String title;
  protected String detail;
  protected int status;

  public Error(Integer id, String title, String detail, int status) {
    this.id = id;
    this.title = title;
    this.detail = detail;
    this.status = status;
  }

  public Error(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDetail() {
    return detail;
  }

  public int getStatus() {
    return status;
  }
}
