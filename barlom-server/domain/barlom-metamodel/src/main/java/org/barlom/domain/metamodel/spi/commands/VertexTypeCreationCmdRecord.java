//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import org.barlom.domain.metamodel.api.elements.EAbstractness;
import org.barlom.domain.metamodel.api.elements.IVertexType;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of a vertex type creation command.
 */
public class VertexTypeCreationCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public VertexTypeCreationCmdRecord(
        JsonObject jsonCmdArgs
    ) {
        super( jsonCmdArgs );
        this.vt = new IVertexType.Record(
            UUID.fromString( jsonCmdArgs.getString( "id" ) ),
            UUID.fromString( jsonCmdArgs.getString( "parentPackageId" ) ),
            jsonCmdArgs.getString( "name" ),
            EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) ),
            UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) )
        );
    }

    public final IVertexType.Record vt;

}
