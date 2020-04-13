package com.company.jersey03.endpoints;

import com.company.jersey03.models.Donor;
import com.company.jersey03.services.DonorService;
import io.swagger.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Api(value = "Donors", description = "Simple example endpoint")
@Path("donors")
public class Donors extends AbstractEndpoint {

    protected DonorService donorService;

    @Inject
    public Donors(DonorService donorService) {
        this.donorService = donorService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a list of donors",
            notes = "Returns all known donors",
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

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Updates a Donor identified by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "donor", value = "Donor to update", required = true, dataType = "com.company.jersey03.models.Donor", paramType = "body"),
    })
    public Response update(@PathParam("id") long donorId, @ApiParam(hidden = true) String requestBody) {
        String results = "Hello There - PUT on Donor " + donorId;
        return Response.status(Response.Status.OK).entity(results).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Deletes a Donor identified by id")
    public Response delete(@PathParam("id") Integer id) {

        Donor data = donorService.getDonor(id);

        return createDeleteResponse(data, null);
    }
}
