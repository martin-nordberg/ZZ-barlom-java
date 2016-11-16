//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices.services.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.domain.metamodel.api.IMetamodelFacade;

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
    public VertexTypeQueries( IMetamodelFacade metamodelFacade ) {
        this.metamodelFacade = metamodelFacade;
    }

    /**
     * Queries for all vertex types.
     *
     * @return JSON for the vertex types found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.barlom.org.v1.vertextype+json" } )
    public String getAllVertexTypes() {

        VertexTypeQueries.LOG.info( "Querying for all vertex types." );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        // TODO: need dynamic results

        out.println( "{ \"data\": [" );
        out.println( " { \"uuid\": \"1\", \"name\": \"VertexType1\" }," );
        out.println( " { \"uuid\": \"2\", \"name\": \"VertexType2\" }," );
        out.println( " { \"uuid\": \"3\", \"name\": \"VertexType3\" }," );
        out.println( " { \"uuid\": \"4\", \"name\": \"VertexType4\" }" );
        out.println( "] }" );

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
    @Produces( { "application/json", "application/vnd.barlom.org.v1.vertextype+json" } )
    public String getVertexTypeByUuid( @PathParam( "uuid" ) String uuid ) {

        VertexTypeQueries.LOG.info( "Querying for vertex type {}.", uuid );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        // TODO: need dynamic results

        out.println( " { \"uuid\": \"" + uuid + "\", \"name\": \"VertexType" + uuid + "\" }" );

        out.close();

        return result.toString();

    }

    private static final Logger LOG = LogManager.getLogger();

    private final IMetamodelFacade metamodelFacade;

}
