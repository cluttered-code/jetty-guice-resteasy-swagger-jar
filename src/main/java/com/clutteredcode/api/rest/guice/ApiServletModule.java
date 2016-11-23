package com.clutteredcode.api.rest.guice;

import com.clutteredcode.api.rest.filter.CorsFilter;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

/**
 * Created by david on 11/20/16.
 */
public class ApiServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(GuiceResteasyBootstrapServletContextListener.class).in(Singleton.class);

        bind(HttpServletDispatcher.class).in(Singleton.class);
        serve("/*").with(HttpServletDispatcher.class);

        bind(CorsFilter.class).in(Singleton.class);
        filter("/*").through(CorsFilter.class);
    }
}