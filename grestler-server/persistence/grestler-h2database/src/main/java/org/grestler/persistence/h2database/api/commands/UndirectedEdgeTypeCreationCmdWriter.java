//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.persistence.h2database.api.commands;

import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.spi.commands.UndirectedEdgeTypeCreationCmdRecord;
import org.grestler.infrastructure.utilities.configuration.Configuration;
import org.grestler.persistence.dbutilities.api.IConnection;
import org.grestler.persistence.dbutilities.api.IDataSource;
import org.grestler.persistence.h2database.H2DatabaseModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command to create a directed edge type.
 */
final class UndirectedEdgeTypeCreationCmdWriter
    extends AbstractMetamodelCommandWriter<UndirectedEdgeTypeCreationCmdRecord> {

    /**
     * Constructs a new directed edge type creation command.
     *
     * @param dataSource the data source in which to save the new edge type.
     */
    UndirectedEdgeTypeCreationCmdWriter( IDataSource dataSource ) {
        super( dataSource );
    }

    @Override
    protected void writeCommand(
        IConnection connection, UndirectedEdgeTypeCreationCmdRecord record
    ) {

        // Build a map of the arguments.
        Map<String, Object> args = new HashMap<>();
        args.put( "id", record.et.id );
        args.put( "parentPackageId", record.et.parentPackageId );
        args.put( "name", record.et.name );
        args.put( "superTypeId", record.et.superTypeId );
        args.put( "isAbstract", record.et.abstractness.isAbstract() );
        if ( record.et.cyclicity != ECyclicity.UNCONSTRAINED ) {
            args.put( "isAcyclic", record.et.cyclicity == ECyclicity.ACYCLIC );
        }
        if ( record.et.multiEdgedness != EMultiEdgedness.UNCONSTRAINED ) {
            args.put( "isMultiEdgeAllowed", record.et.multiEdgedness == EMultiEdgedness.MULTI_EDGES_ALLOWED );
        }
        if ( record.et.selfLooping != ESelfLooping.UNCONSTRAINED ) {
            args.put( "isSelfLoopAllowed", record.et.selfLooping == ESelfLooping.SELF_LOOPS_ALLOWED );
        }
        args.put( "vertexTypeId", record.et.vertexTypeId );
        if ( record.et.minDegree.isPresent() ) {
            args.put( "minDegree", record.et.minDegree.getAsInt() );
        }
        if ( record.et.maxDegree.isPresent() ) {
            args.put( "maxDegree", record.et.maxDegree.getAsInt() );
        }

        args.put( "cmdId", record.cmdId );
        args.put( "jsonCmdArgs", record.jsonCmdArgs );

        // Read the SQL commands.
        Configuration config = new Configuration( H2DatabaseModule.class );
        List<String> sqlInserts = config.readStrings( "UndirectedEdgeType.Insert" );

        // Perform the inserts.
        for ( String sqlInsert : sqlInserts ) {
            connection.executeOneRowCommand( sqlInsert, args );
        }

    }

}
