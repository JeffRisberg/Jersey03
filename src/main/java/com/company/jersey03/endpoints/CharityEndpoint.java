package com.company.jersey03.endpoints;

import com.company.jersey03.common.model.jooq.query.FilterDesc;
import com.company.jersey03.common.model.jooq.query.SortDesc;
import com.company.jersey03.models.Charity;
import com.company.jersey03.services.CharityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Api
@Path("charities")
public class CharityEndpoint extends AbstractEndpoint {

    protected CharityService charityService;

    @Inject
    public CharityEndpoint(CharityService charityService) {
        this.charityService = charityService;
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Finds a charity",
            notes = "Finds a charity by id",
            response = Charity.class,
            responseContainer = "Map")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetch(@PathParam("id") Integer id) {

        Charity data = charityService.getCharity(id);

        return createEntityResponse(data, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a list of charities",
            notes = "Finds a list of charities",
            response = Charity.class,
            responseContainer = "List")
    public Response fetchList(
            @DefaultValue("50") @QueryParam("limit") int limit,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("") @QueryParam("sort") String sortStr,
            @Context UriInfo uriInfo) {

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        List<FilterDesc> filterDescs = this.parseFiltering(queryParams);
        List<SortDesc> sortDescs = this.parseSortStr(sortStr);

        List<Charity> data = charityService.getCharities(limit, offset, filterDescs, sortDescs);
        long totalCount = charityService.getCharitiesCount(filterDescs);

        return createEntityListResponse(data, totalCount, limit, offset, null);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {

        Charity data = charityService.getCharity(id);

        return createDeleteResponse(data, null);
    }
}
