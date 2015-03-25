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

        var setTopNavActivePage = function ( topNavSelection : topnavmodel.ETopNavSelection ) : void {
            me.isTopNavQueriesPageActive = topNavSelection == topnavmodel.ETopNavSelection.QUERIES;
            me.isTopNavSchemaPageActive = topNavSelection == topnavmodel.ETopNavSelection.SCHEMA;
            me.isTopNavServerPageActive = topNavSelection == topnavmodel.ETopNavSelection.SERVER;
        };

        setTopNavActivePage( panelSelections.topNavSelection );

        Object['observe'](
            this._panelSelections,
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
    public get panelSelections() : topnavmodel.IPanelSelections {
        return this._panelSelections;
    }

    /** Whether the Queries page is active. */
    public isTopNavQueriesPageActive = false;

    /** Whether the Schemas page is active. */
    public isTopNavSchemaPageActive = false;

    /** Whether the Server page is active. */
    public isTopNavServerPageActive = false;

    /** The panel selections model that feeds into this view model. */
    private _panelSelections : topnavmodel.IPanelSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The singleton panel visibilities viewmodel instance. */
var thePanelVisibilities : Promise<PanelVisibilities>;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Loads the panel visibilities viewmodel asynchronously.
 * @returns a promise for the viewmodel instance.
 */
export function loadPanelVisibilities() : Promise<PanelVisibilities> {

    // Create the singeton on the first time through.
    if ( thePanelVisibilities == null ) {
        thePanelVisibilities = topnavmodel.loadPanelSelections().then(
            function ( panelSelections : topnavmodel.IPanelSelections ) : PanelVisibilities {
                return new PanelVisibilities( panelSelections );
            }
        );
    }

    return thePanelVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

