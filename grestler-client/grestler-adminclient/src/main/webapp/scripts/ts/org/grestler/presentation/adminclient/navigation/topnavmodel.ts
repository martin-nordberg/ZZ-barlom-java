//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/topnavmodel
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of top navigation items.
 */
export enum ETopNavSelection {
    QUERIES,
    SCHEMA,
    SERVER
}

export function topNavSelectionToString( value : ETopNavSelection ) : string {
    if ( value != null ) {
        switch ( value ) {
            case ETopNavSelection.QUERIES:
                return 'QUERIES';
            case ETopNavSelection.SCHEMA:
                return 'SCHEMA';
            case ETopNavSelection.SERVER:
                return 'SERVER';
        }
    }
    return null;
}

export function topNavSelectionFromString( value : string ) : ETopNavSelection {
    if ( value != null ) {
        switch ( value ) {
            case 'QUERIES' :
                return ETopNavSelection.QUERIES;
            case 'SCHEMA' :
                return ETopNavSelection.SCHEMA;
            case 'SERVER' :
                return ETopNavSelection.SERVER;
        }
    }
    return null;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining what items of the application are selected.
 */
export interface IPanelSelections {

    topNavSelection : ETopNavSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of IPanelSelections.
 */
class PanelSelections implements IPanelSelections {

    /**
     * Constructs a new panel selections object.
     */
    constructor() {
        this._topNavSelection = topNavSelectionFromString( window.localStorage[PanelSelections.PREFIX + 'topNavSelection'] );
        if ( this._topNavSelection == null ) {
            this._topNavSelection = ETopNavSelection.SCHEMA;
        }
    }

    get topNavSelection() : ETopNavSelection {
        return this._topNavSelection;
    }

    set topNavSelection( value : ETopNavSelection ) {
        // Notify observers of the change (happens asynchronously).
        Object['getNotifier']( this ).notify(
            {
                type: 'change.topNavSelection',
                name: 'topNavSelection',
                oldValue: this._topNavSelection,
                newValue: value
            }
        );

        // Make the change.
        this._topNavSelection = value;

        // Save to local storage.
        window.localStorage[PanelSelections.PREFIX + 'topNavSelection'] = topNavSelectionToString( value );
    }

    private _topNavSelection : ETopNavSelection;

    private static PREFIX = 'org.grestler.presentation.adminclient.navigation.topnavmodel.PanelSelections.';
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only panel selections instance available asynchronously. */
var thePanelSelections : Promise<IPanelSelections> = null;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Creates or returns the one and only panel selections, loading it when first requested.
 * @returns {Promise<IPanelSelections>} the panel selections, available asynchronously.
 */
export function loadPanelSelections() : Promise<IPanelSelections> {

    // Create the panel selections first time through.
    if ( thePanelSelections == null ) {
        thePanelSelections = new Promise<IPanelSelections>(
            function ( resolve : ( value? : IPanelSelections ) => void, reject : ( error? : any ) => void ) {
                // TODO: truly asynchronous if add persistence
                resolve( new PanelSelections() );
            }
        );
    }

    return thePanelSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
