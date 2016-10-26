//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.presentation.gdbconsoleserver.BarlomGdbConsoleServerBuilder;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.barlom.application.restserver.RestServerBuilder;
import org.barlom.infrastructure.utilities.configuration.Configuration;
import org.barlom.presentation.adminserver.AdminServerBuilder;
import org.h2.server.web.WebServlet;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Jetty web server initial configuration and start up.
 */
@Singleton
public class WebServer
    implements AutoCloseable {

    /**
     * Constructs a new web server.
     */
    @Inject
    public WebServer() {
    }

    /**
     * Stops the running web server.
     */
    @Override
    public void close() {

        WebServer.LOG.info( "Preparing to shut down ..." );

        // Define a task to stop the server.
        Runnable stopServer = () -> {
            try {
                Thread.sleep( 100L );
                this.server.stop();
                WebServer.LOG.info( "Application server shut down." );
            }
            catch ( Exception e ) {
                WebServer.LOG.error( "Failed shutdown.", e );
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
    public void run() throws Exception {

        // Read the configuration.
        Configuration config = new Configuration( WebServer.class );
        int port = config.readInt( "port" );
        boolean enableAdminServer = config.readBoolean( "enableAdminServer" );
        boolean enableH2Console = config.readBoolean( "enableH2Console" );
        boolean enableBarlomGdbConsole = config.readBoolean( "enableBarlomGdbConsole" );

        // Build the app server.
        this.server = WebServer.makeServer( port );

        // Create the context list.
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        // Build the REST server.
        RestServerBuilder.makeRestServer( contexts );

        // Build the admin server.
        if ( enableAdminServer ) {
            AdminServerBuilder.makeAdminServer( this, contexts );
        }

        // Build the Barlom-GDB console.
        if ( enableBarlomGdbConsole ) {
            BarlomGdbConsoleServerBuilder.makeConsole( this, contexts );
        }

        // Add a raw H2 SQL console.
        if ( enableH2Console ) {
            WebServer.makeH2Console( contexts );
        }

        // Configure the server for its contexts
        this.server.setHandler( contexts );

        // Start the server.
        WebServer.LOG.info( "Starting application server ..." );
        this.server.start();

        // Set up graceful shut down.
        this.server.setStopTimeout( 5000L );
        this.server.setStopAtShutdown( true );

        // Hang out until the server is stopped.
        this.server.join();

    }

    /**
     * Add the H2 console servlet to the set of contexts.
     *
     * @param contexts the contexts being built.
     */
    private static void makeH2Console( ContextHandlerCollection contexts ) {

        // TODO: move this to a lower level

        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/h2console" );

        ServletHolder servletHolder = new ServletHolder( new WebServlet() );
        servletHolder.setInitParameter( "webAllowOthers", "true" );

        result.addServlet( servletHolder, "/*" );

        contexts.addHandler( result );

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

        // Configure request logging
        NCSARequestLog requestLog = new NCSARequestLog( /*TODO: for file*/ );
        requestLog.setAppend( true );
        requestLog.setExtended( false );
        requestLog.setLogTimeZone( "EST" );
        requestLog.setLogLatency( true );
        requestLog.setRetainDays( 10 );
        result.setRequestLog( requestLog );

        return result;

    }

    private static final Logger LOG = LogManager.getLogger();

    /** App server for static content and REST web services. */
    private Server server = null;

}
