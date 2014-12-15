//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.grestler.adminserver.AdminServerBuilder;
import org.grestler.dbutilities.IDataSource;
import org.grestler.restserver.RestServerBuilder;
import org.grestler.webutilities.servlets.ShutdownServlet;

/**
 * Jetty web server initial configuration and start up.
 */
public class WebServer
    implements AutoCloseable {

    /**
     * Constructs a new web server.
     */
    public WebServer() {
    }

    /**
     * Stops the running web server.
     */
    public void close() {

        LOG.info( "Preparing to shut down ..." );

        // Define a task to stop the two servers.
        Runnable stopServers = () -> {
            try {
                Thread.sleep( 1000 );
                WebServer.adminServer.stop();
                LOG.info( "Admin server shut down." );

                Thread.sleep( 500 );
                WebServer.restServer.stop();
                LOG.info( "REST server shut down." );
            }
            catch ( Exception e ) {
                LOG.error( "Failed shutdown.", e );
            }
        };

        // Stop the servers in a separate thread.
        final Thread stopThread = new Thread( stopServers );
        stopThread.setName( "Shutdown" );

        stopThread.start();

    }

    /**
     * Starts the app server and admin server of Grestler. Does not return until they are stopped.
     * @param dataSource the data source for the web servers to query from.
     * @throws Exception If Jetty servers do not start properly.
     */
    public void run( IDataSource dataSource ) throws Exception {

        // Build the app server.
        WebServer.restServer = RestServerBuilder.makeRestServer( dataSource );

        // Build the admin server.
        WebServer.adminServer = AdminServerBuilder.makeAdminServer( dataSource );

        // Start the servers.
        LOG.info( "Starting REST server ..." );
        WebServer.restServer.start();

        LOG.info( "Starting Admin server ..." );
        WebServer.adminServer.start();

        // Set up graceful shut down.
        WebServer.restServer.setStopTimeout( 5000 );
        WebServer.restServer.setStopAtShutdown( true );
        WebServer.adminServer.setStopTimeout( 5000 );
        WebServer.adminServer.setStopAtShutdown( true );

        ShutdownServlet.registerWebServer( this );

        // Hang out until both servers are stopped.
        WebServer.adminServer.join();
        WebServer.restServer.join();

    }

    private static final Logger LOG = LogManager.getLogger();

    /** App server for static content and REST web services. */
    private static Server restServer;

    /** Admin server for control tasks. */
    private static Server adminServer;

}
