//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Grestler main program.
 */
public class Application {

    /** Executes the Grestler application. */
    public static void main( String[] args ) throws Exception {

        // Capture Jetty logging into Log4J2.
        org.eclipse.jetty.util.log.Log.setLog( new org.grestler.application.JettyToLog4J2Logger( "Jetty" ) );

        LOG.info( "Application started." );

        //try ( H2DataSource dataSource = new H2DataSource() ) {

            // Run the web server.
            new WebServer().run();

        //}

        LOG.info( "Application stopped." );

    }

    private static final Logger LOG = LogManager.getLogger();

}