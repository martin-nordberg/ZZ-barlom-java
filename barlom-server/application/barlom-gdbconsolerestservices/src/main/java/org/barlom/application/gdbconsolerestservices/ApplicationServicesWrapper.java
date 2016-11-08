//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import dagger.Component;
import org.barlom.infrastructure.utilities.injections.InjectionsFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * Wrapper for a dependency-injected registry of services for RESTEasy. This wrapper exists to be instantiated by JAX-RS
 * reflection since JAX-RS does not integrate with Dagger.
 */
public class ApplicationServicesWrapper
    extends Application {

    /**
     * Constructs a new REST application.
     */
    public ApplicationServicesWrapper() throws Exception {
        this.delegate = new InjectionsFactory().create( Injections.class ).makeApplicationServices();
    }

    @Override
    public Set<Object> getSingletons() {
        return this.delegate.getSingletons();
    }

    @SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
    @Singleton
    @Component( modules = GdbConsoleRestServicesModule.class )
    interface Injections {

        ApplicationServices makeApplicationServices();
    }

    /** The wrapped application that benefits from dependency injection. */
    private final Application delegate;

}

