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
export class PageVisibilities {

    /**
     * Constructs a new page visibilities object.
     */
    constructor( pageSelection : topnavmodel.PageSelection ) {

        var me = this;

        me._pageSelection = pageSelection;

        me._modelObserver = function ( changes ) {
            changes.forEach(
                function ( change ) {
                    console.log( change );
                    if ( change.name == 'topNavSelection' ) {
                        me.setPageSelection( change.newValue );
                    }
                }
            )
        }

    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        this.setPageSelection( this._pageSelection.topNavSelection );

        Object['observe'](
            this._pageSelection,
            this._modelObserver,
            ['change.topNavSelection']
        );

    }

    /**
     * Stops observing the associated model for changes.
     */
    public unobserveModelChanges() {

        Object['unobserve'](
            this._pageSelection,
            this._modelObserver,
            ['change.topNavSelection']
        );

    }


    /**
     * @returns the model behind this viewmodel.
     */
    public get pageSelection() : topnavmodel.PageSelection {
        return this._pageSelection;
    }

    /**
     * Changes the selected page.
     * @param topNavSelection the new selection.
     */
    private setPageSelection( topNavSelection : topnavmodel.ETopNavSelection ) : void {
        this.isQueriesPageActive = topNavSelection == topnavmodel.ETopNavSelection.QUERIES;
        this.isSchemaPageActive = topNavSelection == topnavmodel.ETopNavSelection.SCHEMA;
        this.isServerPageActive = topNavSelection == topnavmodel.ETopNavSelection.SERVER;
    }

    /** Whether the Queries page is active. */
    public isQueriesPageActive = false;

    /** Whether the Schemas page is active. */
    public isSchemaPageActive = false;

    /** Whether the Server page is active. */
    public isServerPageActive = false;

    /** The observer function for top nav selection changes. */
    private _modelObserver : ( changes : any ) => void;

    /** The page selection model that feeds into this view model. */
    private _pageSelection : topnavmodel.PageSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

