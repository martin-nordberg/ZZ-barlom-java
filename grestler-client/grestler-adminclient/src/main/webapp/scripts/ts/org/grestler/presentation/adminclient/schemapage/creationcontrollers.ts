//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/creationcontrollers
 */

import api_commands = require( '../../../domain/metamodel/api/commands' );
import api_elements = require( '../../../domain/metamodel/api/elements' );
import elementmodel = require( '../../metamodel/elementmodel' );
import uuids = require( '../../../infrastructure/utilities/uuids' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for creating packages.
 */
export class PackageCreationController {

    /**
     * Constructs a new schema page package creation controller.
     * @param elementSelection the browsed element holding the parent package for the new child package.
     * @param metamodelCommandFactory the factory for command creation.
     */
    constructor(
        elementSelection : elementmodel.ElementSelection,
        metamodelCommandFactory : api_commands.IMetamodelCommandFactory
    ) {
        this._elementSelection = elementSelection;
        this._metamodelCommandFactory = metamodelCommandFactory;
    }

    /**
     * Responds to clicking the "Add package" link.
     * @param event
     */
    public onAddPackageClicked( event : any ) : void {

        // TODO: UUIDs from server
        var cmdId = uuids.makeUuid();
        var pkgId = uuids.makeUuid();
        var parentPkgId = this._elementSelection.elementSelection.id;

        var pkgJson = {
            cmdId: cmdId,
            id: pkgId,
            parentPackageId: parentPkgId,
            name: "package" + PackageCreationController._counter++
        };

        var cmd = this._metamodelCommandFactory.makeCommand( "packagecreation" );

        cmd.execute( pkgJson )
    }

    /** Counter for adding to names for more likely uniqueness. */
    private static _counter : number = 1;

    /** The selected element to be watched. */
    private _elementSelection : elementmodel.ElementSelection;

    /** The factory for commands. */
    private _metamodelCommandFactory : api_commands.IMetamodelCommandFactory;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
