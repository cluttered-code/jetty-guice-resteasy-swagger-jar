package com.clutteredcode.api.rest.guice;

import com.clutteredcode.api.rest.json.GsonMessageBodyHandler;
import com.google.common.reflect.ClassPath;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by david on 11/20/16.
 */
public class ApiModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(ApiModule.class);
    private static final String RESOURCE_PACKAGE = "com.clutteredcode.api.rest.resource";

    @Override
    protected void configure() {
        bind(GsonMessageBodyHandler.class);

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