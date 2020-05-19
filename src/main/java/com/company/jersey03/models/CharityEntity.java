package com.company.jersey03.models;

import com.company.jersey03.services.FieldService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "charities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class CharityEntity extends AbstractDatedCFVEntity {

  @Column(name = "name", nullable = false)
  protected String name;

  @Column(name = "ein", nullable = false)
  private String ein;

  @Column(name = "description", nullable = true)
  protected String description;

  @Column(name = "website", nullable = true)
  protected String website;

  public CharityDTO toDTO() {
    CharityDTO result = new CharityDTO();
    update(result);

    result.setName(getName());
    result.setEin(getEin());
    result.setDescription(getDescription());
    result.setWebsite(getWebsite());

    return result;
  }

  public CharityEntity applyDTO(CharityDTO dto, FieldService fieldService) {
    if (dto != null) {
      super.applyDTO(dto, "Charity", fieldService);

      if (dto.getName() != null) this.setName(dto.getName());
      if (dto.getEin() != null) this.setEin(dto.getEin());
      if (dto.getDescription() != null) this.setDescription(dto.getDescription());
      if (dto.getWebsite() != null) this.setWebsite(dto.getWebsite());
    }
    return this;
  }
}
