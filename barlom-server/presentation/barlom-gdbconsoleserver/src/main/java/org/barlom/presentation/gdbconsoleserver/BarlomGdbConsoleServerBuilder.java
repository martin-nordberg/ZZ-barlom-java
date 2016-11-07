//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.gdbconsoleserver;

import org.barlom.application.apputilities.filters.ThreadNameFilter;
import org.barlom.presentation.webutilities.servlets.ShutdownServlet;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.DispatcherType;
import java.net.MalformedURLException;
import java.util.EnumSet;

/**
 * Builder class creates and configures the admin server.
 * TODO: Seems like this stuff could be dependency-injected in a backwards sort of way.
 */
public final class BarlomGdbConsoleServerBuilder {

    /** Static utility class. */
    private BarlomGdbConsoleServerBuilder() {
    }

    /**
     * Creates a Jetty server for Barlom-GDB Console.
     *
     * @param webServer The top level web server to be shutdown via the console.
     * @param contexts  the context collection to be configured with the Barlom console application.
     *
     * @throws java.net.MalformedURLException if the configuration is broken.
     */
    public static void makeConsole( AutoCloseable webServer, ContextHandlerCollection contexts )
        throws MalformedURLException {

        // Serve static content.
        ContextHandler staticContext = BarlomGdbConsoleServerBuilder.makeStaticContextHandler();

        // Serve dynamic content plus a shutdown handler.
        ServletContextHandler dynamicContext = BarlomGdbConsoleServerBuilder.makeDynamicContextHandler( webServer );

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
        result.setContextPath( "/barlomgdbconsole" );

        // Add a shutdown servlet for the dynamic content.
        ServletHolder servletHolder = new ServletHolder(
            new ShutdownServlet(
                webServer,
                "Barlom application server stopping..."
            )
        );
        result.addServlet( servletHolder, "/exit" );

        // TODO: other stuff will be needed

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
        fileServerContext.setContextPath( "/barlomgdbconsole" );
        fileServerContext.setWelcomeFiles( new String[]{ "index.html" } );

        // Set the source for static content.
        ResourceHandler fileResourceHandler = new ResourceHandler();
        fileResourceHandler.setCacheControl( "max-age=3600,public" );

        // TODO: only for development to avoid cache clearing:
        fileResourceHandler.setCacheControl( "max-age=0, no-cache, no-store" );

        // TODO: Make internal resource
        fileResourceHandler.setBaseResource(
            Resource.newResource(
                "/home/mnordberg/workspace/barlom/barlom-client/barlom-gdbconsole/webapp"
            )
        );

        fileServerContext.setHandler( fileResourceHandler );

        return fileServerContext;

    }

}
