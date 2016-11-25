package com.clutteredcode.api.rest.resource;

import com.clutteredcode.api.rest.model.Person;
import com.google.inject.Inject;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by david on 11/24/16.
 */
@Path("people")
@Api("People")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

    @Inject
    private PeopleResource() {}

    @GET
    public Person getPerson(@QueryParam("firstName") @DefaultValue("John") final String firstName,
                            @QueryParam("lastName") @DefaultValue("Doe") final String lastName) {
        return new Person(firstName, lastName);
    }
}