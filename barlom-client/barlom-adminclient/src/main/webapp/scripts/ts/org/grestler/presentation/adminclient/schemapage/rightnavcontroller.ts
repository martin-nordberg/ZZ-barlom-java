//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/schemapagenavcontroller
 */

import rightnavmodel = require( './rightnavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the schema page right tabs schemapage.
 */
export class RightNavController {

    /**
     * Constructs a new schema page right schemapage controller.
     * @param rightTabSelection the right tab selection maintained by this controller.
     */
    constructor( rightTabSelection : rightnavmodel.RightTabSelection ) {
        this._rightTabSelection = rightTabSelection;
    }

    /**
     * Responds to clicking the "Diagrams" tab.
     * @param event
     */
    public onDiagramsTabClicked( event : any ) : void {
        this._rightTabSelection.rightTabSelection = rightnavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
    }

    /**
     * Responds to clicking the "Documentation" tab.
     * @param event
     */
    public onDocumentationTabClicked( event : any ) : void {
        this._rightTabSelection.rightTabSelection = rightnavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
    }

    /** The right tab selection model under control. */
    private _rightTabSelection : rightnavmodel.RightTabSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
