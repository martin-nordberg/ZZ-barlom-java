//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.persistence.h2database.api.commands;

import org.barlom.domain.metamodel.spi.commands.PackageCreationCmdRecord;
import org.barlom.infrastructure.utilities.configuration.Configuration;
import org.barlom.persistence.dbutilities.api.IConnection;
import org.barlom.persistence.dbutilities.api.IDataSource;
import org.barlom.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command to create a vertex type.
 */
final class PackageCreationCmdWriter
    extends AbstractMetamodelCommandWriter<PackageCreationCmdRecord> {

    /**
     * Constructs a new package creation command.
     *
     * @param dataSource the data source in which to save the new package.
     */
    PackageCreationCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, PackageCreationCmdRecord record ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.pkg.id );
        args.put( "parentPackageId", record.pkg.parentPackageId );
        args.put( "name", record.pkg.name );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "Package.Insert" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
