package com.company.jersey03.endpoints;

import com.company.common.FilterDescription;
import com.company.common.SortDescription;
import com.company.common.services.util.ObjectUtils;
import com.company.jersey03.models.DonorDTO;
import com.company.jersey03.models.DonorEntity;
import com.company.jersey03.services.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import java.sql.Timestamp;
import java.util.*;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;

@Api(value = "Donors", description = "Donor Management")
@Path("donors")
@Slf4j
public class Donors extends AbstractEndpoint {

  protected DonorService donorService;

  private static final ObjectMapper objectMapper = ObjectUtils.getDefaultObjectMapper();

  @Inject
  public Donors(FieldService fieldService, CustomFieldValueService customFieldValueService,
      DonorService donorService) {
    super(fieldService, customFieldValueService);
    this.donorService = donorService;
  }

  @POST
  @ApiOperation(value = "Register a new Donor. Set id=0", response = DonorDTO.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(DonorDTO donor) {
    try {
      donor.setDateCreated(new Timestamp(System.currentTimeMillis()));
      donor.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      DonorEntity createdDonor =
          donorService.create(new DonorEntity().applyDTO(donor, fieldService));

      if (createdDonor == null) {
        log.error("Cannot create donor from {}", donor);
        return Response.serverError().build();
      }

      return Response.ok(createdDonor.toDTO()).build();
    } catch (Exception e) {
      log.error("Exception during request", e);
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
    }
  }

  @GET
  @ApiOperation(value = "Gets Donor by Id", response = DonorDTO.class, responseContainer = "List")
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
      return Response.serverError()
          .entity(RestTools.getErrorJson("Exception during request", false, Optional.of(e)))
          .build();
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
  @ApiOperation(value = "Updates a Donor identified by id")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "donor", value = "Donor to update", required = true, dataType = "com.company.jersey03.models.DonorDTO", paramType = "body"),
  })
  public Response update(@PathParam("id") Long donorId,
      @ApiParam(hidden = true) String requestBody) {
    log.info("updating donor with id {}", donorId);

    try {
      DonorEntity donorEntity = donorService.getById(donorId);

      if (donorEntity == null) {
        return Response.serverError()
            .entity(RestTools.getErrorJson("donorId does not exist in DB", false, Optional.empty()))
            .build();
      }

      DonorDTO donorDTO;
      try {
        donorDTO = objectMapper.readValue(requestBody, DonorDTO.class);
        donorDTO.setId(donorId);
      } catch (JsonMappingException jme) {
        log.error("Invalid JSON, defaulting to \"{}\" ", jme);
        donorDTO = new DonorDTO();
      }

      List<Long> cfvIdDeletes = donorEntity.findCustomFieldValueDeletes(donorDTO);
      donorEntity.applyDTO(donorDTO, fieldService);
      donorEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));

      if (donorService.update(donorEntity)) {
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
      DonorEntity data = donorService.getById(id);

      return createDeleteResponse(data, null);
    } catch (Exception e) {
      return null;
    }
  }
}
