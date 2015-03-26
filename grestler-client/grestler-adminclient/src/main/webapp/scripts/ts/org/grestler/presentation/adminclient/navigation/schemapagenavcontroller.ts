//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/schemapagenavcontroller
 */

import schemapagenavmodel = require( '../navigation/schemapagenavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for the schema page navigation.
 */
export class SchemaPageNavController {

    /**
     * Constructs a new schema page navigation controller.
     * @param schemaPageSelections the page selections controlled by the schema page tabs.
     */
    constructor( schemaPageSelections : schemapagenavmodel.ISchemaPageSelections ) {
        this._schemaPageSelections = schemaPageSelections;
    }

    /**
     * Responds to clicking the "Browse" tab.
     * @param event
     */
    public onBrowseTabClicked( event : any ) : void {
        this._schemaPageSelections.leftTabSelection = schemapagenavmodel.ESchemaPageLeftTabSelection.BROWSE;
    }

    /** The page selections model under control. */
    private _schemaPageSelections : schemapagenavmodel.ISchemaPageSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
