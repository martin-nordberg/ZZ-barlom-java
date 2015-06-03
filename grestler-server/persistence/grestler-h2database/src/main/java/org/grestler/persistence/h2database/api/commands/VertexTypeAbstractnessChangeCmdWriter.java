package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.spi.commands.VertexTypeAbstractnessChangeCmdRecord;
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
public final class VertexTypeAbstractnessChangeCmdWriter
    extends AbstractMetamodelCommandWriter<VertexTypeAbstractnessChangeCmdRecord> {

    /**
     * Constructs a new abstractness change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    VertexTypeAbstractnessChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, VertexTypeAbstractnessChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "isAbstract", record.abstractness.isAbstract() );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "VertexType.Update.Abstractness" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
