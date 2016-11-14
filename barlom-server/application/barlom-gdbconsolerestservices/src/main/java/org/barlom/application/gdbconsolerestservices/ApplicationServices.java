//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import org.barlom.application.gdbconsolerestservices.filters.CacheControlFilter;
import org.barlom.application.gdbconsolerestservices.services.queries.VertexTypeQueries;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Registry of services for RESTEasy.
 */
class ApplicationServices
    extends Application {

    /**
     * Constructs a new application definition.
     */
    ApplicationServices(
        VertexTypeQueries vertexTypeQueries
    ) {

        this.singletons = new HashSet<>();

        // register RESTful query services
        this.singletons.add( vertexTypeQueries );

        // register filters
        this.singletons.add( new CacheControlFilter() );

    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }

    /** The resources collected by the Barlom application. */
    private final Set<Object> singletons;

}

