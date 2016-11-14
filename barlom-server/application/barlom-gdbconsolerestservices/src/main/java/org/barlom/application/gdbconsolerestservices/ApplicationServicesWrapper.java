//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import org.barlom.application.gdbconsolerestservices.services.queries.VertexTypeQueries;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Wrapper for a dependency-injected registry of services for RESTEasy. This wrapper exists to be instantiated by JAX-RS
 * reflection since JAX-RS by itself does not have any way to initialize dependencies.
 */
@SuppressWarnings( "unused" )
public class ApplicationServicesWrapper
    extends Application {

    /**
     * Constructs a new REST application.
     */
    public ApplicationServicesWrapper() throws Exception {
        this.delegate = new ApplicationServices( new VertexTypeQueries() );
    }

    @Override
    public Set<Object> getSingletons() {
        return this.delegate.getSingletons();
    }

    /** The wrapped application that benefits from dependency injection. */
    private final Application delegate;

}

