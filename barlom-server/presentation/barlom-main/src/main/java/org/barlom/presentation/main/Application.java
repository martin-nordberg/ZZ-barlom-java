//
// (C) Copyright 2014-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.infrastructure.utilities.uuids.Uuids;
import org.barlom.presentation.webutilities.logging.Log4j2JettyLogger;

/**
 * Barlom main program.
 */
public final class Application {

    private Application() {
        this.webServer = new WebServer();
    }

    /**
     * Executes the Barlom application.
     *
     * @param args the command line arguments
     *
     * @throws Exception if anything goes wrong without handling.
     */
    public static void main( String[] args ) throws Exception {

        // Capture Jetty logging into Log4J2.
        org.eclipse.jetty.util.log.Log.setLog( new Log4j2JettyLogger( "Jetty" ) );

        Application app = new Application();

        // do extra experimental stuff
        Application.experiment();

        // Run the application.
        app.run();

    }

    /**
     * Placeholder for extra code when experimenting.
     */
    private static void experiment() {
        Application.LOG.info( "UUID: " + Uuids.makeUuidWithReservedBlock() );
    }

    /**
     * Runs the main application.
     *
     * @throws Exception if anything goes wrong without handling.
     */
    private void run() throws Exception {

        Application.LOG.info( "Application started." );

        // Run the web server.
        this.webServer.run();

        Application.LOG.info( "Application stopped." );

    }

    private static final Logger LOG = LogManager.getLogger();

    /**
     * The web server to be run by this application.
     */
    private final WebServer webServer;

}