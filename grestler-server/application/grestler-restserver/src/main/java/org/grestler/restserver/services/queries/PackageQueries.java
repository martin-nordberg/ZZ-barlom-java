//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.elements.IPackage;
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
 * Service for querying Grestler packages.
 */
@Path( "/metadata/packages" )
public class PackageQueries {

    /**
     * Constructs a new package query service backed by given metamodel repository.
     *
     * @param metamodelRepository  the metamodel repository to query from.
     * @param jsonGeneratorFactory the factory for generating JSON output.
     */
    @Inject
    public PackageQueries( IMetamodelRepository metamodelRepository, JsonGeneratorFactory jsonGeneratorFactory ) {

        this.metamodelRepository = metamodelRepository;
        this.jsonGeneratorFactory = jsonGeneratorFactory;

    }

    /**
     * Queries for all packages.
     *
     * @return JSON for the packages found.
     */
    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.package+json" } )
    public String getAllPackages() {

        PackageQueries.LOG.info( "Querying for all packages." );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        json.writeStartObject();
        json.writeStartArray( "packages" );

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Iterable<IPackage> packages = this.metamodelRepository.findAllPackages();
                packages.forEach( pkg -> pkg.generateJson( json ) );
            }
        );

        json.writeEnd();
        json.writeEnd();

        json.close();
        return result.toString();

    }

    /**
     * Queries for one package by ID or by path.
     *
     * @param idOrPath the UUID or the full path of the package.
     *
     * @return JSON for the package found or null
     */
    @GET
    @Path( "/{idOrPath}" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.package+json" } )
    public String getPackageByIdOrPath( @PathParam( "idOrPath" ) String idOrPath ) {

        PackageQueries.LOG.info( "Querying for package {}.", idOrPath );

        StringWriter result = new StringWriter();
        JsonGenerator json = this.jsonGeneratorFactory.createGenerator( result );

        // TODO: distinguish UUID from path

        StmTransactionContext.doInReadOnlyTransaction(
            () -> {
                Optional<IPackage> pkg = this.metamodelRepository.findOptionalPackageById( UUID.fromString( idOrPath ) );

                if ( pkg.isPresent() ) {
                    pkg.get().generateJson( json );
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
