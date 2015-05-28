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
 * Controller for the schema page Properties panel.
 */
export class PropertiesPanelController {

    /**
     * Constructs a new schema page properties panel controller.
     * @param elementSelection the selected element whose properties are shown and changed by the panel.
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
     * Responds to changing a radio field.
     * @param event the Ractive event capturing the change.
     */
    public onRadioChanged( event : any ) : void {

        var fieldName = event.node.name;

        switch ( fieldName ) {
            case 'vertexTypeAbstractness':
                this._onVertexTypeAbstractnessChanged( event.node.value );
                break;
            default:
                console.log( "Unknown field name: " + fieldName );
                break
        }

    }

    /**
     * Responds to changing a text field.
     * @param event the Ractive event signifying the change.
     */
    public onTextChanged( event : any ) : void {

        var fieldName = event.node.name;

        switch ( fieldName ) {
            case 'name':
                this._onNameChanged( event.node.value );
                break;
            default:
                console.log( "Unknown field name: " + fieldName );
                break
        }

    }

    /**
     * Responds to changing the name.
     * @param newName the new element name.
     */
    private _onNameChanged( newName : string ) : void {

        // TODO: UUID from server
        var cmdId = uuids.makeUuid();

        var pkgJson = {
            cmdId: cmdId,
            id: this._elementSelection.elementSelection.id,
            name: newName
        };

        var cmd = this._metamodelCommandFactory.makeCommand( "packagedelementnamechange" );

        cmd.execute( pkgJson )

    }

    /**
     * Responds to a change in the abstractness of a vertex type.
     * @param abstractnessNumStr the new value as a numeric string.
     * @private
     */
    private _onVertexTypeAbstractnessChanged( abstractnessNumStr : string ) : void {

        // TODO: UUID from server
        var cmdId = uuids.makeUuid();

        var pkgJson = {
            cmdId: cmdId,
            id: this._elementSelection.elementSelection.id,
            abstractness: api_elements.EAbstractness[parseInt( abstractnessNumStr, 10 )]
        };

        var cmd = this._metamodelCommandFactory.makeCommand( "vertextypeabstractnesschange" );

        cmd.execute( pkgJson )

    }

    /** The panel selection model under control. */
    private _elementSelection : elementmodel.ElementSelection;

    /** The factory for commands. */
    private _metamodelCommandFactory : api_commands.IMetamodelCommandFactory;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
