//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import org.grestler.domain.metamodel.api.elements.EAbstractness;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of an edge type abstractness change command.
 */
public class EdgeTypeAbstractnessChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public EdgeTypeAbstractnessChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.abstractness = EAbstractness.valueOf( jsonCmdArgs.getString( "abstractness" ) );
    }

    public final EAbstractness abstractness;

    public final UUID id;

}
