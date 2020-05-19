package com.company.jersey03.models;

import lombok.*;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DonorDTO extends AbstractDatedDTO {

  private String firstName;
  private String lastName;
  private Integer age;
}
