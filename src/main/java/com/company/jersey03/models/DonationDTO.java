package com.company.jersey03.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class DonationDTO {

  private Long id;

  private Date dateCreated;

  private Date lastUpdated;

  private Double amount;

  private Long donorId;

  private String donorFirstName;
  private String donorLastName;

  private Long charityId;

  private String charityName;
}
