//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.grestler.dbutilities.IDataSource;
import org.grestler.restserver.logging.Log4j2RestEasyLogger;
import org.grestler.utilities.configuration.Configuration;
import org.grestler.webutilities.filters.ThreadNameFilter;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.EnumSet;

/**
 * Builder class creates and configures a new REST server.
 */
public class RestServerBuilder {

    /**
     * Creates a Jetty server for REST services.
     * @return the newly created server.
     * @throws java.net.MalformedURLException if the configuration is broken.
     */
    public static Server makeRestServer( IDataSource dataSource ) throws MalformedURLException {

        // Redirect RESTEasy logging to Log4j2.
        overrideRESTEasyLoggerInitialization();

        // Read the configuration.
        Configuration config = new Configuration( RestServerBuilder.class );
        int restPort = config.readInt( "restPort" );

        // Create the server itself.
        Server result = new Server();

        // Configure the server connection.
        ServerConnector connector = new ServerConnector( result );
        connector.setPort( restPort );
        result.setConnectors( new Connector[]{ connector } );

        // Serve dynamic content.
        ServletContextHandler webServiceContext = makeWebServiceContextHandler();

        // Combine the two contexts.
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers( new Handler[]{ webServiceContext } );

        // Configure the server for its contexts
        result.setHandler( contexts );

        return result;
    }

    private static void overrideRESTEasyLoggerInitialization() {

        // Ensure that RESTEasy logging is initialized
        org.jboss.resteasy.logging.Logger.setLoggerType( Logger.LoggerType.JUL );

        // Override by reflection
        try {
            Field field = org.jboss.resteasy.logging.Logger.class.getDeclaredField( "loggerConstructor" );
            field.setAccessible( true );
            field.set( null, Log4j2RestEasyLogger.class.getDeclaredConstructor( String.class ) );
        }
        catch ( NoSuchFieldException e ) {
            assert false : "RESTEasy logger field has changed." + e.getMessage();
        }
        catch ( NoSuchMethodException e ) {
            assert false : "Missing RESTEasy logger constructor." + e.getMessage();
        }
        catch ( IllegalAccessException e ) {
            assert false : "Inaccessible RESTEasy logger constructor." + e.getMessage();
        }

    }

    /**
     * Creates the REST service context handler.
     * @return the new context handler.
     */
    private static ServletContextHandler makeWebServiceContextHandler() {

        // Set the context for dynamic content.
        ServletContextHandler result = new ServletContextHandler( ServletContextHandler.SESSIONS );
        result.setContextPath( "/grestlerdata" );

        // Add a RESTEasy servlet for the dynamic content.
        ServletHolder servletHolder = new ServletHolder( new HttpServletDispatcher() );
        servletHolder.setInitParameter( "javax.ws.rs.Application", "org.grestler.restserver.ApplicationServices" );
        result.addServlet( servletHolder, "/*" );

        // Rename request threads for better logging.
        result.addFilter( ThreadNameFilter.class, "/*", EnumSet.of( DispatcherType.REQUEST ) );

        return result;

    }


}
