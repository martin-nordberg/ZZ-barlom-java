package org.grestler.domain.metamodel.spi.commands;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Attributes of a name change command.
 */
public class NamedElementNameChangeCmdRecord
    extends IMetamodelCommandSpi.CmdRecord {

    public NamedElementNameChangeCmdRecord( JsonObject jsonCmdArgs ) {
        super( jsonCmdArgs );
        this.id = UUID.fromString( jsonCmdArgs.getString( "id" ) );
        this.name = jsonCmdArgs.getString( "name" );
    }

    public final UUID id;

    public final String name;

}
