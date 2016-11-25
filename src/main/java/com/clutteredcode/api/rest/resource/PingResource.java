package com.clutteredcode.api.rest.resource;

import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

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
@Api("Ping")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {

    public static final String PONG = "\"pong\"";
    private static final XLogger LOG = XLoggerFactory.getXLogger(PingResource.class);

    @Inject
    private PingResource() {
    }

    @GET
    public String ping() {
        LOG.entry();
        LOG.exit(PONG);
        return PONG;
    }

    @GET
    @Path("/async")
    public void asyncPing(@Suspended final AsyncResponse response) {
        new Thread(() -> {
            LOG.entry();
            response.resume(Response.ok(PONG).build());
            LOG.exit(PONG);
        }).start();
    }
}