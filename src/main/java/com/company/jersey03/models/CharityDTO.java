package com.company.jersey03.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CharityDTO extends AbstractDatedDTO {

  private String name;
  private String ein;
  private String description;
  private String website;
}
