//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.dbutilities.IDataSource;
import org.grestler.dbutilities.IDataSourceDefinition;
import org.grestler.h2database.H2DataSourceDefinition;
import org.grestler.utilities.uuids.Uuids;
import org.grestler.webutilities.logging.Log4j2JettyLogger;

/**
 * Grestler main program.
 */
public class Application {

    /**
     * Executes the Grestler application.
     *
     * @param args the command line arguments
     *
     * @throws Exception if anything goes wrong without handling.
     */
    public static void main( String[] args ) throws Exception {

        // do extra experimental stuff
        experiment();

        // Capture Jetty logging into Log4J2.
        org.eclipse.jetty.util.log.Log.setLog( new Log4j2JettyLogger( "Jetty" ) );

        LOG.info( "Application started." );

        // Configure the data source.
        // TODO: support different back ends
        IDataSourceDefinition dataSourceDefinition = new H2DataSourceDefinition();

        // Create the data source ...
        try ( IDataSource dataSource = dataSourceDefinition.makeDataSource() ) {

            // Run the web server.
            new WebServer().run( dataSource );

        }

        LOG.info( "Application stopped." );

    }

    /**
     * Placeholder for extra code when experimenting.
     */
    private static void experiment() {
        LOG.info( "UUID: " + Uuids.makeUuidWithReservedBlock() );
    }

    private static final Logger LOG = LogManager.getLogger();

}