//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import org.barlom.domain.metamodel.api.elements.EAbstractness;
import org.barlom.domain.metamodel.api.elements.ECyclicity;
import org.barlom.domain.metamodel.api.elements.EMultiEdgedness;
import org.barlom.domain.metamodel.api.elements.ESelfLooping;
import org.barlom.domain.metamodel.api.elements.IDirectedEdgeType;

import javax.json.JsonObject;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Attributes of a directed edge type creation command.
 */
public class DirectedEdgeTypeCreationCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public DirectedEdgeTypeCreationCmdRecord(
        JsonObject jsonCmdArgs
    ) {
        super( jsonCmdArgs );
        this.et = new IDirectedEdgeType.Record(
            UUID.fromString( jsonCmdArgs.getString( "id" ) ),
            UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) ),
            UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) ),
            jsonCmdArgs.getString( "name" ),
            EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) ),
            ECyclicity.valueOf( jsonCmdArgs.getString( "cyclicity" ) ),
            EMultiEdgedness.valueOf( jsonCmdArgs.getString( "multiEdgedness" ) ),
            ESelfLooping.valueOf( jsonCmdArgs.getString( "selfLooping" ) ),
            this.hasNoValue( jsonCmdArgs, "headRoleName" ) ? Optional.empty() : Optional.of(
                jsonCmdArgs.getString( "headRoleName" )
            ),
            UUID.fromString( jsonCmdArgs.getString( "headVertexTypeId" ) ),
            this.hasNoValue(
                jsonCmdArgs,
                "maxHeadInDegree"
            ) ? OptionalInt.empty() : OptionalInt.of( jsonCmdArgs.getInt( "maxHeadInDegree" ) ),
            this.hasNoValue(
                jsonCmdArgs,
                "maxTailOutDegree"
            ) ? OptionalInt.empty() : OptionalInt.of( jsonCmdArgs.getInt( "maxTailOutDegree" ) ),
            this.hasNoValue(
                jsonCmdArgs,
                "minHeadInDegree"
            ) ? OptionalInt.empty() : OptionalInt.of( jsonCmdArgs.getInt( "minHeadInDegree" ) ),
            this.hasNoValue(
                jsonCmdArgs,
                "minTailOutDegree"
            ) ? OptionalInt.empty() : OptionalInt.of( jsonCmdArgs.getInt( "minTailOutDegree" ) ),
            this.hasNoValue( jsonCmdArgs, "tailRoleName" ) ? Optional.empty() : Optional.of(
                jsonCmdArgs.getString( "tailRoleName" )
            ),
            UUID.fromString( jsonCmdArgs.getString( "tailVertexTypeId" ) )
        );
    }

    public final IDirectedEdgeType.Record et;

}
