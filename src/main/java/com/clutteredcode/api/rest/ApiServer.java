package com.clutteredcode.api.rest;

import com.clutteredcode.api.rest.guice.ApiModule;
import com.clutteredcode.api.rest.guice.ApiServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javax.servlet.DispatcherType;
import java.net.URISyntaxException;
import java.util.EnumSet;

/**
 * Created by david on 11/20/16.
 */
public class ApiServer implements Runnable {

    private static final XLogger LOG = XLoggerFactory.getXLogger(ApiServer.class);

    // TODO: get value from build
    private static final String SWAGGER_UI_VERSION = "2.2.6";

    private final int port;

    private Injector injector;

    public ApiServer(final int port) {
        this.port = port;
    }

    @Override
    public void run() {
        LOG.entry();
        injector = createInjector();
        try {
            final Server server = createServer();
            server.start();
            server.join();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        LOG.exit();
    }

    private Injector createInjector() {
        return Guice.createInjector(
                Stage.PRODUCTION,
                new ApiServletModule(),
                new ApiModule());
    }

    private Server createServer() throws URISyntaxException {
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{buildApiContext(), buildSwaggerContext()});
        final Server server = new Server(port);
        server.setHandler(contexts);
        return server;
    }

    private ContextHandler buildApiContext() {
        final ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/api");
        handler.addEventListener(getGuiceResteasyListener());
        handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        return handler;
    }

    private ContextHandler buildSwaggerContext() throws URISyntaxException {
        ResourceHandler rh = new ResourceHandler();
        rh.setResourceBase(ApiServer.class.getClassLoader()
                .getResource("META-INF/resources/webjars/swagger-ui/" + ApiServer.SWAGGER_UI_VERSION)
                .toURI().toString());
        ContextHandler context = new ContextHandler();
        context.setContextPath("/docs");
        context.setHandler(rh);
        return context;
    }

    private GuiceResteasyBootstrapServletContextListener getGuiceResteasyListener() {
        return injector.getInstance(GuiceResteasyBootstrapServletContextListener.class);
    }

    public static void main(final String[] args) {
        LOG.entry();
        new ApiServer(9000).run();
        LOG.exit();
    }
}