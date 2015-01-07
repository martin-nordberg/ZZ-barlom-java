//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import dagger.ObjectGraph;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Wrapper for a dependency-injected registry of services for RESTEasy.
 */
public class ApplicationServicesWrapper
    extends Application {

    /**
     * Constructs a new REST application.
     */
    public ApplicationServicesWrapper() {
        this.delegate = objectGraph.get( ApplicationServices.class );
    }

    public static void registerObjectGraph( ObjectGraph objectGraph ) {
        ApplicationServicesWrapper.objectGraph = objectGraph;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.delegate.getSingletons();
    }

    /**
     * The Dagger dependency injection service. Explicitly used since no clear way to provide ApplicationServices
     * itself.
     */
    private static ObjectGraph objectGraph;

    /** The wrapped application that benefits from dependency injection. */
    private final Application delegate;

}

