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

        // do extra experimental stuff
        Application.experiment();

        // Run the application.
        Application.LOG.info( "Application started." );

        new ApplicationSystem().makeWebServer().run();

        Application.LOG.info( "Application stopped." );

    }

    /**
     * Placeholder for extra code when experimenting.
     */
    private static void experiment() {
        Application.LOG.info( "UUID: " + Uuids.makeUuidWithReservedBlock() );
    }

    private static final Logger LOG = LogManager.getLogger();


}