//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.grestler.dbutilities.IDataSource;
import org.grestler.webutils.filters.ThreadNameFilter;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import javax.servlet.DispatcherType;
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

        int appPort = 8080;  // TODO: configurable

        // Create the server itself.
        Server result = new Server();

        // Configure the server connection.
        ServerConnector connector = new ServerConnector( result );
        connector.setPort( appPort );
        result.setConnectors( new Connector[]{ connector } );

        // Serve static content.
        ContextHandler fileServerContext = makeFileServerContextHandler();

        // Serve dynamic content.
        ServletContextHandler webServiceContext = makeWebServiceContextHandler();

        // Combine the two contexts.
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers( new Handler[]{ fileServerContext, webServiceContext } );

        // Configure the server for its contexts
        result.setHandler( contexts );

        return result;
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

    /**
     * Creates the static file server context handler.
     * @return the new context handler.
     * @throws MalformedURLException if things are configured incorrectly.
     */
    private static ContextHandler makeFileServerContextHandler() throws MalformedURLException {

        // Set the context for static content.
        ContextHandler fileServerContext = new ContextHandler();
        fileServerContext.setContextPath( "/grestler" );

        // Set the source for static content.
        ResourceHandler fileResourceHandler = new ResourceHandler();
        fileResourceHandler.setCacheControl( "max-age=3600,public" );
        fileResourceHandler.setBaseResource( Resource.newResource( "/home/mnordberg/Workspace/grestler/GrestlerWebClient" ) );
        fileServerContext.setHandler( fileResourceHandler );

        return fileServerContext;

    }


}
