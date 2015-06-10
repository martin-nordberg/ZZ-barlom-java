//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application.restserver;

import org.grestler.application.restserver.filters.CacheControlFilter;
import org.grestler.application.restserver.services.commands.MetamodelCommands;
import org.grestler.application.restserver.services.queries.AttributeTypeQueries;
import org.grestler.application.restserver.services.queries.DirectedEdgeTypeQueries;
import org.grestler.application.restserver.services.queries.PackageQueries;
import org.grestler.application.restserver.services.queries.UndirectedEdgeTypeQueries;
import org.grestler.application.restserver.services.queries.VertexTypeQueries;

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
        PackageQueries packageQueries,
        VertexTypeQueries vertexTypeQueries,
        DirectedEdgeTypeQueries directedEdgeTypeQueries,
        UndirectedEdgeTypeQueries undirectedEdgeTypeQueries,
        AttributeTypeQueries attributeTypeQueries,
        MetamodelCommands metamodelCommands
    ) {

        this.singletons = new HashSet<>();

        // register RESTful query services
        this.singletons.add( packageQueries );
        this.singletons.add( vertexTypeQueries );
        this.singletons.add( directedEdgeTypeQueries );
        this.singletons.add( undirectedEdgeTypeQueries );
        this.singletons.add( attributeTypeQueries );

        // register RESTful command services
        this.singletons.add( metamodelCommands );

        // register filters
        this.singletons.add( new CacheControlFilter() );

    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }

    /** The resources collected by the Grestler application. */
    private final Set<Object> singletons;

}

