package com.company.jersey03.endpoints;

import com.company.jersey03.common.model.jooq.query.FilterDesc;
import com.company.jersey03.common.model.jooq.query.SortDesc;
import com.company.jersey03.models.Donor;
import com.company.jersey03.services.DonorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Api
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
    public Response fetch(@PathParam("id") Integer id) {

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

        List<Donor> data = donorService.getDonors(limit, offset, sortDescs, filterDescs);
        long totalCount = donorService.getDonorsCount(filterDescs);

        return createEntityListResponse(data, totalCount, limit, offset, null);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {

        Donor data = donorService.getDonor(id);

        return createDeleteResponse(data, null);
    }
}
