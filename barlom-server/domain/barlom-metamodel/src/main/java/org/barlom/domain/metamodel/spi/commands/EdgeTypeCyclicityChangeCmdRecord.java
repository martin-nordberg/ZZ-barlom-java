//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import org.barlom.domain.metamodel.api.elements.ECyclicity;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of an edge type cyclicity change command.
 */
public class EdgeTypeCyclicityChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public EdgeTypeCyclicityChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.cyclicity = ECyclicity.valueOf( jsonCmdArgs.getString( "cyclicity" ) );
    }

    public final ECyclicity cyclicity;

    public final UUID id;

}
