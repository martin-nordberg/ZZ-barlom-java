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
 * Command writer for persisting a vertex type abstractness change.
 */
public final class VertexTypeSuperTypeChangeCmdWriter
    extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new abstractness change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    VertexTypeSuperTypeChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        String superTypeId = jsonCmdArgs.getString( "superTypeId" );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", id );
        args.put( "superTypeId", superTypeId );

        args.put( "cmdId", cmdId );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "VertexType.Update.SuperType" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
