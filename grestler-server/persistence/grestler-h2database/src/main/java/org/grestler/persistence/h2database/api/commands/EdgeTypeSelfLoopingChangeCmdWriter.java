package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeSelfLoopingChangeCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command writer for persisting an edge type self-looping change.
 */
public final class EdgeTypeSelfLoopingChangeCmdWriter
    extends AbstractMetamodelCommandWriter<EdgeTypeSelfLoopingChangeCmdRecord> {

    /**
     * Constructs a new self-looping change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    EdgeTypeSelfLoopingChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, EdgeTypeSelfLoopingChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "isSelfLoopAllowed", ESelfLooping.isSelfLoopAllowed( record.selfLooping ).orElse( null ) );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "EdgeType.Update.SelfLooping" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
