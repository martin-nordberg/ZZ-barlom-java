//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.webutils.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;

/**
 * Crude servlet shuts down the whole web server.
 */
public class ShutdownServlet
    extends HttpServlet {

    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        // ask the web server to stop (asynchronously)
        try {
            webServer.close();
        } catch ( Exception e ) {
            throw new ServletException( e );
        }

        // respond with a simple output
        resp.getOutputStream().println( "Grestler admin and application servers stopping..." );
        resp.setStatus( HttpServletResponse.SC_OK );

    }

    /**
     * Registers the web server to be closed when this shutdown servlet executes.
     * TODO: not static
     * @param webServer the web server to shut down.
     */
    public static void registerWebServer( Closeable webServer ) {
        ShutdownServlet.webServer = webServer;
    }

    /** The web server to shutdown when this servlet executes. */
    private static Closeable webServer;

}
