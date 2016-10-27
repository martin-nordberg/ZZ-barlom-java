//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices;

import dagger.Module;
import dagger.Provides;
import org.barlom.application.gdbconsolerestservices.services.queries.VertexTypeQueries;

/**
 * Dagger dependency injection module.
 */
@Module(
    complete = false,
    injects = ApplicationServices.class,
    library = true )
public class GdbConsoleRestServicesModule {

    @Provides
    public VertexTypeQueries provideVertexTypeQueries(
        // TODO: persistence provider
    ) {
        return new VertexTypeQueries();
    }

}
