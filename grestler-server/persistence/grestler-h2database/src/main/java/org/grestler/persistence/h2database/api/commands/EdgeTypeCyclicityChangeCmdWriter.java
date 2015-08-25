package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeCyclicityChangeCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command writer for persisting an edge type cyclicity change.
 */
public final class EdgeTypeCyclicityChangeCmdWriter
    extends AbstractMetamodelCommandWriter<EdgeTypeCyclicityChangeCmdRecord> {

    /**
     * Constructs a new cyclicity change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    EdgeTypeCyclicityChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, EdgeTypeCyclicityChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "isAcyclic", ECyclicity.isAcyclic( record.cyclicity ).orElse( null ) );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "EdgeType.Update.Cyclicity" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}
