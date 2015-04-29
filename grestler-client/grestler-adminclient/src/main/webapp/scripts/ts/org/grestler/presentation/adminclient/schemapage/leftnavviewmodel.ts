//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/leftnavviewmodel
 */

import leftnavmodel = require( './leftnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Schema page left tab visibilities as a number of boolean attributes.
 */
export class LeftTabVisibilities {

    /**
     * Constructs a new schema page visibilities object.
     */
    constructor( leftTabSelection : leftnavmodel.LeftTabSelection ) {

        var me = this;

        this._leftTabSelection = leftTabSelection;

        this._modelObserver = function ( changes ) {
            try {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'leftTabSelection' ) {
                            me.setLeftTabSelection( change.newValue );
                        }
                    }
                );
            }
            catch ( error ) {
                console.log( error );
            }
        }

    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        this.setLeftTabSelection( this._leftTabSelection.leftTabSelection );

        Object['observe'](
            this._leftTabSelection,
            this._modelObserver,
            ['change.leftTabSelection']
        );

    }

    /**
     * Stops observing the associated model for changes.
     */
    public unobserveModelChanges() {

        Object['unobserve'](
            this._leftTabSelection,
            this._modelObserver
        );

    }

    /**
     * Changes the selected left tab.
     * @param leftTabSelection the new selection.
     */
    private setLeftTabSelection( leftTabSelection : leftnavmodel.ESchemaPageLeftTabSelection ) : void {
        this.isBookmarksTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
        this.isBrowseTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.BROWSE;
        this.isRecentTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.RECENT;
        this.isSearchTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.SEARCH;
    }

    /** Whether the Bookmarks page is active. */
    public isBookmarksTabActive = false;

    /** Whether the Browse page is active. */
    public isBrowseTabActive = false;

    /** Whether the Recent page is active. */
    public isRecentTabActive = false;

    /** Whether the Search page is active. */
    public isSearchTabActive = false;

    /** The observer function for right tab selection changes. */
    private _modelObserver : ( changes : any ) => void;

    /** The schema page selections model that feeds into this view model. */
    private _leftTabSelection : leftnavmodel.LeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

