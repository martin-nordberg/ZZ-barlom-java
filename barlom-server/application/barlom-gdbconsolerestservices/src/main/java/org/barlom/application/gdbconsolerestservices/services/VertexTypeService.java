//
// (C) Copyright 2015-2016 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.application.gdbconsolerestservices.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barlom.domain.metamodel.api.IMetamodelFacade;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

/**
 * Service for querying and updating Barlom vertex types.
 */
@Path( "/vertextypes" )
public class VertexTypeService {

    /**
     * Constructs a new vertex type query service backed by given metamodel repository.
     */
    public VertexTypeService( IMetamodelFacade metamodelFacade ) {
        this.metamodelFacade = metamodelFacade;
    }

    /**
     * Deletes the vertex type with given UUID.
     *
     * @param uuid the UUID of the vertex type to delete.
     *
     * @return JSON with success code.
     */
    @DELETE
    @Path( "/{uuid}" )
    @Produces( { "application/json", "application/vnd.barlom.org.v1.vertextype+json" } )
    public String deleteVertexType( @PathParam( "uuid" ) String uuid ) {

        VertexTypeService.LOG.info( "Deleting vertex type {}.", uuid );

        this.metamodelFacade.deleteVertexType( UUID.fromString(uuid) );

        return "{ \"success\": true }";

    }

    /**
     * Queries for all vertex types.
     *
     * @return JSON for the vertex types found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.barlom.org.v1.vertextype+json" } )
    public String findAllVertexTypes() {

        VertexTypeService.LOG.info( "Querying for all vertex types." );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        String[] delimiter = { "" };

        out.print( "{ \"data\": [" );

        this.metamodelFacade.findVertexTypesAll( (uuid,name) -> {
            out.println( delimiter[0] );
            delimiter[0] = ",";
            out.print( " { \"uuid\": \"" + uuid + "\", \"name\": \"" + name + "\" }" );
        } );

        out.println();
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
    public String findVertexTypeByUuid( @PathParam( "uuid" ) String uuid ) {

        VertexTypeService.LOG.info( "Querying for vertex type {}.", uuid );

        StringWriter result = new StringWriter();
        PrintWriter out = new PrintWriter( result );

        this.metamodelFacade.findVertexTypeByUuid(
            UUID.fromString(uuid),
            ( uuid2, name ) -> out.println( " { \"uuid\": \"" + uuid2 + "\", \"name\": \"" + name + "\" }" )
        );

        out.close();

        return result.toString();

    }

    /**
     * Inserts or creates a new vertex type.
     * @param jsonCommandArgsStr the JSON of the vertex type to create or update.
     * @param uuid the UUID of the vertex type to modify.
     * @return the JSON of the changed vertex type.
     */
    @POST
    @Path( "/{uuid}" )
    @Consumes( "application/json" )
    @Produces( { "application/json", "application/vnd.barlom.org.v1.vertextype+json" } )
    public String upsertVertexType( String jsonCommandArgsStr, @PathParam( "uuid" ) String uuid ) {

        VertexTypeService.LOG.info( "Updating vertex type {}.", uuid );

        JsonObject jsonVertexType;
        try {
            jsonVertexType = Json.createReader( new StringReader( jsonCommandArgsStr ) ).readObject();
        }
        catch ( Exception e ) {
            throw new BadRequestException( "Invalid JSON for vertex type.", e );
        }

        String name = jsonVertexType.getString( "name" );

        this.metamodelFacade.upsertVertexType( UUID.fromString( uuid ), name );

        return "{ \"uuid\": \"" + uuid + "\", \"name\": \"" + name + "\" }";

    }

    private static final Logger LOG = LogManager.getLogger();

    private final IMetamodelFacade metamodelFacade;

}
