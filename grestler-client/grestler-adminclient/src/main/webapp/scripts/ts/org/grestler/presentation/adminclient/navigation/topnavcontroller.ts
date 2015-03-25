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
 * Responds to clicking the "Queries" top nav button.
 * @param event
 */
export function onQueriesClicked( event : any ) : void {

    topnavmodel.loadPanelSelections().then( function( panelSelections : topnavmodel.IPanelSelections ) : void {
        panelSelections.topNavSelection = topnavmodel.ETopNavSelection.QUERIES;
    } );

    alert( 'TODO: Queries Clicked!' );
    // TODO: revise the panel selection model
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Responds to clicking the "Schema" top nav button.
 * @param event
 */
export function onSchemaClicked( event : any ) : void {

    topnavmodel.loadPanelSelections().then( function( panelSelections : topnavmodel.IPanelSelections ) : void {
        panelSelections.topNavSelection = topnavmodel.ETopNavSelection.SCHEMA;
    } );

    alert( 'TODO: Schema Clicked!' );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Responds to clicking the "Search" top nav button.
 * @param event
 */
export function onSearchClicked( event : any ) : void {

    alert( 'TODO: Search Clicked!' );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Responds to clicking the "Server" top nav button.
 * @param event
 */
export function onServerClicked( event : any ) : void {

    topnavmodel.loadPanelSelections().then( function( panelSelections : topnavmodel.IPanelSelections ) : void {
        panelSelections.topNavSelection = topnavmodel.ETopNavSelection.SERVER;
    } );

    alert( 'TODO: Server Clicked!' );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
