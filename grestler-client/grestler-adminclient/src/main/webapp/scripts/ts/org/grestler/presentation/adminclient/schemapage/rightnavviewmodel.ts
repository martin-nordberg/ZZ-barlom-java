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

        var me = this;

        this._rightTabSelection = rightTabSelection;

        this._modelObserver = function ( changes ) {
            changes.forEach(
                function ( change ) {
                    console.log( change );
                    if ( change.name == 'rightTabSelection' ) {
                        me.setRightTabSelection( change.newValue );
                    }
                }
            )
        }

    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        var me = this;

        me.setRightTabSelection( this._rightTabSelection.rightTabSelection );

        Object['observe'](
            this._rightTabSelection,
            this._modelObserver,
            ['change.rightTabSelection']
        );

    }

    /**
     * Stops observing the associated model for changes.
     */
    public unobserveModelChanges() {

        Object['unobserve'](
            this._rightTabSelection,
            this._modelObserver
        );

    }

    /**
     * @returns the model behind this viewmodel.
     */
    public get rightTabSelection() : rightnavmodel.RightTabSelection {
        return this._rightTabSelection;
    }

    /**
     * Changes the selected right tab.
     * @param rightTabSelection the new selection.
     */
    private setRightTabSelection( rightTabSelection : rightnavmodel.ESchemaPageRightTabSelection ) : void {
        this.isDiagramsTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
        this.isDocumentationTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
        this.isPropertiesTabActive = rightTabSelection == rightnavmodel.ESchemaPageRightTabSelection.PROPERTIES;
    }

    /** Whether the Diagrams page is active. */
    public isDiagramsTabActive = false;

    /** Whether the Documentation page is active. */
    public isDocumentationTabActive = false;

    /** Whether the Properties page is active. */
    public isPropertiesTabActive = false;

    /** The observer function for right tab selection changes. */
    private _modelObserver : ( changes : any ) => void;

    /** The schema page selections model that feeds into this view model. */
    private _rightTabSelection : rightnavmodel.RightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

