package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.jersey03.models.CharityDTO;
import com.company.jersey03.models.CharityEntity;
import com.company.jersey03.services.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.annotations.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

@Api(value = "Charities", description = "Charity Management")
@Path("charities")
@Slf4j
public class Charities extends AbstractEndpoint {

  protected CharityService charityService;

  @Inject
  public Charities(FieldService fieldService, CustomFieldValueService customFieldValueService,
      CharityService charityService) {
    super(fieldService, customFieldValueService);
    this.charityService = charityService;
  }

  @POST
  @ApiOperation(value = "Register a new Charity. Set id=0", response = CharityDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(CharityDTO charity) {
    try {
      charity.setDateCreated(new Timestamp(System.currentTimeMillis()));
      charity.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      CharityEntity createdCharity =
          charityService.create(new CharityEntity().applyDTO(charity, fieldService));

      if (createdCharity == null) {
        log.error("Cannot create charity from {}", charity);
        return Response.serverError().build();
      }

      return Response.ok(createdCharity.toDTO()).build();
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @GET
  @ApiOperation(value = "Gets Charity by Id", response = CharityDTO.class, responseContainer = "List")
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@PathParam("id") Long id) {
    try {
      JSONArray result = new JSONArray();
      CharityEntity data = charityService.getById(id);
      if (data != null) {
        result.add(data.toDTO());
      }
      return Response.ok(result).build();
    } catch (Throwable e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @GET
  @ApiOperation(value = "Gets all Charities", response = CharityDTO.class, responseContainer = "List")
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
      List<CharityEntity> charities =
          charityService.getByCriteria(filterDescs, sortDescs, limit, offset);

      for (CharityEntity charity : charities) {
        result.add(charity.toDTO());
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
  @ApiOperation(value = "Updates a Charity identified by id")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "charity", value = "Charity to update", required = true, dataType = "com.company.jersey03.models.CharityDTO", paramType = "body"),
  })
  public Response update(@PathParam("id") Long charityId,
      @ApiParam(hidden = true) String requestBody) {
    log.info("updating charity with id {}", charityId);

    try {
      CharityEntity charityEntity = charityService.getById(charityId);

      if (charityEntity == null) {
        return Response.serverError().entity(
            RestTools.getErrorJson("charityId does not exist in DB", false, Optional.empty()))
            .build();
      }

      CharityDTO charityDTO;
      try {
        charityDTO = objectMapper.readValue(requestBody, CharityDTO.class);
        charityDTO.setId(charityId);
      } catch (JsonMappingException jme) {
        log.error("Invalid JSON, defaulting to \"{}\" ", jme);
        charityDTO = new CharityDTO();
      }

      List<Long> cfvIdDeletes = charityEntity.findCustomFieldValueDeletes(charityDTO);
      charityEntity.applyDTO(charityDTO, fieldService);
      charityEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      if (charityService.update(charityEntity)) {
        for (Long cfvId : cfvIdDeletes) {
          customFieldValueService.delete(cfvId);
        }
        return Response.ok().build();
      } else {
        return Response.serverError().build();
      }
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") Long id) {
    try {
      CharityEntity  charityEntity = charityService.getById(id);

      if (charityEntity == null) {
        return Response.serverError().entity(
            RestTools.getErrorJson("charityId does not exist in DB", false, Optional.empty()))
            .build();
      }

      charityService.delete(id);

      return createDeleteResponse(charityEntity, null);
    } catch (Exception e) {
      return null;
    }
  }
}
