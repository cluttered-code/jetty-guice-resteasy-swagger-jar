package com.clutteredcode.api.rest.resource;

import com.google.inject.Singleton;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by david on 11/20/16.
 */
@Path("ping")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    public static final String PONG = "\"pong\"";

    @Inject
    private PingResource() {
    }

    @GET
    public String ping() {
        return PONG;
    }

    @GET
    @Path("/async")
    public void asyncPing(@Suspended final AsyncResponse response) {
        new Thread(() ->
                response.resume(Response.ok(PONG).build())
        ).start();
    }
}