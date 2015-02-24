//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi.commands;

import javax.json.JsonObject;

/**
 * Interface defining the persistence layer for a command.
 */
@FunctionalInterface
public interface IMetamodelCommandWriter {

    /**
     * Executes this command.
     *
     * @param jsonCmdArgs the arguments for the command as a JSON object.
     */
    void execute( JsonObject jsonCmdArgs, IMetamodelCommandSpi cmdFinisher );

}
