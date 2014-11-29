//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Grestler main program.
 */
public class Application {

    public static void main( String[] args ) throws Exception {

        // Capture Jetty logging into Log4J2.
        org.eclipse.jetty.util.log.Log.setLog( new org.grestler.application.JettyToLog4J2Logger( "Jetty" ) );

        LOG.info( "Application started." );

        LOG.info( "Random UUID: " + UUID.randomUUID().toString() );

        //try ( H2DataSource dataSource = new H2DataSource() ) {

            new WebServer().run();

        //}

        LOG.info( "Application stopped." );

    }

    private static final Logger LOG = LogManager.getLogger();

}