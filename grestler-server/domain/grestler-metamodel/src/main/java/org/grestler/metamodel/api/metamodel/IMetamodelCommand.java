//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.metamodel;

import javax.json.JsonObject;
import java.util.UUID;

/**
 * Interface to a command that changes some element of a metamodel.
 */
public interface IMetamodelCommand {

    /**
     * Executes this command.
     *
     * @param jsonCmdArgs the arguments for the command as a JSON object.
     */
    void execute( JsonObject jsonCmdArgs );

    /**
     * @return the unique ID of this command.
     */
    UUID getCmdId();

}
