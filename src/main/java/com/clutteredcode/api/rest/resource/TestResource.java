package com.clutteredcode.api.rest.resource;

import com.google.inject.servlet.RequestScoped;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by david on 11/20/16.
 */
@RequestScoped
@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @Inject
    private TestResource() {}

    @GET
    public String test(@QueryParam("name") @DefaultValue("Stranger") final String name) {
        return "\"" + name + "\"";
    }
}
