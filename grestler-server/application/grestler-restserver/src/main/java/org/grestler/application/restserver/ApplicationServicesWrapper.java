//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application.restserver;

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
        this.delegate = ApplicationServicesWrapper.objectGraph.get( ApplicationServices.class );
    }

    /**
     * Registers the Dagger object graph that will be used to inject everything below the application itself. This
     * clumsy hack is to work around the seeming inability to dependency inject the application object itself without a
     * JEE container.
     *
     * @param objGraph the already initialized Dagger object graph for dependency injection.
     */
    public static void registerObjectGraph( ObjectGraph objGraph ) {
        ApplicationServicesWrapper.objectGraph = objGraph;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.delegate.getSingletons();
    }

    /**
     * The Dagger dependency injection service. Explicitly used since no clear way to inject Application itself.
     */
    private static ObjectGraph objectGraph = null;

    /** The wrapped application that benefits from dependency injection. */
    private final Application delegate;

}

