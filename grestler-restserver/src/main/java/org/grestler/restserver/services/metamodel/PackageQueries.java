//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.restserver.services.metamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.impl.elements.Package;
import org.grestler.utilities.revisions.StmTransaction;
import org.grestler.utilities.revisions.StmTransactionContext;
import org.grestler.utilities.uuids.Uuids;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for querying Grestler packages.
 */
@Path( "/metadata/packages" )
public class PackageQueries {

    @GET
    @Path( "/{idOrPath}" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.package+json" } )
    public String getPackageByIdOrPath( @PathParam( "idOrPath" ) String idOrPath ) {

        LOG.info( "Querying for package {}.", idOrPath );

        try ( StmTransaction ignored = StmTransactionContext.beginTransaction() ) {

            // TODO: real query
            IPackage pkg = new Package( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "sample2" );

            return pkg.toJson();

        }

    }

    @GET
    @Path( "" )
    @Produces( { "application/json", "application/vnd.grestler.org.v1.package+json" } )
    public String getPackagesAll() {

        LOG.info( "Querying for all packages." );

        StringWriter result = new StringWriter();
        JsonGenerator json = Json.createGenerator( result );

        json.writeStartObject();
        json.writeStartArray( "packages" );

        StmTransactionContext.doInTransaction(
            2, () -> {
                List<IPackage> packages = new ArrayList<>();

                // TODO: real query
                packages.add( IPackage.ROOT_PACKAGE );
                packages.add( new Package( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "sample2" ) );
                packages.add( new Package( Uuids.makeUuid(), IPackage.ROOT_PACKAGE, "sample3" ) );

                packages.forEach( ( pkg ) -> pkg.generateJson( json ) );
            }
        );

        json.writeEnd();
        json.writeEnd();

        json.close();
        return result.toString();

    }

    private static final Logger LOG = LogManager.getLogger();

}
