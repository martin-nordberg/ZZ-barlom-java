//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

import javax.json.JsonObject;

/**
 * Service provide callback interface for completing a command after it has been persisted.
 */
@FunctionalInterface
public interface IMetamodelCommandSpi {

    /**
     * Write the command's changes into the metamodel repository.
     *
     * @param jsonCmdArgs the JSON for the command's changes.
     */
    void finish( JsonObject jsonCmdArgs );

}
