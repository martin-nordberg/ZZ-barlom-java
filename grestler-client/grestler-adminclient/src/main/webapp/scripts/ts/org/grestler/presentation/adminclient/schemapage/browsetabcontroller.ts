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
export class BrowseTabController {

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
        var parentPkg = <api_elements.IPackage> this._elementSelection.elementSelection;
        var parentPkgId = parentPkg.id;

        var pkgName = "package";
        var isUnique = false;
        for ( var counter = 1 ; !isUnique && counter < 1000 ; counter += 1 ) {
            isUnique = true;
            pkgName = "package" + counter;
            parentPkg.childPackages.forEach( function( pkg ) {
                if ( pkg.name === pkgName ) {
                    isUnique = false;
                    return false;
                }
            });
        }

        var pkgJson = {
            cmdId: cmdId,
            id: pkgId,
            parentPackageId: parentPkgId,
            name: pkgName
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
