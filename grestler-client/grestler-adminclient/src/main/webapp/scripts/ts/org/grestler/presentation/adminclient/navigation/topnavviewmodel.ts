//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/topnavviewmodel
 */

import topnavmodel = require( '../navigation/topnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Page visibilities as a number of boolean attributes.
 */
class PageVisibilities {

    /**
     * Constructs a new page visibilities object.
     */
    constructor( pageSelection : topnavmodel.IPageSelection ) {
        var me = this;

        this._pageSelection = pageSelection;

        var setTopNavActivePage = function ( topNavSelection : topnavmodel.ETopNavSelection ) : void {
            me.isQueriesPageActive = topNavSelection == topnavmodel.ETopNavSelection.QUERIES;
            me.isSchemaPageActive = topNavSelection == topnavmodel.ETopNavSelection.SCHEMA;
            me.isServerPageActive = topNavSelection == topnavmodel.ETopNavSelection.SERVER;
        };

        setTopNavActivePage( pageSelection.topNavSelection );

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
var thePageVisibilities : Promise<PageVisibilities>;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Loads the page visibilities viewmodel asynchronously.
 * @returns a promise for the viewmodel instance.
 */
export function loadPageVisibilities() : Promise<PageVisibilities> {

    // Create the singleton on the first time through.
    if ( thePageVisibilities == null ) {
        thePageVisibilities = topnavmodel.loadPageSelection().then(
            function ( pageSelection : topnavmodel.IPageSelection ) : PageVisibilities {
                return new PageVisibilities( pageSelection );
            }
        );
    }

    return thePageVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

