//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Service provide callback interface for completing a command after it has been persisted.
 */
@FunctionalInterface
public interface IMetamodelCommandSpi<R extends IMetamodelCommandSpi.CmdRecord> {

    /**
     * Write the command's changes into the metamodel repository.
     *
     * @param record the JSON for the command's changes.
     */
    void finish( R record );

    /**
     * Minimal record for the attributes used by a command.
     */
    class CmdRecord {

        protected CmdRecord( JsonObject jsonCmdArgs ) {
            this.cmdId = UUID.fromString( jsonCmdArgs.getString( "cmdId" ) );
            this.jsonCmdArgs = jsonCmdArgs.toString();
        }

        public final UUID cmdId;

        public final String jsonCmdArgs;
    }

}
