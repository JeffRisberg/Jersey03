package com.company.jersey03.models;

import com.company.jersey03.services.FieldService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "donors")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class DonorEntity extends AbstractDatedCFVEntity {

  static final String entityType = "entity_type = 'Donor'";

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "age", nullable = true)
  private Integer age;

  @OneToMany(mappedBy = "donor")
  @JsonIgnore
  private List<DonationEntity> donations = new ArrayList<DonationEntity>();

  public DonorDTO toDTO() {
    DonorDTO result = new DonorDTO();
    update(result);

    result.setId(getId());
    result.setFirstName(getFirstName());
    result.setLastName(getLastName());
    result.setAge(age);

    return result;
  }

  public DonorEntity applyDTO(DonorDTO dto, FieldService fieldService) {
    if (dto != null) {
      super.applyDTO(dto, "Donor", fieldService);

      if (dto.getFirstName() != null) {
        this.setFirstName(dto.getFirstName());
      }
      if (dto.getLastName() != null) {
        this.setLastName(dto.getLastName());
      }
      if (dto.getAge() != null) {
        this.setAge(dto.getAge());
      }
    }
    return this;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("DonorEntity[firstName=" + firstName);
    sb.append(", lastName=" + lastName);
    sb.append(", age=" + age);
    sb.append("]");

    return sb.toString();
  }
}
