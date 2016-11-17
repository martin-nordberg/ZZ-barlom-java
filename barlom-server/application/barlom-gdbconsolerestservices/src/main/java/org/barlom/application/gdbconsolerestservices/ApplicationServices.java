//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import org.barlom.application.gdbconsolerestservices.filters.CacheControlFilter;
import org.barlom.application.gdbconsolerestservices.services.VertexTypeService;
import org.barlom.domain.metamodel.api.IMetamodelFacade;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.Set;

/**
 * Registry of services for RESTEasy.
 */
@SuppressWarnings( "unused" )
public class ApplicationServices
    extends Application {

    /**
     * Constructs a new application services definition.
     */
    public ApplicationServices() {

        this.singletons = new HashSet<>();

        // register filters
        this.singletons.add( new CacheControlFilter() );

    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }

    @Context
    public void setServletContext( ServletContext context ) {

        IMetamodelFacade metamodelFacade =
            (IMetamodelFacade) context.getAttribute( GdbConsoleRestServicesSubsystem.METAMODEL_FACADE );

        // register RESTful query services
        this.singletons.add( new VertexTypeService( metamodelFacade ) );

    }

    /** The resources collected by the Barlom application. */
    private final Set<Object> singletons;

}

