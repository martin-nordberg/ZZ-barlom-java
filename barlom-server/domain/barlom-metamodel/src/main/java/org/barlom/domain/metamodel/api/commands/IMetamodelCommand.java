//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.api.commands;

import javax.json.JsonObject;

/**
 * Interface to a command that changes some element of a metamodel.
 */
@FunctionalInterface
public interface IMetamodelCommand {

    /**
     * Executes this command.
     *
     * @param jsonCmdArgs the arguments for the command as a JSON object.
     */
    void execute( JsonObject jsonCmdArgs );

}
