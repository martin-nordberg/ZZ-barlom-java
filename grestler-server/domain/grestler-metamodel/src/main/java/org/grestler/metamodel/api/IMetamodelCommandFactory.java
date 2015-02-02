//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api;

import javax.json.stream.JsonParser;

/**
 * Interface for creating commands by name.
 */
@FunctionalInterface
public interface IMetamodelCommandFactory {

    /**
     * Creates a new command with given name for given arguments.
     *
     * @param commandName     the name of a command.
     * @param jsonCommandArgs the JSON giving the arguments needed by the command.
     */
    IMetamodelCommand makeCommand( String commandName, JsonParser jsonCommandArgs );

}
