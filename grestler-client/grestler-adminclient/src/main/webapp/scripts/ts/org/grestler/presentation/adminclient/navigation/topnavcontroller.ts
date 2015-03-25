//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/topnavcontroller
 */

import topnavmodel = require( '../navigation/topnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the top navigation bar.
 */
export class TopNavController {

    /**
     * Constructs a new top navigation controller.
     * @param panelSelections the panel selections controlled by the top nav buttons.
     */
    constructor( panelSelections : topnavmodel.IPanelSelections ) {
        this._panelSelections = panelSelections;
    }

    /**
     * Responds to clicking the "Queries" top nav button.
     * @param event
     */
    public onQueriesClicked( event : any ) : void {
        this._panelSelections.topNavSelection = topnavmodel.ETopNavSelection.QUERIES;
    }

    /**
     * Responds to clicking the "Schema" top nav button.
     * @param event
     */
    public onSchemaClicked( event : any ) : void {
        this._panelSelections.topNavSelection = topnavmodel.ETopNavSelection.SCHEMA;
    }

    /**
     * Responds to clicking the "Search" top nav button.
     * @param event
     */
    public onSearchClicked( event : any ) : void {
        alert( 'TODO: Search Clicked!' );
    }

    /**
     * Responds to clicking the "Server" top nav button.
     * @param event
     */
    public onServerClicked( event : any ) : void {
        this._panelSelections.topNavSelection = topnavmodel.ETopNavSelection.SERVER;
    }

    /** The panel selections model under control. */
    private _panelSelections : topnavmodel.IPanelSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
