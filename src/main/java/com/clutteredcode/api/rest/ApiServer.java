package com.clutteredcode.api.rest;

import com.clutteredcode.api.rest.guice.ApiModule;
import com.clutteredcode.api.rest.guice.ApiServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by david on 11/20/16.
 */
public class ApiServer implements Runnable {

    private static final XLogger LOG = XLoggerFactory.getXLogger(ApiServer.class);

    private final int port;

    private Injector injector;

    public ApiServer(final int port) {
        this.port = port;
    }

    @Override
    public void run() {
        LOG.entry();
        injector = createInjector();
        final Server server = createServer();
        try {
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

    private Server createServer() {
        final ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));
        handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
        final Server server = new Server(port);
        server.setHandler(handler);
        return server;
    }

    public static void main(final String[] args) {
        LOG.entry();
        new ApiServer(9000).run();
        LOG.exit();
    }
}
