//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.commands;

/**
 * Interface for creating commands by name.
 */
@FunctionalInterface
public interface IMetamodelCommandWriterFactory {

    /**
     * Creates a new command writer with given type.
     *
     * @param commandTypeName the name of a command type.
     */
    IMetamodelCommandWriter makeCommandWriter( String commandTypeName );

}
