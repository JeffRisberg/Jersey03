package com.company.jersey03.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharityDTO {

  private Long id;

  protected Date dateCreated;

  protected Date lastUpdated;

  protected String name;

  protected String ein;

  protected String description;

  protected String website;

  protected List<CustomFieldDTO> customFields = new ArrayList();
}
