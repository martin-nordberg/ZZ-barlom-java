//
// (C) Copyright 2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.main;

import org.barlom.application.gdbconsolerestservices.GdbConsoleRestServicesSubsystem;
import org.barlom.domain.metamodel.api.IMetamodelFacade;
import org.barlom.infrastructure.utilities.configuration.IConfiguration;
import org.barlom.infrastructure.utilities.configuration.PropertiesFileConfiguration;
import org.barlom.persistence.gdbpostgresql.GdbPostgreSqlSubsystem;
import org.barlom.persistence.postgresql.PostgreSqlSubsystem;
import org.barlom.presentation.gdbconsoleserver.GdbConsoleServerSubsystem;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import java.util.Collection;

/**
 * Top level factory for the entire application.
 */
class ApplicationSystem
    implements GdbConsoleRestServicesSubsystem.IDependencies {

    ApplicationSystem() {
        // TODO: hierarchical configuration
        // TODO: configurable data source name

        PostgreSqlSubsystem postgreSqlSubsystem = new PostgreSqlSubsystem( "test" );
        this.gdbPostgreSqlSubsystem = new GdbPostgreSqlSubsystem( postgreSqlSubsystem );
        this.gdbConsoleServerSubsystem = new GdbConsoleServerSubsystem();
        this.gdbConsoleRestServicesSubsystem = new GdbConsoleRestServicesSubsystem( this );

    }

    @Override
    public IMetamodelFacade provideMetamodelFacade() {
        return this.gdbPostgreSqlSubsystem.provideMetamodelFacade();
    }

    /**
     * Constructs a request log.
     *
     * @return the created server ready to be configured.
     */
    private static NCSARequestLog makeRequestLog() {

        // Configure request logging
        NCSARequestLog result = new NCSARequestLog( /*TODO: for file*/ );
        result.setAppend( true );
        result.setExtended( false );
        result.setLogTimeZone( "EST" );
        result.setLogLatency( true );
        result.setRetainDays( 10 );

        return result;

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

    private Iterable<ContextHandler> makeContextHandlers( AutoCloseable webServer ) {
        Collection<ContextHandler> result = this.gdbConsoleServerSubsystem.makeContextHandlers( webServer );
        result.add( this.gdbConsoleRestServicesSubsystem.makeWebServiceContextHandler() );
        return result;
    }

    WebServer makeWebServer() {

        // Read the configuration.
        IConfiguration config = new PropertiesFileConfiguration( WebServer.class );
        int port = config.readInt( "port" );
        boolean enableBarlomGdbConsole = config.readBoolean( "enableBarlomGdbConsole" );

        // Build the app server.
        Server server = ApplicationSystem.makeServer( port );

        // Configure request logging
        NCSARequestLog requestLog = ApplicationSystem.makeRequestLog();
        server.setRequestLog( requestLog );

        // Wrap the Jetty server to be auto-closeable.
        WebServer result = new WebServer( server );

        // Create the context list.
        ContextHandlerCollection contexts = new ContextHandlerCollection();

        // Build the Barlom-GDB console.
        if ( enableBarlomGdbConsole ) {

            Iterable<ContextHandler> handlers = new ApplicationSystem().makeContextHandlers( result );

            for ( ContextHandler handler : handlers ) {
                contexts.addHandler( handler );
            }

        }

        // Configure the server for its contexts
        server.setHandler( contexts );

        return result;

    }

    private final GdbConsoleRestServicesSubsystem gdbConsoleRestServicesSubsystem;

    private final GdbConsoleServerSubsystem gdbConsoleServerSubsystem;

    private final GdbPostgreSqlSubsystem gdbPostgreSqlSubsystem;

}
