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
        this._leftTabSelection = leftTabSelection;
    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        var me = this;

        var setActiveLeftTab = function ( leftTabSelection : leftnavmodel.ESchemaPageLeftTabSelection ) : void {
            me.isBookmarksTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
            me.isBrowseTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.BROWSE;
            me.isRecentTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.RECENT;
            me.isSearchTabActive = leftTabSelection == leftnavmodel.ESchemaPageLeftTabSelection.SEARCH;
        };

        setActiveLeftTab( this._leftTabSelection.leftTabSelection );

        Object['observe'](
            this._leftTabSelection,
            function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'leftTabSelection' ) {
                            setActiveLeftTab( change.newValue );
                        }
                    }
                )
            }, ['change.leftTabSelection']
        );

    }

    /**
     * @returns the model behind this viewmodel.
     */
    public get leftTabSelection() : leftnavmodel.LeftTabSelection {
        return this._leftTabSelection;
    }

    /** Whether the Bookmarks page is active. */
    public isBookmarksTabActive = false;

    /** Whether the Browse page is active. */
    public isBrowseTabActive = false;

    /** Whether the Recent page is active. */
    public isRecentTabActive = false;

    /** Whether the Search page is active. */
    public isSearchTabActive = false;

    /** The schema page selections model that feeds into this view model. */
    private _leftTabSelection : leftnavmodel.LeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

