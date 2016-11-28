package com.clutteredcode.api.rest.guice;

import com.clutteredcode.api.rest.resource.PingResource;
import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by david on 11/20/16.
 */
public class ApiModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(ApiModule.class);
    private static final String RESOURCE_PACKAGE = PingResource.class.getPackage().getName();

    @Override
    protected void configure() {
        // Swagger
        bind(ApiListingResource.class);
        bind(SwaggerSerializers.class);

        try {
            loadResources();
        } catch (final IOException e) {
            LOG.error("Unable to load rest resources: {}", e.getLocalizedMessage());
        }
    }

    private void loadResources() throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final ClassPath classPath = ClassPath.from(classLoader);
        final Collection<ClassPath.ClassInfo> classInfoCollection = classPath.getTopLevelClasses(RESOURCE_PACKAGE);
        for (final ClassPath.ClassInfo classInfo : classInfoCollection) {
            bind(classInfo.load()).asEagerSingleton();
        }
    }
}