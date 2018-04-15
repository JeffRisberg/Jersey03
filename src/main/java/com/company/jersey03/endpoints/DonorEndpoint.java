package com.company.jersey03.endpoints;

import com.company.jersey03.common.FilterDesc;
import com.company.jersey03.common.SortDesc;
import com.company.jersey03.models.Charity;
import com.company.jersey03.models.Donor;
import com.company.jersey03.services.DonorService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.List;

@Api(value = "Donors", description = "Simple example endpoint")
@Path("donors")
public class DonorEndpoint extends AbstractEndpoint {

    protected DonorService donorService;

    @Inject
    public DonorEndpoint(DonorService donorService) {
        this.donorService = donorService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a donor",
            notes = "Finds a donor by id",
            response = Donor.class,
            responseContainer = "Map")
    public Response fetch(@PathParam("id") int id) {

        Donor data = donorService.getDonor(id);

        return createEntityResponse(data, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a list of donors",
            notes = "Finds a list of donors",
            response = Donor.class,
            responseContainer = "List")
    public Response fetchList(
            @DefaultValue("50") @QueryParam("limit") int limit,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("") @QueryParam("sort") String sortStr,
            @Context UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        List<FilterDesc> filterDescs = this.parseFiltering(queryParams);
        List<SortDesc> sortDescs = this.parseSortStr(sortStr);

        List<Donor> data = donorService.getDonors(limit, offset, filterDescs);
        long totalCount = donorService.getDonorsCount(filterDescs);

        return createEntityListResponse(data, totalCount, limit, offset, null);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String requestBody) {
        String results = "Hello There";
        return Response.status(Response.Status.OK).entity(results).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {

        Donor data = donorService.getDonor(id);

        return createDeleteResponse(data, null);
    }
}
