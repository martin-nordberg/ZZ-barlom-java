//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.grestler.adminserver.AdminServerBuilder;
import org.grestler.dbutilities.IDataSource;
import org.grestler.restserver.RestServerBuilder;
import org.grestler.utilities.configuration.Configuration;
import org.grestler.webutilities.servlets.ShutdownServlet;
import org.h2.server.web.WebServlet;

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
        Runnable stopServer = () -> {
            try {
                Thread.sleep( 100 );
                WebServer.server.stop();
                LOG.info( "Application server shut down." );
            }
            catch ( Exception e ) {
                LOG.error( "Failed shutdown.", e );
            }
        };

        // Stop the servers in a separate thread.
        final Thread stopThread = new Thread( stopServer );
        stopThread.setName( "Shutdown" );

        stopThread.start();

    }

    /**
     * Starts the app server of Grestler. Does not return until they are stopped.
     *
     * @param dataSource the data source for the web servers to query from.
     *
     * @throws Exception If Jetty servers do not start properly.
     */
    public void run( IDataSource dataSource ) throws Exception {

        // Read the configuration.
        Configuration config = new Configuration( WebServer.class );
        int port = config.readInt( "port" );
        boolean enableAdminServer = config.readBoolean( "enableAdminServer" );
        boolean enableH2Console = config.readBoolean( "enableH2Console" );

        // Build the app server.
        WebServer.server = makeServer( port );

        // Create the context list.
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        // Build the REST server.
        RestServerBuilder.makeRestServer( dataSource, contexts );

        // Build the admin server.
        if ( enableAdminServer ) {
            AdminServerBuilder.makeAdminServer( dataSource, contexts );
        }

        // Add a raw H2 SQL console.
        if ( enableH2Console ) {
            this.makeH2Console( contexts );
        }

        // Configure the server for its contexts
        server.setHandler( contexts );

        // Start the server.
        LOG.info( "Starting application server ..." );
        WebServer.server.start();

        // Set up graceful shut down.
        WebServer.server.setStopTimeout( 5000 );
        WebServer.server.setStopAtShutdown( true );

        ShutdownServlet.registerWebServer( this );

        // Hang out until the server is stopped.
        WebServer.server.join();

    }

    /**
     * Constructs the Jetty HTTP server.
     *
     * @param port the port for the server to listen on.
     *
     * @return the created server ready to be configured.
     */
    private static Server makeServer( int port ) {

        // Create the server itself.
        Server result = new Server();

        // Configure the server connection.
        ServerConnector connector = new ServerConnector( result );
        connector.setPort( port );
        result.setConnectors( new Connector[]{ connector } );

        return result;

    }

    /**
     * Add the H2 console servlet to the set of contexts.
     *
     * @param contexts the contexts being built.
     */
    private void makeH2Console( ContextHandlerCollection contexts ) {

        // TODO: move this to a lower level

        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/h2console" );

        ServletHolder servletHolder = new ServletHolder( new WebServlet() );
        servletHolder.setInitParameter( "webAllowOthers", "true" );

        result.addServlet( servletHolder, "/*" );

        contexts.addHandler( result );

    }

    private static final Logger LOG = LogManager.getLogger();

    /** App server for static content and REST web services. */
    private static Server server;

}
