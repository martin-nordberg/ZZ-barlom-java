//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands;

import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Command to create a vertex type.
 */
final class PackageCreationCmdWriter
    extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new package creation command.
     *
     * @param dataSource the data source in which to save the new package.
     */
    PackageCreationCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        UUID parentPackageId = UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) );
        String name = jsonCmdArgs.getString( "name" );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", id );
        args.put( "parentPackageId", parentPackageId );
        args.put( "name", name );

        args.put( "cmdId", cmdId );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "Package.Insert" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
