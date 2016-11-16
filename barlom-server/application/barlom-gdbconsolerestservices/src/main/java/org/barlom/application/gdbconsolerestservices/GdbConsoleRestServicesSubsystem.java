//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import org.barlom.application.apputilities.filters.ThreadNameFilter;
import org.barlom.domain.metamodel.api.IMetamodelFacade;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Subsystem facade class creates and configures a new REST server for Barlom-GDB Console.
 */
public final class GdbConsoleRestServicesSubsystem {

    /**
     * Interface defining theexternal dependencies of this subsystem.
     */
    @SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
    public interface IDependencies {

        /**
         * @return a metamodel facade instance for persistence of the entities queried via this REST server.
         */
        IMetamodelFacade provideMetamodelFacade();

    }

    /**
     * Constructs a new instance of this subsystem.
     * @param dependencies provider of subsystem dependencies.
     */
    public GdbConsoleRestServicesSubsystem( IDependencies dependencies ) {
        this.dependencies = dependencies;
    }

    /**
     * Creates the REST service context handler.
     *
     * @return the new context handler.
     */
    public ContextHandler makeWebServiceContextHandler() {

        // Set the context for dynamic content.
        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/barlomgdbconsolecontent" );

        // Pass a metamodel facade down to the JAX-RS application.
        result.setAttribute(
            GdbConsoleRestServicesSubsystem.METAMODEL_FACADE, this.dependencies.provideMetamodelFacade()
        );

        // Add a RESTEasy servlet for the dynamic content.
        ServletHolder servletHolder = new ServletHolder( new HttpServletDispatcher() );
        servletHolder.setInitParameter(
            "javax.ws.rs.Application", "org.barlom.application.gdbconsolerestservices.ApplicationServices"
        );
        result.addServlet( servletHolder, "/*" );

        // Rename request threads for better logging.
        result.addFilter( ThreadNameFilter.class, "/*", EnumSet.of( DispatcherType.REQUEST ) );

        return result;

    }

    /**
     * Attribute name for the metamodel facade passed from here into JAX-RS application services.
     */
    static final String METAMODEL_FACADE = "org.barlom.application.gdbconsolerestservices.metamodelFacade";

    /**
     * The provider of dependencies for this subsystem.
     */
    private final IDependencies dependencies;

}
