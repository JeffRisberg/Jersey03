package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharityDTO extends AbstractDatedDTO {

  protected String name;
  protected String ein;
  protected String description;
  protected String website;
}
