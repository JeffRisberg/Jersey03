package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeff Risberg
 * @since 11/3/17
 */

@Entity
@Table(name = "donors")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DonorEntity extends AbstractDatedEntity {

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "age", nullable = true)
  private Integer age;

  @OneToMany(mappedBy = "donor")
  @JsonIgnore
  private List<DonationEntity> donations = new ArrayList<DonationEntity>();

  //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  //@JoinColumn(name = "entity_id")
  //@Where(clause = "entity_type = 'DONOR'")
  //List<CustomFieldEntity> customFields = new ArrayList();

  public DonorEntity(String firstName, String lastName, int age) {
    this.setId(null);
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("DonorEntity[firstName=" + firstName);
    sb.append(", lastName=" + lastName);
    sb.append(", age=" + age);
    sb.append("]");

    return sb.toString();
  }

  public DonorDTO toDTO() {
    DonorDTO result = new DonorDTO();
    update(result);

    result.setId(getId());
    result.setFirstName(getFirstName());
    result.setLastName(getLastName());
    result.setAge(age);

    return result;
  }

  public JSONObject toJSON() {
    JSONObject result = new JSONObject();

    result.put("id", getId());
    result.put("dateCreated", getDateCreated());
    result.put("lastUpdated", getLastUpdated());
    result.put("firstName", getFirstName());
    result.put("lastName", getLastName());
    if (getAge() != null) {
      result.put("age", getAge());
    }

    return result;
  }
}
