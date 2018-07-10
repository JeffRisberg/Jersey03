package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
@Data
@Entity
@Table(name = "Donors")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Donor extends AbstractEntity {

  @Column(name = "first_name")
  protected String firstName;

  @Column(name = "last_name")
  protected String lastName;

  // The Lombok @AllArgsConstructor doesn't include superclass fields, so we need this
  public Donor(Long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
