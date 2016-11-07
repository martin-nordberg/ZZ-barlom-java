//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import dagger.Module;
import dagger.Provides;
import org.barlom.application.apputilities.filters.ThreadNameFilter;
import org.barlom.application.gdbconsolerestservices.services.queries.VertexTypeQueries;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.inject.Named;
import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Dagger dependency injection module.
 */
@Module
public class GdbConsoleRestServicesModule {

    @Provides
    public ApplicationServices provideApplicationServices(
        VertexTypeQueries vertexTypeQueries
    ) {
        return new ApplicationServices( vertexTypeQueries );
    }

    @Provides
    public VertexTypeQueries provideVertexTypeQueries(
        // TODO: persistence provider
    ) {
        return new VertexTypeQueries();
    }

    /**
     * Creates the REST service context handler.
     *
     * @return the new context handler.
     */
    @Provides
    @Named( "GdbConsoleRest ServletContextHandler")
    public ServletContextHandler provideWebServiceContextHandler() {

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
