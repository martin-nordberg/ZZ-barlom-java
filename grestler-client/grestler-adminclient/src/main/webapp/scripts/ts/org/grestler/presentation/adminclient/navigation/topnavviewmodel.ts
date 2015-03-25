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
 * Panel visibilities as a number of boolean attributes.
 */
class PanelVisibilities {

    /**
     * Constructs a new panel visibilities object.
     */
    constructor( panelSelections : topnavmodel.IPanelSelections ) {
        var me = this;

        this._panelSelections = panelSelections;

        Object['observe'](
            this._panelSelections,
            function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'topNavSelection' ) {
                            me.isTopNavQueriesPageActive = change.newValue == topnavmodel.ETopNavSelection.QUERIES;
                            me.isTopNavSchemaPageActive = change.newValue == topnavmodel.ETopNavSelection.SCHEMA;
                            me.isTopNavServerPageActive = change.newValue == topnavmodel.ETopNavSelection.SERVER;
                        }
                    }
                )
            }, ['change.topNavSelection']
        );
    }

    public isTopNavQueriesPageActive = false;

    public isTopNavSchemaPageActive = true;

    public isTopNavServerPageActive = false;

    private _panelSelections : topnavmodel.IPanelSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var thePanelVisibilities : Promise<PanelVisibilities>;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export function loadPanelVisibilities() : Promise<PanelVisibilities> {

    if ( thePanelVisibilities == null ) {
        thePanelVisibilities = topnavmodel.loadPanelSelections().then(
            function( panelSelections : topnavmodel.IPanelSelections ) : PanelVisibilities {
                return new PanelVisibilities( panelSelections );
            }
        );
    }

    return thePanelVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

