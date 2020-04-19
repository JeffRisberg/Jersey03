package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.CharityDTO;
import com.company.jersey03.models.CharityEntity;
import com.company.jersey03.services.CharityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

@Api(value = "Charities", description = "charity management")
@Path("charities")
@Slf4j
public class Charities extends AbstractEndpoint {

  protected CharityService charityService;

  @Inject
  public Charities(CharityService charityService) {
    this.charityService = charityService;
  }

  @POST
  @ApiOperation(value = "Register a new DataSource. Set id=0", response = CharityDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response x() {
    return null;
  }

  @GET
  @ApiOperation(value = "Gets all Charities", response = CharityDTO.class, responseContainer = "List")
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      JSONArray result = new JSONArray();
      CharityEntity data = charityService.getById(id);
      if (data != null) {
        result.add(data.toJSON());
      }
      return Response.ok(result).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError().entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e))).build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Charities", response = CharityDTO.class, responseContainer = "List")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetchList(
    @DefaultValue("50") @QueryParam("limit") int limit,
    @DefaultValue("0") @QueryParam("offset") int offset,
    @DefaultValue("") @QueryParam("sort") String sortStr,
    @Context UriInfo uriInfo) {

    MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
    List<FilterDescription> filterDescs = this.parseFiltering(queryParams);
    List<SortDescription> sortDescs = this.parseSortStr(sortStr);

    try {
      JSONArray result = new JSONArray();
      List<CharityEntity> charities = charityService.getAll(limit, offset);

      for (CharityEntity charity : charities) {
        result.add(charity.toJSON());
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
      CharityEntity data = charityService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}
