package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CharityDTO extends AbstractDatedDTO {

  protected String name;
  protected String ein;
  protected String description;
  protected String website;
}
