//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/navigation/schemapageviewmodel
 */

import schemapagenavmodel = require( '../navigation/schemapagenavmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Schema page component visibilities as a number of boolean attributes.
 */
class SchemaPageVisibilities {

    /**
     * Constructs a new schema page visibilities object.
     */
    constructor( schemaPageSelections : schemapagenavmodel.ISchemaPageSelections ) {
        var me = this;

        this._schemaPageSelections = schemaPageSelections;

        var setActiveLeftTab = function ( leftTabSelection : schemapagenavmodel.ESchemaPageLeftTabSelection ) : void {
            me.isBookmarksTabActive = leftTabSelection == schemapagenavmodel.ESchemaPageLeftTabSelection.BOOKMARKS;
            me.isBrowseTabActive = leftTabSelection == schemapagenavmodel.ESchemaPageLeftTabSelection.BROWSE;
            me.isRecentTabActive = leftTabSelection == schemapagenavmodel.ESchemaPageLeftTabSelection.RECENT;
            me.isSearchTabActive = leftTabSelection == schemapagenavmodel.ESchemaPageLeftTabSelection.SEARCH;
        };

        var setActiveRightTab = function ( rightTabSelection : schemapagenavmodel.ESchemaPageRightTabSelection ) : void {
            me.isDiagramsTabActive = rightTabSelection == schemapagenavmodel.ESchemaPageRightTabSelection.DIAGRAMS;
            me.isDocumentationTabActive = rightTabSelection == schemapagenavmodel.ESchemaPageRightTabSelection.DOCUMENTATION;
            me.isPropertiesTabActive = rightTabSelection == schemapagenavmodel.ESchemaPageRightTabSelection.PROPERTIES;
        };

        setActiveLeftTab( schemaPageSelections.leftTabSelection );
        setActiveRightTab( schemaPageSelections.rightTabSelection );

        Object['observe'](
            this._schemaPageSelections,
            function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        if ( change.name == 'leftTabSelection' ) {
                            setActiveLeftTab( change.newValue );
                        }
                        else if ( change.name == 'rightTabSelection' ) {
                            setActiveRightTab( change.newValue );
                        }
                    }
                )
            }, ['change.leftTabSelection','change.rightTabSelection']
        );
    }

    /**
     * @returns the model behind this viewmodel.
     */
    public get schemaPageSelections() : schemapagenavmodel.ISchemaPageSelections {
        return this._schemaPageSelections;
    }

    /** Whether the Browse page is active. */
    public isBrowseTabActive = false;

    /** Whether the Bookmarks page is active. */
    public isBookmarksTabActive = false;

    /** Whether the Diagrams page is active. */
    public isDiagramsTabActive = false;

    /** Whether the Documentation page is active. */
    public isDocumentationTabActive = false;

    /** Whether the Properties page is active. */
    public isPropertiesTabActive = false;

    /** Whether the Recent page is active. */
    public isRecentTabActive = false;

    /** Whether the Search page is active. */
    public isSearchTabActive = false;

    /** The schema page selections model that feeds into this view model. */
    private _schemaPageSelections : schemapagenavmodel.ISchemaPageSelections;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The singleton page visibilities viewmodel instance. */
var theSchemaPageVisibilities : Promise<SchemaPageVisibilities>;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Loads the page visibilities viewmodel asynchronously.
 * @returns a promise for the viewmodel instance.
 */
export function loadPageVisibilities() : Promise<SchemaPageVisibilities> {

    // Create the singleton on the first time through.
    if ( theSchemaPageVisibilities == null ) {
        theSchemaPageVisibilities = schemapagenavmodel.loadSchemaPageSelections().then(
            function ( schemaPageSelections : schemapagenavmodel.ISchemaPageSelections ) : SchemaPageVisibilities {
                return new SchemaPageVisibilities( schemaPageSelections );
            }
        );
    }

    return theSchemaPageVisibilities;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

