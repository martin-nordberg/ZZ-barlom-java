//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.commands;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Service provide callback interface for completing a command after it has been persisted.
 */
@FunctionalInterface
public interface IMetamodelCommandSpi<R extends IMetamodelCommandSpi.CmdRecord> {

    /**
     * Minimal record for the attributes used by a command.
     */
    class CmdRecord {

        protected CmdRecord( JsonObject jsonCmdArgsObj ) {
            this.cmdId = UUID.fromString( jsonCmdArgsObj.getString( "cmdId" ) );
            this.jsonCmdArgs = jsonCmdArgsObj.toString();
        }

        /**
         * Tests whether a JSON attribute has a value.
         *
         * @param jsonCmdArgsObj the JSON object to check.
         * @param key            the key to look for.
         *
         * @return true if there is a non-null value for the key.
         */
        protected boolean hasNoValue( JsonObject jsonCmdArgsObj, String key ) {
            return !jsonCmdArgsObj.containsKey( key ) || jsonCmdArgsObj.isNull( key );
        }

        public final UUID cmdId;

        public final String jsonCmdArgs;
    }

    /**
     * Write the command's changes into the metamodel repository.
     *
     * @param record the JSON for the command's changes.
     */
    void finish( R record );

}
