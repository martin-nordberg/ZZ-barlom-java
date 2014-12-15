//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.grestler.restserver.filters.CacheControlFilter;
import org.grestler.restserver.json.StuffMapper;
import org.grestler.restserver.services.HelloService;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Registry of services for RESTEasy.
 */
public class ApplicationServices
    extends Application {

    public ApplicationServices() {
        // register RESTful services
        singletons.add( new HelloService() );

        // register filters
        singletons.add( new CacheControlFilter() );

        // register mappers
        singletons.add( new StuffMapper() );
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    private static Set<Object> singletons = new HashSet<>();

}

