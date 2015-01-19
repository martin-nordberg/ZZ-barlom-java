//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import dagger.Module;
import dagger.Provides;
import org.grestler.h2database.H2DatabaseModule;
import org.grestler.restserver.RestServerModule;

/**
 * Dagger dependency injection module for top level application configuration.
 */
@Module(
    injects = Application.class,
    includes = { RestServerModule.class, H2DatabaseModule.class } )
public class ApplicationModule {

    /** Constructs the main web server. */
    @Provides
    public WebServer provideWebServer() {
        return new WebServer();
    }

}
