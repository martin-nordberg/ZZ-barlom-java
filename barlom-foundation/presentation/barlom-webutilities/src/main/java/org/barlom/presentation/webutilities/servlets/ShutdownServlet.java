//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.presentation.webutilities.servlets;

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
     * @param webServer The web server to be shut down when this servlet executes.
     * @param message The message to display in the final screen of output.
     */
    public ShutdownServlet( AutoCloseable webServer, String message ) {
        this.webServer = webServer;
        this.message = message;
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
        resp.getOutputStream().println( this.message );
        resp.setStatus( HttpServletResponse.SC_OK );

    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

    /** The message to display in the final screen of output. */
    private final String message;

    /** The web server to shutdown when this servlet executes. */
    private final AutoCloseable webServer;

}
