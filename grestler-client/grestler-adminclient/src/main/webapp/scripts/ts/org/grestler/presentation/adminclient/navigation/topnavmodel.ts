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
    SERVER,
    SCHEMA,
    QUERIES
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
     * TODO: persistence in the browser
     */
    constructor() {
        this._topNavSelection = ETopNavSelection.SCHEMA;
    }

    get topNavSelection() : ETopNavSelection {
        return this._topNavSelection;
    }

    set topNavSelection( value : ETopNavSelection ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.topNavSelection',
                name: 'topNavSelection',
                oldValue: this._topNavSelection,
                newValue: value
            }
        );
        this._topNavSelection = value;
    }

    private _topNavSelection : ETopNavSelection;

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
