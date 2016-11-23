package com.clutteredcode.api.rest.resource;

import com.google.inject.servlet.RequestScoped;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by david on 11/20/16.
 */
@RequestScoped
@Path("ping")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    @Inject
    private PingResource() {}

    @GET
    public String ping() {
        return "\"pong\"";
    }
}
