//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.metamodel;

import org.grestler.metamodel.api.metamodel.IMetamodelCommand;

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
