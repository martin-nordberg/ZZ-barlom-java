//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/schemapagenavcontroller
 */

import schemapage_rightnavmodel = require( './rightnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the schema page right tabs schemapage.
 */
export class RightNavController {

    /**
     * Constructs a new schema page right schemapage controller.
     * @param rightTabSelection the right tab selection maintained by this controller.
     */
    constructor( rightTabSelection : schemapage_rightnavmodel.RightTabSelection ) {
        this._rightTabSelection = rightTabSelection;
    }

    /**
     * Responds to clicking the "Diagrams" tab.
     * @param event
     */
    public onDiagramsTabClicked( event : any ) : void {
        this._rightTabSelection.rightTabSelection = schemapage_rightnavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
    }

    /**
     * Responds to clicking the "Documentation" tab.
     * @param event
     */
    public onDocumentationTabClicked( event : any ) : void {
        this._rightTabSelection.rightTabSelection = schemapage_rightnavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
    }

    /**
     * Responds to clicking the "Properties" tab.
     * @param event
     */
    public onPropertiesTabClicked( event : any ) : void {
        this._rightTabSelection.rightTabSelection = schemapage_rightnavmodel.ESchemaPageRightTabSelection.PROPERTIES;
    }

    /** The right tab selection model under control. */
    private _rightTabSelection : schemapage_rightnavmodel.RightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
