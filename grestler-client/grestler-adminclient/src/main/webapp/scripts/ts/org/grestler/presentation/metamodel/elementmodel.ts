//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/metamodel/elementmodel
 */

import api_elements = require( '../../domain/metamodel/api/elements' )
import api_queries = require( '../../domain/metamodel/api/queries' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The model for a selected element.
 */
export class ElementSelection {

    /**
     * Constructs a new element selection object.
     */
    constructor( metamodelRepository : api_queries.IMetamodelRepository ) {
        this._elementSelection = null; // TODO: root package
    }

    get elementSelection() : api_elements.IDocumentedElement {
        return this._elementSelection;
    }

    set elementSelection( value : api_elements.IDocumentedElement ) {
        // Notify observers of the change (happens asynchronously).
        Object['getNotifier']( this ).notify(
            {
                type: 'change.elementSelection',
                name: 'elementSelection',
                oldValue: this._elementSelection,
                newValue: value
            }
        );

        // Make the change.
        this._elementSelection = value;

    }

    private _elementSelection : api_elements.IDocumentedElement;

    // TODO: store selection

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
