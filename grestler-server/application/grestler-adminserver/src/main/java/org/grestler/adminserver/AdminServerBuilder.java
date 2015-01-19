//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.adminserver;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.grestler.webutilities.filters.ThreadNameFilter;
import org.grestler.webutilities.servlets.ShutdownServlet;

import javax.servlet.DispatcherType;
import java.net.MalformedURLException;
import java.util.EnumSet;

/**
 * Builder class creates and configures the admin server.
 */
public final class AdminServerBuilder {

    /** Static utility class. */
    private AdminServerBuilder() {
    }

    /**
     * Creates a Jetty server for administration.
     *
     * @param webServer The top level web server to be shutdown via the admin console.
     * @param contexts  the context collection to be configured with the Grestler admin application.
     *
     * @throws java.net.MalformedURLException if the configuration is broken.
     */
    public static void makeAdminServer( AutoCloseable webServer, ContextHandlerCollection contexts )
        throws MalformedURLException {

        // Serve static content.
        ContextHandler staticContext = AdminServerBuilder.makeStaticContextHandler();

        // Serve dynamic content plus a shutdown handler.
        ServletContextHandler dynamicContext = AdminServerBuilder.makeDynamicContextHandler( webServer );

        // Combine the two contexts plus a shutdown handler.
        contexts.addHandler( staticContext );
        contexts.addHandler( dynamicContext );

    }

    /**
     * Creates the context handler for the admin server.
     *
     * @return the new context handler.
     */
    private static ServletContextHandler makeDynamicContextHandler( AutoCloseable webServer ) {

        // Set the context for dynamic content
        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/grestleradmin" );

        // Add a shutdown servlet for the dynamic content.
        ServletHolder servletHolder = new ServletHolder( new ShutdownServlet( webServer ) );
        result.addServlet( servletHolder, "/exit" );

        // TBD: Any other stuff needed for admin ...

        // Rename request threads for better logging.
        result.addFilter( ThreadNameFilter.class, "/*", EnumSet.of( DispatcherType.REQUEST ) );

        return result;

    }

    /**
     * Creates the static file server context handler.
     *
     * @return the new context handler.
     *
     * @throws java.net.MalformedURLException if things are configured incorrectly.
     */
    private static ContextHandler makeStaticContextHandler() throws MalformedURLException {

        // Set the context for static content.
        ContextHandler fileServerContext = new ContextHandler();
        fileServerContext.setContextPath( "/grestler" );

        // Set the source for static content.
        ResourceHandler fileResourceHandler = new ResourceHandler();
        fileResourceHandler.setCacheControl( "max-age=3600,public" );
        // TODO: Make internal resource
        fileResourceHandler.setBaseResource(
            Resource.newResource(
                "/home/mnordberg/Workspace/grestler/grestler-client/grestler-adminclient/src/main/webapp"
            )
        );
        fileServerContext.setHandler( fileResourceHandler );

        return fileServerContext;

    }

}
