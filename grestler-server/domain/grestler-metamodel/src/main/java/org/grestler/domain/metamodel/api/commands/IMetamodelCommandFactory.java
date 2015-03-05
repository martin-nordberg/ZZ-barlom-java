//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.commands;

/**
 * Interface for creating commands by name.
 */
@FunctionalInterface
public interface IMetamodelCommandFactory {

    /**
     * Creates a new command with given type.
     *
     * @param commandTypeName the name of a command type.
     */
    IMetamodelCommand makeCommand( String commandTypeName );

}
