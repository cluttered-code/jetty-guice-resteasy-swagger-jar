package com.clutteredcode.api.rest.guice;

import com.clutteredcode.api.rest.resource.PingResource;
import com.google.inject.AbstractModule;

/**
 * Created by david on 11/20/16.
 */
public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PingResource.class);
    }
}