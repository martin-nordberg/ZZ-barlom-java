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
 * Command writer for persisting a name change to a packaged element..
 */
public final class PackagedElementNameChangeCmdWriter
    extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new package creation command.
     *
     * @param dataSource the data source in which to save the new package.
     */
    PackagedElementNameChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        String name = jsonCmdArgs.getString( "name" );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", id );
        args.put( "name", name );

        args.put( "cmdId", cmdId );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "PackagedElement.Update.Name" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
