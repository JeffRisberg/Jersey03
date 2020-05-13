package com.company.jersey03.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@NoArgsConstructor
@Data
public class DonorDTO extends AbstractDatedDTO {

  private String firstName;
  private String lastName;
  private Integer age;
}
