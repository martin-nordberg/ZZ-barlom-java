//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.webutilities.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Crude servlet shuts down the whole web server.
 */
public class ShutdownServlet
    extends HttpServlet {

    /**
     * Constructs a new shutdown servlet.
     *
     * @param webServer the web server to be shut down when this servlet executes.
     */
    public ShutdownServlet( AutoCloseable webServer ) {
        this.webServer = webServer;
    }

    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        // Ask the web server to stop (asynchronously).
        try {
            this.webServer.close();
        }
        catch ( Exception e ) {
            throw new ServletException( e );
        }

        // Respond with a simple output.
        resp.getOutputStream().println( "Grestler application server stopping..." );
        resp.setStatus( HttpServletResponse.SC_OK );

    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

    /** The web server to shutdown when this servlet executes. */
    private final AutoCloseable webServer;

}
