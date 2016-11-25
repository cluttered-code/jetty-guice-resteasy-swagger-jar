package com.clutteredcode.api.rest.guice;

import com.clutteredcode.api.rest.filter.CorsFilter;
import com.clutteredcode.api.rest.swagger.SwaggerBootstrap;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import io.swagger.jaxrs.config.BeanConfig;
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

        serve("").with(SwaggerBootstrap.class);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0-SNAPSHOT");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setDescription("This is a app.");
        beanConfig.setTitle("Swagger API");
        beanConfig.setHost("localhost:9000");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.clutteredcode.api.rest.resource");
        beanConfig.setContact("cluttered.code@gmail.com");
        beanConfig.setScan(true);

        bind(CorsFilter.class).in(Singleton.class);
        filter("/*").through(CorsFilter.class);
    }
}