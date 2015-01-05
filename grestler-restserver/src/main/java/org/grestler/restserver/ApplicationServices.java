//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.grestler.restserver.filters.CacheControlFilter;
import org.grestler.restserver.services.metamodel.PackageQueries;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Registry of services for RESTEasy.
 */
public class ApplicationServices
    extends Application {

    public ApplicationServices() {

        // register RESTful query services
        singletons.add( new PackageQueries() );

        // register filters
        singletons.add( new CacheControlFilter() );

    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    private Set<Object> singletons = new HashSet<>();

}

