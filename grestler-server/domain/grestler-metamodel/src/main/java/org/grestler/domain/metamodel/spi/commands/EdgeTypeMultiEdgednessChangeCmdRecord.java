//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of an edge type multi-edgedness change command.
 */
public class EdgeTypeMultiEdgednessChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public EdgeTypeMultiEdgednessChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.multiEdgedness = EMultiEdgedness.valueOf( jsonCmdArgs.getString( "multiEdgedness" ) );
    }

    public final EMultiEdgedness multiEdgedness;

    public final UUID id;

}
