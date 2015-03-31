//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/main/topnavviewmodel
 */

import topnavmodel = require( './topnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Page visibilities as a number of boolean attributes.
 */
class PageVisibilities {

    /**
     * Constructs a new page visibilities object.
     */
    constructor( pageSelection : topnavmodel.IPageSelection ) {
        this._pageSelection = pageSelection;
    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        var me = this;

        var setTopNavActivePage = function ( topNavSelection : topnavmodel.ETopNavSelection ) : void {
            me.isQueriesPageActive = topNavSelection == topnavmodel.ETopNavSelection.QUERIES;
            me.isSchemaPageActive = topNavSelection == topnavmodel.ETopNavSelection.SCHEMA;
            me.isServerPageActive = topNavSelection == topnavmodel.ETopNavSelection.SERVER;
        };

        setTopNavActivePage( this._pageSelection.topNavSelection );

        Object['observe'](
            this._pageSelection,
            function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'topNavSelection' ) {
                            setTopNavActivePage( change.newValue );
                        }
                    }
                )
            }, ['change.topNavSelection']
        );

    }

    /**
     * @returns the model behind this viewmodel.
     */
    public get pageSelection() : topnavmodel.IPageSelection {
        return this._pageSelection;
    }

    /** Whether the Queries page is active. */
    public isQueriesPageActive = false;

    /** Whether the Schemas page is active. */
    public isSchemaPageActive = false;

    /** Whether the Server page is active. */
    public isServerPageActive = false;

    /** The page selection model that feeds into this view model. */
    private _pageSelection : topnavmodel.IPageSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The singleton page visibilities viewmodel instance. */
var thePageVisibilities : PageVisibilities;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Loads the page visibilities viewmodel asynchronously.
 * @returns a promise for the viewmodel instance.
 */
export function loadPageVisibilities() : PageVisibilities {

    // Create the singleton on the first time through.
    if ( thePageVisibilities == null ) {
        return new PageVisibilities( topnavmodel.loadPageSelection() );
    }

    return thePageVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

