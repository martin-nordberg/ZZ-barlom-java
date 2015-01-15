//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.grestler.restserver.filters.CacheControlFilter;
import org.grestler.restserver.services.metamodel.EdgeTypeQueries;
import org.grestler.restserver.services.metamodel.PackageQueries;
import org.grestler.restserver.services.metamodel.VertexTypeQueries;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Registry of services for RESTEasy.
 */
public class ApplicationServices
    extends Application {

    /**
     * Constructs a new application definition.
     */
    @Inject
    public ApplicationServices(
        PackageQueries packageQueries, VertexTypeQueries vertexTypeQueries, EdgeTypeQueries edgeTypeQueries
    ) {

        // register RESTful query services
        singletons.add( packageQueries );
        singletons.add( vertexTypeQueries );
        singletons.add( edgeTypeQueries );

        // register filters
        singletons.add( new CacheControlFilter() );

    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    /** The resources collected by the Grestler application. */
    private Set<Object> singletons = new HashSet<>();

}

