//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/leftnavcontroller
 */

import leftnavmodel = require( './leftnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the schema page left tabs schemapage.
 */
export class LeftNavController {

    /**
     * Constructs a new schema page left schemapage controller.
     * @param leftTabSelection the left tab selection maintained by this controller.
     */
    constructor( leftTabSelection : leftnavmodel.LeftTabSelection ) {
        this._leftTabSelection = leftTabSelection;
    }

    /**
     * Responds to clicking the "Bookmarks" tab.
     * @param event
     */
    public onBookmarksTabClicked( event : any ) : void {
        this._leftTabSelection.leftTabSelection = leftnavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
    }

    /**
     * Responds to clicking the "Browse" tab.
     * @param event
     */
    public onBrowseTabClicked( event : any ) : void {
        this._leftTabSelection.leftTabSelection = leftnavmodel.ESchemaPageLeftTabSelection.BROWSE;
    }

    /**
     * Responds to clicking the "Recent" tab.
     * @param event
     */
    public onRecentTabClicked( event : any ) : void {
        this._leftTabSelection.leftTabSelection = leftnavmodel.ESchemaPageLeftTabSelection.RECENT;
    }

    /**
     * Responds to clicking the "Search" tab.
     * @param event
     */
    public onSearchTabClicked( event : any ) : void {
        this._leftTabSelection.leftTabSelection = leftnavmodel.ESchemaPageLeftTabSelection.SEARCH;
    }

    /** The left tab selection model under control. */
    private _leftTabSelection : leftnavmodel.LeftTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
