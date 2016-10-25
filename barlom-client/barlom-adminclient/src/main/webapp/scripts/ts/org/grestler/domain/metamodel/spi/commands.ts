//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/spi/commands
 */

import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Service provide callback interface for completing a command after it has been persisted.
 */
export interface IMetamodelCommandSpi {

    /**
     * Write the command's changes into the metamodel repository.
     *
     * @param jsonCmdArgs the JSON for the command's changes.
     */
    finish( jsonCmdArgs : any ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining the persistence layer for a command.
 */
export interface IMetamodelCommandWriter {

    /**
     * Executes this command.
     *
     * @param jsonCmdArgs the arguments for the command as a JSON object.
     */
    execute( jsonCmdArgs : any, cmdFinisher : IMetamodelCommandSpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface for creating commands by name.
 */
export interface IMetamodelCommandWriterFactory {

    /**
     * Creates a new command writer with given type.
     *
     * @param commandTypeName the name of a command type.
     */
    makeCommandWriter( commandTypeName : string ) : IMetamodelCommandWriter;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

