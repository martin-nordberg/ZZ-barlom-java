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
     * Responds to clicking the "Bookmarks" tab.
     * @param event
     */
    public onBookmarksTabClicked( event : any ) : void {
        this._schemaPageSelections.leftTabSelection = schemapagenavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
    }

    /**
     * Responds to clicking the "Browse" tab.
     * @param event
     */
    public onBrowseTabClicked( event : any ) : void {
        this._schemaPageSelections.leftTabSelection = schemapagenavmodel.ESchemaPageLeftTabSelection.BROWSE;
    }

    /**
     * Responds to clicking the "Diagrams" tab.
     * @param event
     */
    public onDiagramsTabClicked( event : any ) : void {
        this._schemaPageSelections.rightTabSelection = schemapagenavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
    }

    /**
     * Responds to clicking the "Documentation" tab.
     * @param event
     */
    public onDocumentationTabClicked( event : any ) : void {
        this._schemaPageSelections.rightTabSelection = schemapagenavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
    }

    /**
     * Responds to clicking the "Properties" tab.
     * @param event
     */
    public onPropertiesTabClicked( event : any ) : void {
        this._schemaPageSelections.rightTabSelection = schemapagenavmodel.ESchemaPageRightTabSelection.PROPERTIES;
    }

    /**
     * Responds to clicking the "Recent" tab.
     * @param event
     */
    public onRecentTabClicked( event : any ) : void {
        this._schemaPageSelections.leftTabSelection = schemapagenavmodel.ESchemaPageLeftTabSelection.RECENT;
    }

    /**
     * Responds to clicking the "Search" tab.
     * @param event
     */
    public onSearchTabClicked( event : any ) : void {
        this._schemaPageSelections.leftTabSelection = schemapagenavmodel.ESchemaPageLeftTabSelection.SEARCH;
    }

    /** The page selections model under control. */
    private _schemaPageSelections : schemapagenavmodel.ISchemaPageSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
