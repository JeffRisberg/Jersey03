package com.company.jersey03.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonationDTO extends AbstractDatedDTO {

  private Double amount;
  private Long donorId;

  private String donorFirstName;
  private String donorLastName;

  private Long charityId;

  private String charityName;
}
