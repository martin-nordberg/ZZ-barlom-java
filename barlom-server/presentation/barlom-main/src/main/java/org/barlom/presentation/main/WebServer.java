//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;

/**
 * Jetty web server initial configuration and start up.
 */
class WebServer
    implements AutoCloseable {

    /**
     * Constructs a new web server.
     */
    WebServer( Server server ) {
        this.server = server;
    }

    /**
     * Stops the running web server.
     */
    @Override
    public void close() {

        WebServer.LOG.info( "Stopping application server ..." );

        // Define a task to stop the server.
        Runnable stopServer = () -> {
            try {
                Thread.sleep( 100L );
                this.server.stop();
                WebServer.LOG.info( "Application server stopped." );
            }
            catch ( Exception e ) {
                WebServer.LOG.error( "Failed to stop application server.", e );
            }
        };

        // Stop the server in a separate thread.
        final Thread stopThread = new Thread( stopServer );
        stopThread.setName( "Shutdown" );

        stopThread.start();

    }

    /**
     * Starts the app server of Barlom. Does not return until it is stopped.
     *
     * @throws Exception If Jetty servers do not start properly.
     */
    void run() throws Exception {

        // Start the server.
        WebServer.LOG.info( "Starting application server ..." );
        this.server.start();

        // Set up graceful shut down.
        this.server.setStopTimeout( 5000L );
        this.server.setStopAtShutdown( true );

        // Hang out until the server is stopped.
        this.server.join();

    }

    private static final Logger LOG = LogManager.getLogger();

    /** App server for static content and REST web services. */
    private final Server server;

}
