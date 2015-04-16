//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/schemapageviewmodel
 */

import rightnavmodel = require( './rightnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Schema page component visibilities as a number of boolean attributes.
 */
export class RightTabVisibilities {

    /**
     * Constructs a new schema page visibilities object.
     */
    constructor( rightTabSelection : rightnavmodel.RightTabSelection ) {
        this._rightTabSelection = rightTabSelection;
    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        var me = this;

        var setActiveRightTab = function ( rightTabSelection : rightnavmodel.ESchemaPageRightTabSelection ) : void {
            me.isDiagramsTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
            me.isDocumentationTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
            me.isPropertiesTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.PROPERTIES;
        };

        setActiveRightTab( this._rightTabSelection.rightTabSelection );

        Object['observe'](
            this._rightTabSelection,
            function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'rightTabSelection' ) {
                            setActiveRightTab( change.newValue );
                        }
                    }
                )
            }, ['change.rightTabSelection']
        );

    }

    /**
     * @returns the model behind this viewmodel.
     */
    public get rightTabSelection() : rightnavmodel.RightTabSelection {
        return this._rightTabSelection;
    }

    /** Whether the Diagrams page is active. */
    public isDiagramsTabActive = false;

    /** Whether the Documentation page is active. */
    public isDocumentationTabActive = false;

    /** Whether the Properties page is active. */
    public isPropertiesTabActive = false;

    /** The schema page selections model that feeds into this view model. */
    private _rightTabSelection : rightnavmodel.RightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

