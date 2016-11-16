//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.gdbconsoleserver;

import org.barlom.application.apputilities.filters.ThreadNameFilter;
import org.barlom.presentation.webutilities.servlets.ShutdownServlet;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.DispatcherType;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

/**
 * Subsystem facade class creates and configures the GDB console server.
 */
public final class GdbConsoleServerSubsystem {

    public GdbConsoleServerSubsystem() {
    }

    /**
     * Creates a Jetty server for Barlom-GDB Console.
     *
     * @param webServer The top level web server to be shutdown via the console.
     */
    public Collection<ContextHandler> makeContextHandlers( AutoCloseable webServer ) {

        Collection<ContextHandler> result = new ArrayList<>();

        // Serve static content.
        result.add( GdbConsoleServerSubsystem.makeStaticContextHandler() );

        // Serve dynamic content plus a shutdown handler.
        result.add( GdbConsoleServerSubsystem.makeDynamicContextHandler( webServer ) );

        return result;
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
     */
    private static ContextHandler makeStaticContextHandler() {

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
        try {
            fileResourceHandler.setBaseResource(
                Resource.newResource(
                    "/home/mnordberg/workspace/barlom/barlom-client/barlom-gdbconsole/webapp"
                )
            );
        }
        catch ( MalformedURLException e ) {
            throw new RuntimeException( "Failed to make static context handler", e );
        }

        fileServerContext.setHandler( fileResourceHandler );

        return fileServerContext;

    }

}
