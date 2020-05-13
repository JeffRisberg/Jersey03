package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "donations")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DonationEntity extends AbstractDatedEntity {

  @Column(name = "amount", nullable = false)
  private Double amount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "donor_id", insertable = false, updatable = false)
  @JsonIgnore
  private DonorEntity donor;

  @Column(name = "donor_id")
  private Long donorId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "charity_id", insertable = false, updatable = false)
  @JsonIgnore
  private CharityEntity charity;

  @Column(name = "charity_id")
  private Long charityId;

  public DonationEntity(Double amount) {
    this.setId(null);
    this.amount = amount;
  }

  public DonationEntity(Double amount, DonorEntity donor, long donorId, CharityEntity charity, long charityId) {
    this.setId(null);
    this.amount = amount;
    this.donor = donor;
    this.donorId = donorId;
    this.charity = charity;
    this.charityId = charityId;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("DonationEntity[date=" + getDateCreated());
    sb.append(", amount=" + amount);
    sb.append("]");

    return sb.toString();
  }

  public DonationDTO toDTO() {
    DonationDTO result = new DonationDTO();
    update(result);

    result.setAmount(amount);
    result.setCharityId(getCharityId());
    result.setCharityName(charity.getName());
    result.setDonorId(getDonorId());
    result.setDonorFirstName(donor.getFirstName());
    result.setDonorLastName(donor.getLastName());

    return result;
  }

  public JSONObject toJSON() {
    JSONObject result = new JSONObject();

    result.put("id", getId());
    result.put("dateCreated", getDateCreated());
    result.put("lastUpdated", getLastUpdated());

    //for (CustomFieldEntity cfe : getCustomFields()) {
    //  result.put(cfe.getKey(), cfe.getValue());
    //}
    return result;
  }
}
