//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/leftnavcontroller
 */

import api_commands = require( '../../../domain/metamodel/api/commands' );
import api_elements = require( '../../../domain/metamodel/api/elements' );
import elementmodel = require( '../../metamodel/elementmodel' )
import uuids = require( '../../../infrastructure/utilities/uuids' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the schema page Properties tab.
 */
export class PropertiesTabController {

    /**
     * Constructs a new schema page properties tab controller.
     * @param elementSelection the selected element whose properties are shown and changed by the tab.
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
     * Responds to changing the name.
     * @param event
     */
    public onInputChanged( event : any ) : void {

        var fieldName = event.node.name;

        switch ( fieldName ) {
            case 'name':
                this._onNameChanged( event );
                break;
            default:
                console.log( "Unknown field name: " + fieldName );
                break
        }
    }

    private _onNameChanged( event : any ) : void {

        // TODO: UUID from server
        var cmdId = uuids.makeUuid();

        var element = this._elementSelection.elementSelection;

        var pkgJson = {
            cmdId: cmdId,
            id: element.id,
            name: event.node.value
        };

        var cmd = this._metamodelCommandFactory.makeCommand( "packagedelementnamechange" );

        cmd.execute( pkgJson )

    }

    /** The left tab selection model under control. */
    private _elementSelection : elementmodel.ElementSelection;

    /** The factory for commands. */
    private _metamodelCommandFactory : api_commands.IMetamodelCommandFactory;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
