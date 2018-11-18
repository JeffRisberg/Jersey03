package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Jeff Risberg
 * @since 10/26/17
 */
@Data
@Entity
@Table(name = "donors")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Donation extends AbstractEntity {

  @ManyToOne()
  @JoinColumn(name = "donor_id")
  protected Donor donor;

  @ManyToOne()
  @JoinColumn(name = "charity_id")
  protected Charity charity;

  @Column(name = "amount")
  protected float amount;
}
