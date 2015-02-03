//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api;

/**
 * Interface for creating commands by name.
 */
@FunctionalInterface
public interface IMetamodelCommandFactory {

    /**
     * Creates a new command with given name for given arguments.
     *
     * @param commandName the name of a command.
     */
    IMetamodelCommand makeCommand( String commandName );

}
