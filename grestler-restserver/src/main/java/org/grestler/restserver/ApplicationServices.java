//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.grestler.restserver.filters.CacheControlFilter;
import org.grestler.restserver.services.metamodel.PackageQueries;

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
    public ApplicationServices( PackageQueries packageQueries ) {

        // register RESTful query services
        singletons.add( packageQueries );

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

