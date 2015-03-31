//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/leftnavviewmodel
 */

import schemapage_leftnavmodel = require( './leftnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Schema page left tab visibilities as a number of boolean attributes.
 */
class LeftTabVisibilities {

    /**
     * Constructs a new schema page visibilities object.
     */
    constructor( leftTabSelection : schemapage_leftnavmodel.ILeftTabSelection ) {
        this._leftTabSelection = leftTabSelection;
    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        var me = this;

        var setActiveLeftTab = function ( leftTabSelection : schemapage_leftnavmodel.ESchemaPageLeftTabSelection ) : void {
            me.isBookmarksTabActive = leftTabSelection == schemapage_leftnavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
            me.isBrowseTabActive = leftTabSelection == schemapage_leftnavmodel.ESchemaPageLeftTabSelection.BROWSE;
            me.isRecentTabActive = leftTabSelection == schemapage_leftnavmodel.ESchemaPageLeftTabSelection.RECENT;
            me.isSearchTabActive = leftTabSelection == schemapage_leftnavmodel.ESchemaPageLeftTabSelection.SEARCH;
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
    public get leftTabSelection() : schemapage_leftnavmodel.ILeftTabSelection {
        return this._leftTabSelection;
    }

    /** Whether the Browse page is active. */
    public isBrowseTabActive = false;

    /** Whether the Bookmarks page is active. */
    public isBookmarksTabActive = false;

    /** Whether the Recent page is active. */
    public isRecentTabActive = false;

    /** Whether the Search page is active. */
    public isSearchTabActive = false;

    /** The schema page selections model that feeds into this view model. */
    private _leftTabSelection : schemapage_leftnavmodel.ILeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The singleton page visibilities viewmodel instance. */
var theLeftTabVisibilities : LeftTabVisibilities;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Loads the page visibilities viewmodel asynchronously.
 * @returns a promise for the viewmodel instance.
 */
export function loadLeftTabVisibilities() : LeftTabVisibilities {

    // Create the singleton on the first time through.
    if ( theLeftTabVisibilities == null ) {
        theLeftTabVisibilities = new LeftTabVisibilities( schemapage_leftnavmodel.loadLeftTabSelection() );
    }

    return theLeftTabVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

