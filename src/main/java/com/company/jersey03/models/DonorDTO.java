package com.company.jersey03.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@NoArgsConstructor
@Data
public class DonorDTO {

  private Long id;

  private String firstName;

  private String lastName;

  private Integer age;

  protected List<CustomFieldDTO> customFields = new ArrayList();
}
