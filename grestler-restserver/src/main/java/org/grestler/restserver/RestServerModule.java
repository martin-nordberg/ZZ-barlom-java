//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import dagger.Module;
import dagger.Provides;
import org.grestler.restserver.services.metamodel.PackageQueries;

/**
 * Dagger dependency injection module.
 */
@Module(
    injects = ApplicationServices.class,
    library = true )
public class RestServerModule {

    @Provides
    public PackageQueries providePackageQueries() {
        return new PackageQueries();
    }

}
