//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/leftnavmodel
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of left tabs.
 */
export enum ESchemaPageLeftTabSelection {
    BOOKMARKS,
    BROWSE,
    RECENT,
    SEARCH
}

export function schemaPageLeftTabSelectionToString( value : ESchemaPageLeftTabSelection ) : string {
    if ( value != null ) {
        return ESchemaPageLeftTabSelection[value];
    }
    return null;
}

export function schemaPageLeftTabSelectionFromString( value : string ) : ESchemaPageLeftTabSelection {
    if ( value != null ) {
        switch ( value ) {
            case 'BOOKMARKS' :
                return ESchemaPageLeftTabSelection.BOOKMARKS;
            case 'BROWSE' :
                return ESchemaPageLeftTabSelection.BROWSE;
            case 'RECENT' :
                return ESchemaPageLeftTabSelection.RECENT;
            case 'SEARCH' :
                return ESchemaPageLeftTabSelection.SEARCH;
        }
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining what items of the Schema page are selected.
 */
export interface ILeftTabSelection {

    leftTabSelection : ESchemaPageLeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of ILeftTabSelection.
 */
class LeftTabSelection implements ILeftTabSelection {

    /**
     * Constructs a new Schema page selections object.
     */
    constructor() {
        this._leftTabSelection = schemaPageLeftTabSelectionFromString( window.localStorage[LeftTabSelection.PREFIX + 'leftTabSelection'] );
        if ( this._leftTabSelection == null ) {
            this._leftTabSelection = ESchemaPageLeftTabSelection.BROWSE;
        }
    }

    get leftTabSelection() : ESchemaPageLeftTabSelection {
        return this._leftTabSelection;
    }

    set leftTabSelection( value : ESchemaPageLeftTabSelection ) {
        // Notify observers of the change (happens asynchronously).
        Object['getNotifier']( this ).notify(
            {
                type: 'change.leftTabSelection',
                name: 'leftTabSelection',
                oldValue: this._leftTabSelection,
                newValue: value
            }
        );

        // Make the change.
        this._leftTabSelection = value;

        // Save to local storage.
        window.localStorage[LeftTabSelection.PREFIX + 'leftTabSelection'] = schemaPageLeftTabSelectionToString( value );
    }

    private _leftTabSelection : ESchemaPageLeftTabSelection;

    /** Key prefix used for key/value local storage of this class's attributes. */
    private static PREFIX = 'org.grestler.presentation.adminclient.schemapage.leftnavmodel.LeftTabSelection.';

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only schema page selections instance. */
var theLeftTabSelection : ILeftTabSelection = null;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Creates or returns the one and only schema page left tab selection model, loading it when first requested.
 * @returns {ILeftTabSelection} the left tab selection.
 */
export function provideSchemaPageLeftTabSelection() : ILeftTabSelection {

    // Create the page selection first time through.
    if ( theLeftTabSelection == null ) {
        theLeftTabSelection = new LeftTabSelection();
    }

    return theLeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
