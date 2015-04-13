//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/browsedPackagedElementModel
 */

import api_elements = require( '../../../domain/metamodel/api/elements' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The model for the browsed element in the schema page.
 */
export class ElementSelection {

    /**
     * Constructs a new packaged element selection object.
     */
    constructor() {
        this._packagedElementSelection = null; // TODO: root package
    }

    get elementSelection() : api_elements.IPackagedElement {
        return this._packagedElementSelection;
    }

    set elementSelection( value : api_elements.IPackagedElement ) {
        // Notify observers of the change (happens asynchronously).
        Object['getNotifier']( this ).notify(
            {
                type: 'change.packagedElementSelection',
                name: 'packagedElementSelection',
                oldValue: this._packagedElementSelection,
                newValue: value
            }
        );

        // Make the change.
        this._packagedElementSelection = value;

    }

    private _packagedElementSelection : api_elements.IPackagedElement;

    // TODO: store selection

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
