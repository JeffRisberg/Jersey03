package com.company.jersey03.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class DonationDTO extends AbstractDatedDTO {

  private Double amount;
  private Long donorId;

  private String donorFirstName;
  private String donorLastName;

  private Long charityId;

  private String charityName;
}
