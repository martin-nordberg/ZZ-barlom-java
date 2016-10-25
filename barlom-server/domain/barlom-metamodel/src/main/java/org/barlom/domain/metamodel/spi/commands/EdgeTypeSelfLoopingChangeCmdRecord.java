//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import org.barlom.domain.metamodel.api.elements.ESelfLooping;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of an edge type self looping change command.
 */
public class EdgeTypeSelfLoopingChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public EdgeTypeSelfLoopingChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.selfLooping = ESelfLooping.valueOf( jsonCmdArgs.getString( "selfLooping" ) );
    }

    public final UUID id;

    public final ESelfLooping selfLooping;

}
