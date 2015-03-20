//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/api/commands
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a command that changes some element of a metamodel.
 */
export interface IMetamodelCommand {

    /**
     * Executes this command.
     *
     * @param jsonCmdArgs the arguments for the command as a JSON object.
     */
    execute( jsonCmdArgs : any ) : Promise<any>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface for creating commands by name.
 */
export interface IMetamodelCommandFactory {

    /**
     * Creates a new command with given type.
     *
     * @param commandTypeName the name of a command type.
     */
    makeCommand( commandTypeName : string ) : IMetamodelCommand;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

