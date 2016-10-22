package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeCyclicityChangeCmdRecord;
import org.grestler.domain.metamodel.spi.commands.EdgeTypeMultiEdgednessChangeCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command writer for persisting an edge type multi-edgedness change.
 */
public final class EdgeTypeMultiEdgednessChangeCmdWriter
    extends AbstractMetamodelCommandWriter<EdgeTypeMultiEdgednessChangeCmdRecord> {

    /**
     * Constructs a new multi-edgedness change command.
     *
     * @param dataSource the data source in which to make the change.
     */
    EdgeTypeMultiEdgednessChangeCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, EdgeTypeMultiEdgednessChangeCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.id );
        args.put( "isMultiEdgeAllowed", EMultiEdgedness.isMultiEdgeAllowed( record.multiEdgedness ).orElse( null ) );

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlUpdates = config.readStrings( "EdgeType.Update.MultiEdgedness" );

        // Perform the inserts.
        for ( String sqlUpdate : sqlUpdates ) {
            connection.executeOneRowCommand( sqlUpdate, args );
        }

    }

}