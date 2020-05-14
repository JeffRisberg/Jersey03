package com.company.jersey03.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

  public static DonationEntity toEntity(DonationDTO dto) {
    DonationEntity entity = new DonationEntity();
    entity.initialize(dto);

    entity.setAmount(dto.getAmount());
    entity.setCharityId(dto.getCharityId());
    entity.setDonorId(dto.getDonorId());

    return entity;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("DonationEntity[date=" + getDateCreated());
    sb.append(", amount=" + amount);
    sb.append("]");

    return sb.toString();
  }
}
