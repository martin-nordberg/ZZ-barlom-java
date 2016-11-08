//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import org.barlom.application.apputilities.filters.ThreadNameFilter;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.net.MalformedURLException;
import java.util.EnumSet;

/**
 * Builder class creates and configures a new REST server for Barlom-GDB Console.
 */
public final class GdbConsoleRestServicesBuilder {

    /** Static utility class. */
    private GdbConsoleRestServicesBuilder() {
    }

    /**
     * Creates a Jetty server for REST services.
     *
     * @param contexts the context collection to be configured for REST web services.
     *
     * @throws java.net.MalformedURLException if the configuration is broken.
     */
    public static void makeRestServer( ContextHandlerCollection contexts ) throws MalformedURLException {

        // Serve dynamic content.
        ServletContextHandler webServiceContext = GdbConsoleRestServicesBuilder.makeWebServiceContextHandler();

        // Insert the REST context.
        contexts.addHandler( webServiceContext );

    }

    /**
     * Creates the REST service context handler.
     *
     * @return the new context handler.
     */
    private static ServletContextHandler makeWebServiceContextHandler() {

        // Set the context for dynamic content.
        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/barlomgdbconsolecontent" );

        // Add a RESTEasy servlet for the dynamic content.
        ServletHolder servletHolder = new ServletHolder( new HttpServletDispatcher() );
        servletHolder.setInitParameter(
            "javax.ws.rs.Application", "org.barlom.application.gdbconsolerestservices.ApplicationServicesWrapper"
        );
        result.addServlet( servletHolder, "/*" );

        // Rename request threads for better logging.
        result.addFilter( ThreadNameFilter.class, "/*", EnumSet.of( DispatcherType.REQUEST ) );

        return result;

    }

}
