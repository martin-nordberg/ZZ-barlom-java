//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/schemapagenavmodel
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
export interface ISchemaPageSelections {

    leftTabSelection : ESchemaPageLeftTabSelection;

    rightTabSelection : ESchemaPageRightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of ISchemaPageSelections.
 */
class SchemaPageSelections implements ISchemaPageSelections {

    /**
     * Constructs a new Schema page selections object.
     */
    constructor() {
        this._leftTabSelection = schemaPageLeftTabSelectionFromString( window.localStorage[SchemaPageSelections.PREFIX + 'leftTabSelection'] );
        if ( this._leftTabSelection == null ) {
            this._leftTabSelection = ESchemaPageLeftTabSelection.BROWSE;
        }
        this._rightTabSelection = schemaPageRightTabSelectionFromString( window.localStorage[SchemaPageSelections.PREFIX + 'rightTabSelection'] );
        if ( this._rightTabSelection == null ) {
            this._rightTabSelection = ESchemaPageRightTabSelection.PROPERTIES;
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
        window.localStorage[SchemaPageSelections.PREFIX + 'leftTabSelection'] = schemaPageLeftTabSelectionToString( value );
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
        window.localStorage[SchemaPageSelections.PREFIX + 'rightTabSelection'] = schemaPageRightTabSelectionToString( value );
    }

    private _leftTabSelection : ESchemaPageLeftTabSelection;

    private _rightTabSelection : ESchemaPageRightTabSelection;

    private static PREFIX = 'org.grestler.presentation.adminclient.navigation.schemapagemodel.SchemaPageSelections.';
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only schema page selections instance available asynchronously. */
var theSchemaPageSelections : Promise<ISchemaPageSelections> = null;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Creates or returns the one and only schema page selections model, loading it when first requested.
 * @returns {Promise<ISchemaPageSelections>} the page selection, available asynchronously.
 */
export function loadSchemaPageSelections() : Promise<ISchemaPageSelections> {

    // Create the page selection first time through.
    if ( theSchemaPageSelections == null ) {
        theSchemaPageSelections = new Promise<ISchemaPageSelections>(
            function ( resolve : ( value? : ISchemaPageSelections ) => void, reject : ( error? : any ) => void ) {
                // TODO: truly asynchronous if add persistence
                resolve( new SchemaPageSelections() );
            }
        );
    }

    return theSchemaPageSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
