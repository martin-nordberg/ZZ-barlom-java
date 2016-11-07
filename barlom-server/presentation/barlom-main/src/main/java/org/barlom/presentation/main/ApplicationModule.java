//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import dagger.Module;
import dagger.Provides;
import org.barlom.application.gdbconsolerestservices.GdbConsoleRestServicesModule;

/**
 * Dagger dependency injection module for top level application configuration.
 */
@Module(
    injects = Application.class,
    includes = {
        GdbConsoleRestServicesModule.class,
    } )
public class ApplicationModule {

    /** Constructs the main web server. */
    @Provides
    public WebServer provideWebServer() {
        return new WebServer();
    }

}
