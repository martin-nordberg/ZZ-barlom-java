//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices.services.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Service for querying Barlom vertex types.
 */
@Path( "/vertextypes" )
public class VertexTypeQueries {

    /**
     * Constructs a new vertex type query service backed by given metamodel repository.
     */
    @Inject
    public VertexTypeQueries( /*TODO*/ ) {
    }

    /**
     * Queries for all vertex types.
     *
     * @return JSON for the vertex types found.
     */
    @GET
    @Path( "" )
    @Produces( { "text/xml" } )
    public String getAllVertexTypes() {

        VertexTypeQueries.LOG.info( "Querying for all vertex types." );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        // TODO: need dynamic results

        out.println( "<barlom-gdb-vertextypes>" );
        out.println( " <barlom-gdb-vertextype id=\"1\" name=\"VertexType1\"></barlom-gdb-vertextype>" );
        out.println( " <barlom-gdb-vertextype id=\"2\" name=\"VertexType2\"></barlom-gdb-vertextype>" );
        out.println( " <barlom-gdb-vertextype id=\"3\" name=\"VertexType3\"></barlom-gdb-vertextype>" );
        out.println( " <barlom-gdb-vertextype id=\"4\" name=\"VertexType4\"></barlom-gdb-vertextype>" );
        out.println( "</barlom-gdb-vertextypes>" );

        out.close();

        return result.toString();

    }

    /**
     * Queries for one vertex type by UUID.
     *
     * @param uuid the UUID of the vertex type.
     *
     * @return JSON for the vertex type found or null.
     */
    @GET
    @Path( "/{uuid}" )
    @Produces( { "text/xml" } )
    public String getVertexTypeByUuid( @PathParam( "uuid" ) String uuid ) {

        VertexTypeQueries.LOG.info( "Querying for vertex type {}.", uuid );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        // TODO: need dynamic results

        out.println( "<barlom-gdb-vertextype id=\"" + uuid + "\" name=\"VertexType" + uuid + "\"></barlom-gdb-vertextype>" );

        out.close();

        return result.toString();

    }

    private static final Logger LOG = LogManager.getLogger();

}
