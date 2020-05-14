package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.DonorDTO;
import com.company.jersey03.models.DonorEntity;
import com.company.jersey03.services.DonorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "Donors", description = "Donor Management")
@Path("donors")
@Slf4j
public class Donors extends AbstractEndpoint {

  protected DonorService donorService;

  @Inject
  public Donors(DonorService donorService) {
    this.donorService = donorService;
  }

  @POST
  @ApiOperation(value = "Register a new Donor. Set id=0", response = DonorDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create() {

    return null;
  }

  @GET
  @ApiOperation(value = "Gets all Donors", response = DonorDTO.class, responseContainer = "List")
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      List<DonorDTO> donors = new ArrayList<>();
      DonorEntity data = donorService.getById(id);
      if (data != null) {
        donors.add(data.toDTO());
      }
      return Response.ok(donors).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Donors", response = DonorDTO.class, responseContainer = "List")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetchList(
    @DefaultValue("50") @QueryParam("limit") int limit,
    @DefaultValue("0") @QueryParam("offset") int offset,
    @Context UriInfo uriInfo) {

    try {
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
      List<SortDescription> sortDescs = this.parseSorting(queryParams);

      JSONArray result = new JSONArray();
      List<DonorEntity> donors =
        donorService.getByCriteria(filterDescs, sortDescs, limit, offset);

      for (DonorEntity donor : donors) {
        result.add(donor.toDTO());
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
      DonorEntity data = donorService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}
