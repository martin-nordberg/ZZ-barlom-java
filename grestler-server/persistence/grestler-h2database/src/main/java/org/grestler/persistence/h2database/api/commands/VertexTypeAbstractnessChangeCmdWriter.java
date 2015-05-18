package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
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
public final class VertexTypeAbstractnessChangeCmdWriter
    extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new abstractness change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    VertexTypeAbstractnessChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand( IConnection connection, JsonObject jsonCmdArgs ) {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        UUID cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
        UUID id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        String abstractness = jsonCmdArgs.getString( "abstractness" );

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", id );
        args.put( "isAbstract", EAbstractness.valueOf( abstractness ).isAbstract() );

        args.put( "cmdId", cmdId );
        args.put( "jsonCmdArgs", jsonCmdArgs.toString() );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "VertexType.Update.Abstractness" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
