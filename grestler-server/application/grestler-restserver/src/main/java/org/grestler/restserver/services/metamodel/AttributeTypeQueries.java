//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.cmdquery.IMetamodelRepository;
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
 * Service for querying Grestler attribute types.
 */
@Path( "/metadata/attributetypes" )
public class AttributeTypeQueries {

    /**
     * Constructs a new attribute type query service backed by given metamodel repository.
     *
     * @param metamodelRepository  the metamodel repository to query from.
     * @param jsonGeneratorFactory the factory for generating JSON.
     */
    @Inject
    public AttributeTypeQueries( IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory ) {
        this.metamodelRepository = metamodelRepository;
        this.jsonGeneratorFactory = jsonGeneratorFactory;
    }

    /**
     * Queries for one attribute type by ID or by path.
     *
     * @param idOrPath the UUID or the full path of the attribute type.
     *
     * @return JSON for the attribute type found or null.
     */
    @GET
    @Path( "/{idOrPath}" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.attributetype+json" } )
    public String getAttributeTypeByIdOrPath( @PathParam( "idOrPath" ) String idOrPath ) {

        AttributeTypeQueries.LOG.info( "Querying for attribute type {}.", idOrPath );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        // TODO: distinguish UUID from path

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Optional<IAttributeType> attributeType = this.metamodelRepository.findAttributeTypeById(
                    UUID.fromString(
                        idOrPath
                    )
                );

                if ( attributeType.isPresent() ) {
                    attributeType.get().generateJson( json );
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
     * Queries for all attribute types.
     *
     * @return JSON for the attribute types found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.attributetype+json" } )
    public String getAttributeTypesAll() {

        AttributeTypeQueries.LOG.info( "Querying for all attribute types." );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        json.writeStartObject();
        json.writeStartArray( "attributeTypes" );

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Iterable<IAttributeType> attributeTypes = this.metamodelRepository.findAttributeTypesAll();
                attributeTypes.forEach( attributeType -> attributeType.generateJson( json ) );
            }
        );

        json.writeEnd();
        json.writeEnd();

        json.close();
        return result.toString();

    }

    /** The logger for this class. */
    private static final Logger LOG = LogManager.getLogger();

    /** The factory for generating JSON. */
    private final JsonGeneratorFactory jsonGeneratorFactory;

    /** The metamodel repository to query from. */
    private final IMetamodelRepository metamodelRepository;

}
