//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of a vertex type super type change command.
 */
public class VertexTypeSuperTypeChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public VertexTypeSuperTypeChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.superTypeId = UUID.fromString( jsonCmdArgs.getString( "superTypeId" ) );
    }

    public final UUID id;

    public final UUID superTypeId;

}
