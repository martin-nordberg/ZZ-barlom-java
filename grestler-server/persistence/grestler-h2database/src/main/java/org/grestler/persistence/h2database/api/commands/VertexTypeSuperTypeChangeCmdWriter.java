package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.spi.commands.VertexTypeSuperTypeChangeCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command writer for persisting a vertex type abstractness change.
 */
public final class VertexTypeSuperTypeChangeCmdWriter
    extends AbstractMetamodelCommandWriter<VertexTypeSuperTypeChangeCmdRecord> {

    /**
     * Constructs a new abstractness change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    VertexTypeSuperTypeChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, VertexTypeSuperTypeChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "superTypeId", record.superTypeId );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "VertexType.Update.SuperType" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
