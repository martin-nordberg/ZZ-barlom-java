//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
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
 * Service for querying Grestler directed edge types.
 */
@Path( "/metadata/directededgetypes" )
public class DirectedEdgeTypeQueries {

    /**
     * Constructs a new directed edge type query service backed by given metamodel repository.
     *
     * @param metamodelRepository  the metamodel repository to query from.
     * @param jsonGeneratorFactory the factory for generating JSON.
     */
    @Inject
    public DirectedEdgeTypeQueries(
        IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory
    ) {

        this.metamodelRepository = metamodelRepository;
        this.jsonGeneratorFactory = jsonGeneratorFactory;

    }

    /**
     * Queries for one directed edge type by ID or by path.
     *
     * @param idOrPath the UUID or the full path of the directed edge type.
     *
     * @return JSON for the directed edge type found or null.
     */
    @GET
    @Path( "/{idOrPath}" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.directededgetype+json" } )
    public String getDirectedEdgeTypeByIdOrPath( @PathParam( "idOrPath" ) String idOrPath ) {

        DirectedEdgeTypeQueries.LOG.info( "Querying for directed edge type {}.", idOrPath );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        // TODO: distinguish UUID from path

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Optional<IDirectedEdgeType> edgeType = this.metamodelRepository.findOptionalDirectedEdgeTypeById(
                    UUID.fromString(
                        idOrPath
                    )
                );

                if ( edgeType.isPresent() ) {
                    edgeType.get().generateJson( json );
                }
                else {
                    json.writeNull();
                }
            }
        );

        json.close();
        return result.toString();

    }

    /**
     * Queries for all directed edge types.
     *
     * @return JSON for the directed edge types found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.directededgetype+json" } )
    public String getEdgeTypesAll() {

        DirectedEdgeTypeQueries.LOG.info( "Querying for all directed edge types." );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        json.writeStartObject();
        json.writeStartArray( "edgeTypes" );

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Iterable<IDirectedEdgeType> edgeTypes = this.metamodelRepository.findAllDirectedEdgeTypes();
                edgeTypes.forEach( edgeType -> edgeType.generateJson( json ) );
            }
        );

        json.writeEnd();
        json.writeEnd();

        json.close();
        return result.toString();

    }

    private static final Logger LOG = LogManager.getLogger();

    /** The factory for generating JSON. */
    private final JsonGeneratorFactory jsonGeneratorFactory;

    /** The metamodel repository to query from. */
    private final IMetamodelRepository metamodelRepository;

}
