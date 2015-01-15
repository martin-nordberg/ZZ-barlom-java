//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import dagger.ObjectGraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.restserver.ApplicationServicesWrapper;
import org.grestler.utilities.uuids.Uuids;
import org.grestler.webutilities.logging.Log4j2JettyLogger;

import javax.inject.Inject;

/**
 * Grestler main program.
 */
public class Application {

    @Inject
    public Application( WebServer webServer ) {
        this.webServer = webServer;
    }

    /**
     * Executes the Grestler application.
     *
     * @param args the command line arguments
     *
     * @throws Exception if anything goes wrong without handling.
     */
    public static void main( String[] args ) throws Exception {

        // Capture Jetty logging into Log4J2.
        org.eclipse.jetty.util.log.Log.setLog( new Log4j2JettyLogger( "Jetty" ) );

        // Initialize dependency injection.
        ObjectGraph objectGraph = ObjectGraph.create( new ApplicationModule() );
        Application app = objectGraph.get( Application.class );

        // Register dependency injection at lower levels which cannot be injected directly
        ApplicationServicesWrapper.registerObjectGraph( objectGraph );

        // do extra experimental stuff
        app.experiment();

        // Run the application.
        app.run();

    }

    /**
     * Placeholder for extra code when experimenting.
     */
    private void experiment() {
        LOG.info( "UUID: " + Uuids.makeUuidWithReservedBlock() );
    }

    /**
     * Runs the main application.
     *
     * @throws Exception if anything goes wrong without handling.
     */
    private void run() throws Exception {

        LOG.info( "Application started." );

        // Run the web server.
        this.webServer.run();

        LOG.info( "Application stopped." );

    }

    private static final Logger LOG = LogManager.getLogger();

    /**
     * The web server to be run by this application.
     */
    private final WebServer webServer;

}