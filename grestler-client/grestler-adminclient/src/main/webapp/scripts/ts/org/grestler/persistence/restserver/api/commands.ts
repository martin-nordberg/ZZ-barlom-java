//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/persistence/restserver/api/queries
 */

import ajax = require( '../../../infrastructure/utilities/ajax' );
import values = require( '../../../infrastructure/utilities/values' );
import api_elements = require( '../../../domain/metamodel/api/elements' );
import api_queries = require( '../../../domain/metamodel/api/queries' );
import spi_commands = require( '../../../domain/metamodel/spi/commands' );
import spi_queries = require( '../../../domain/metamodel/spi/queries' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract REST Server command writer.
 */
class AbstractMetamodelCommandWriter implements spi_commands.IMetamodelCommandWriter {

    /**
     * Constructs a new vertex type creation command.  -- TODO: shared REST server configuration
     */
    constructor() {
    }

    public execute(
        jsonCmdArgs : any, cmdFinisher : spi_commands.IMetamodelCommandSpi
    ) : Promise<values.ENothing> {

        return this.writeCommand( jsonCmdArgs ).then(
            function () {
                cmdFinisher.finish( jsonCmdArgs );
                return values.nothing;
            }
        );

    }

    /**
     * Performs the database inserts needed to save the command in the given connection.
     *
     * @param jsonCmdArgs the JSON for the command.
     */
    public writeCommand( jsonCmdArgs : any ) : Promise<values.ENothing> {
        throw new Error( "Abstract method must be overridden in a derived class." );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to create a package.
 */
class PackageCreationCmdWriter extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new package creation command.
     */
    constructor() {
        super();
    }

    public writeCommand( jsonCmdArgs : any ) : Promise<values.ENothing> {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        var cmdId : string = jsonCmdArgs.cmdId;
        var id : string = jsonCmdArgs.id;
        var parentPackageId : string = jsonCmdArgs.parentPackageId;
        var name : string = jsonCmdArgs.name;

        var content = JSON.stringify(
            {
                cmdId: cmdId,
                id: id,
                parentPackageId: parentPackageId,
                name: name
            }
        );

        return ajax.httpPost(
            "http://localhost:8080/grestlerdata/metadata/commands/packagecreation",
            "application/json",
            content
        ).then(
            function () {
                return values.nothing;
            }
        );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Command to rename a packaged element.
 */
class PackagedElementNameChangeCmdWriter extends AbstractMetamodelCommandWriter {

    /**
     * Constructs a new packaged element rename command.
     */
    constructor() {
        super();
    }

    public writeCommand( jsonCmdArgs : any ) : Promise<values.ENothing> {

        // Parse the JSON arguments.
        // TODO: handle input validation problems
        var cmdId : string = jsonCmdArgs.cmdId;
        var id : string = jsonCmdArgs.id;
        var name : string = jsonCmdArgs.name;

        var content = JSON.stringify(
            {
                cmdId: cmdId,
                id: id,
                name: name
            }
        );

        return ajax.httpPost(
            "http://localhost:8080/grestlerdata/metadata/commands/packagedelementnamechange",
            "application/json",
            content
        ).then(
            function () {
                return values.nothing;
            }
        );

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Factory for metamodel commands supported by the H2 Database provider.
 */
export class MetamodelCommandWriterFactory implements spi_commands.IMetamodelCommandWriterFactory {

    /**
     * Constructs a new factory for creating metamodel commands.
     * TODO: configuration of REST server
     */
    constructor() {
    }

    public makeCommandWriter( commandTypeName : string ) : spi_commands.IMetamodelCommandWriter {

        switch ( commandTypeName.toLowerCase() ) {
            case "packagecreation":
                return new PackageCreationCmdWriter();
            case "packagedelementnamechange":
                return new PackagedElementNameChangeCmdWriter();
            default:
                throw new Error( "Unknown command type: \"" + commandTypeName + "\"." );
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

