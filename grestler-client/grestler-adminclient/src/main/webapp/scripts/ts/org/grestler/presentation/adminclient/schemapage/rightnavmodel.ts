//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/rightnavmodel
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of right tabs.
 */
export enum ESchemaPageRightTabSelection {
    DIAGRAMS,
    DOCUMENTATION,
    PROPERTIES
}

export function schemaPageRightTabSelectionToString( value : ESchemaPageRightTabSelection ) : string {
    if ( value != null ) {
        return ESchemaPageRightTabSelection[value];
    }
    return null;
}

export function schemaPageRightTabSelectionFromString( value : string ) : ESchemaPageRightTabSelection {
    if ( value != null ) {
        switch ( value ) {
            case 'DIAGRAMS' :
                return ESchemaPageRightTabSelection.DIAGRAMS;
            case 'DOCUMENTATION' :
                return ESchemaPageRightTabSelection.DOCUMENTATION;
            case 'PROPERTIES' :
                return ESchemaPageRightTabSelection.PROPERTIES;
        }
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining what items of the Schema page are selected.
 */
export interface IRightTabSelection {

    rightTabSelection : ESchemaPageRightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of IRightTabSelection.
 */
class RightTabSelection implements IRightTabSelection {

    /**
     * Constructs a new Schema page selections object.
     */
    constructor() {
        this._rightTabSelection = schemaPageRightTabSelectionFromString( window.localStorage[RightTabSelection.PREFIX + 'rightTabSelection'] );
        if ( this._rightTabSelection == null ) {
            this._rightTabSelection = ESchemaPageRightTabSelection.PROPERTIES;
        }
    }

    get rightTabSelection() : ESchemaPageRightTabSelection {
        return this._rightTabSelection;
    }

    set rightTabSelection( value : ESchemaPageRightTabSelection ) {
        // Notify observers of the change (happens asynchronously).
        Object['getNotifier']( this ).notify(
            {
                type: 'change.rightTabSelection',
                name: 'rightTabSelection',
                oldValue: this._rightTabSelection,
                newValue: value
            }
        );

        // Make the change.
        this._rightTabSelection = value;

        // Save to local storage.
        window.localStorage[RightTabSelection.PREFIX + 'rightTabSelection'] = schemaPageRightTabSelectionToString( value );
    }

    private _rightTabSelection : ESchemaPageRightTabSelection;

    /** Key prefix used for key/value local storage of this class's attributes. */
    private static PREFIX = 'org.grestler.presentation.adminclient.schemapage.rightnavmodel.RightTabSelection.';

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only schema page selections instance. */
var theRightTabSelection : IRightTabSelection = null;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Creates or returns the one and only schema page right tab selection model, loading it when first requested.
 * @returns {IRightTabSelection} the right tab selection.
 */
export function loadRightTabSelection() : IRightTabSelection {

    // Create the page selection first time through.
    if ( theRightTabSelection == null ) {
        theRightTabSelection = new RightTabSelection();
    }

    return theRightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
