package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.DonationDTO;
import com.company.jersey03.models.DonationEntity;
import com.company.jersey03.services.DonationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "Donations", description = "donations management")
@Path("donations")
@Slf4j
public class Donations extends AbstractEndpoint {

  protected DonationService donationService;

  @Inject
  public Donations(DonationService donationService) {
    this.donationService = donationService;
  }

  @POST
  @ApiOperation(value = "Register a new Donation. Set id=0", response = DonationDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create() {
    return null;
  }

  @GET
  @ApiOperation(value = "Gets all Donations", response = DonationDTO.class, responseContainer = "List")
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      List<DonationDTO> donations = new ArrayList<>();
      DonationEntity data = donationService.getById(id);
      if (data != null) {
        donations.add(data.toDTO());
      }
      return Response.ok(donations).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Donations", response = DonationDTO.class, responseContainer = "List")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetchList(
    @DefaultValue("50") @QueryParam("limit") int limit,
    @DefaultValue("0") @QueryParam("offset") int offset,
    @Context UriInfo uriInfo) {

    try {
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
      List<SortDescription> sortDescs = this.parseSorting(queryParams);

      List<DonationDTO> result = new ArrayList<>();
      List<DonationEntity> donations =
        donationService.getByCriteria(filterDescs, sortDescs, limit, offset);

      for (DonationEntity donation : donations) {
        result.add(donation.toDTO());
      }
      return Response.ok(result).build();
    } catch (Exception e) {
      return null;
    }
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@PathParam("id") Long id) {
    return Response.ok(null).build();
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {

    try {
      DonationEntity data = donationService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}
