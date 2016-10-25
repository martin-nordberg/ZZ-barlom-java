//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.restserver;

import org.barlom.application.restserver.filters.CacheControlFilter;
import org.barlom.application.restserver.services.commands.MetamodelCommands;
import org.barlom.application.restserver.services.queries.AttributeTypeQueries;
import org.barlom.application.restserver.services.queries.DirectedEdgeTypeQueries;
import org.barlom.application.restserver.services.queries.PackageQueries;
import org.barlom.application.restserver.services.queries.UndirectedEdgeTypeQueries;
import org.barlom.application.restserver.services.queries.VertexTypeQueries;

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

    /** The resources collected by the Barlom application. */
    private final Set<Object> singletons;

}

