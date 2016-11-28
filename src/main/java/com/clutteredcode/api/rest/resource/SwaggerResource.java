package com.clutteredcode.api.rest.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by david on 11/27/16.
 */
@Path("/")
@Singleton
public class SwaggerResource {

    @Inject
    private SwaggerResource() {}

    @GET
    public Response redirect() throws URISyntaxException {
        return Response.temporaryRedirect(new URI("../docs?url=%2Fapi/swagger.json")).build();
    }
}