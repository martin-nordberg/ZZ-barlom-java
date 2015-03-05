//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.application.restserver.services.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.api.queries.IMetamodelRepository;
import org.grestler.utilities.revisions.StmTransactionContext;

import javax.inject.Inject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.StringWriter;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for querying Grestler vertex types.
 */
@Path( "/metadata/vertextypes" )
public class VertexTypeQueries {

    /**
     * Constructs a new vertex type query service backed by given metamodel repository.
     *
     * @param metamodelRepository  the metamodel repository to query from.
     * @param jsonGeneratorFactory the factory for generating JSON.
     */
    @Inject
    public VertexTypeQueries( IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory ) {
        this.metamodelRepository = metamodelRepository;
        this.jsonGeneratorFactory = jsonGeneratorFactory;
    }

    /**
     * Queries for all vertex types.
     *
     * @return JSON for the vertex types found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.vertextype+json" } )
    public String getAllVertexTypes() {

        VertexTypeQueries.LOG.info( "Querying for all vertex types." );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        json.writeStartObject();
        json.writeStartArray( "vertexTypes" );

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Iterable<IVertexType> vertexTypes = this.metamodelRepository.findAllVertexTypes();
                vertexTypes.forEach( vertexType -> vertexType.generateJson( json ) );
            }
        );

        json.writeEnd();
        json.writeEnd();

        json.close();
        return result.toString();

    }

    /**
     * Queries for one vertex type by ID or by path.
     *
     * @param idOrPath the UUID or the full path of the vertex type.
     *
     * @return JSON for the vertex type found or null.
     */
    @GET
    @Path( "/{idOrPath}" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.vertextype+json" } )
    public String getVertexTypeByIdOrPath( @PathParam( "idOrPath" ) String idOrPath ) {

        VertexTypeQueries.LOG.info( "Querying for vertex type {}.", idOrPath );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        // TODO: distinguish UUID from path

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Optional<IVertexType> vertexType = this.metamodelRepository.findOptionalVertexTypeById(
                    UUID.fromString(
                        idOrPath
                    )
                );

                if ( vertexType.isPresent() ) {
                    vertexType.get().generateJson( json );
                }
                else {
                    json.writeNull();
                }
            }
        );

        json.close();
        return result.toString();

    }

    private static final Logger LOG = LogManager.getLogger();

    /** The factory for generating JSON. */
    private final JsonGeneratorFactory jsonGeneratorFactory;

    /** The metamodel repository to query from. */
    private final IMetamodelRepository metamodelRepository;

}
