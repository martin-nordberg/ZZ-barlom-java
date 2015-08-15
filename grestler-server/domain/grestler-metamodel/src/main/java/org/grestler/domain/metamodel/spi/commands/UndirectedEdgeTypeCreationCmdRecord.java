//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IUndirectedEdgeType;

import javax.json.JsonObject;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Attributes of an undirected edge type creation command.
 */
public class UndirectedEdgeTypeCreationCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public UndirectedEdgeTypeCreationCmdRecord(
        JsonObject jsonCmdArgs
    ) {
        super( jsonCmdArgs );
        this.et = new IUndirectedEdgeType.Record(
            UUID.fromString( jsonCmdArgs.getString( "id" ) ),
            UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) ),
            UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) ),
            jsonCmdArgs.getString( "name" ),
            EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) ),
            ECyclicity.valueOf( jsonCmdArgs.getString( "cyclicity" ) ),
            EMultiEdgedness.valueOf( jsonCmdArgs.getString( "multiEdgedness" ) ),
            ESelfLooping.valueOf( jsonCmdArgs.getString( "selfLooping" ) ),
            this.hasNoValue( jsonCmdArgs, "maxDegree" ) ? OptionalInt.empty() : OptionalInt.of(
                jsonCmdArgs.getInt( "maxDegree" )
            ),
            this.hasNoValue( jsonCmdArgs, "minDegree" ) ? OptionalInt.empty() : OptionalInt.of(
                jsonCmdArgs.getInt( "minDegree" )
            ),
            UUID.fromString( jsonCmdArgs.getString( "vertexTypeId" ) )
        );
    }

    public final IUndirectedEdgeType.Record et;

}
