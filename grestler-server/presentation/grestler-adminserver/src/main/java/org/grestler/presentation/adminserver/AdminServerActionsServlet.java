//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.presentation.adminserver;

import dagger.Lazy;
import org.grestler.infrastructure.utilities.functions.IAction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet executes actions for the admin console.
 */
public class AdminServerActionsServlet
    extends HttpServlet {

    /**
     * Constructs a new admin server actions servlet.
     * TODO: make the action lazy
     */
    @Inject
    public AdminServerActionsServlet( @Named( "DataDumpAction" ) Lazy<IAction> dataDumpAction ) {
        this.dataDumpAction = dataDumpAction;
    }

    /**
     * Executes the action of exporting the database.
     *
     * @param resp the HTTP response to write to.
     *
     * @return the HTTP status code.
     *
     * @throws IOException if output fails.
     */
    private int executeExportAction( ServletResponse resp ) throws IOException {

        // Create and perform the action.
        this.dataDumpAction.get().perform();

        // Respond with a simple output.
        resp.getOutputStream().println( "Data dump completed successfully." );

        return HttpServletResponse.SC_OK;

    }

    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

        // TODO: Need to perform routing based upon request URL ...

        int status = this.executeExportAction( resp );

        resp.setStatus( status );

    }

    /** Serialization version. */
    private static final long serialVersionUID = 1L;

    private final Lazy<IAction> dataDumpAction;

}
