//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import dagger.Module;
import dagger.Provides;
import org.barlom.application.restserver.RestServerModule;
import org.barlom.persistence.h2database.H2DatabaseModule;
import org.barlom.presentation.adminserver.AdminServerModule;

/**
 * Dagger dependency injection module for top level application configuration.
 */
@Module(
    injects = Application.class,
    includes = { AdminServerModule.class, RestServerModule.class, H2DatabaseModule.class } )
public class ApplicationModule {

    /** Constructs the main web server. */
    @Provides
    public WebServer provideWebServer() {
        return new WebServer();
    }

}
