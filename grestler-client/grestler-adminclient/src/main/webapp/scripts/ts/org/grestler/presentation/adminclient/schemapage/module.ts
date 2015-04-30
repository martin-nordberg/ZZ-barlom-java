//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/module
 */

import api_commands = require( '../../../domain/metamodel/api/commands' )
import api_queries = require( '../../../domain/metamodel/api/queries' )
import browsetabviewmodel = require( './browsetabviewmodel' );
import browsetabcontroller = require( './browsetabcontroller' );
import elementmodel = require( '../../metamodel/elementmodel' );
import elementviewmodel = require( '../../metamodel/elementviewmodel' );
import leftnavcontroller = require( './leftnavcontroller' );
import leftnavmodel = require( './leftnavmodel' );
import leftnavviewmodel = require( './leftnavviewmodel' );
import propertiestabcontroller = require( './propertiestabcontroller' );
import propertiestabviewmodel = require( './propertiestabviewmodel' );
import rightnavcontroller = require( './rightnavcontroller' );
import rightnavmodel = require( './rightnavmodel' );
import rightnavviewmodel = require( './rightnavviewmodel' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export var schemaPageModule = {

    /**
     * Creates or returns the one and only schema page browsed element selection model, creating it when first requested.
     * @returns {ElementSelection} the browsed element selection.
     */
    provideSingletonSchemaPageBrowsedElement: function provideSingletonSchemaPageBrowsedElement(
        metamodelRepository : api_queries.IMetamodelRepository
    ) : elementmodel.ElementSelection {

        return new elementmodel.ElementSelection( metamodelRepository );

    },

    /**
     * Creates or returns the one and only schema page browsed element selection view model, creating it when first requested.
     * @returns {ElementSelection} the browsed element selection.
     */
    provideSingletonSchemaPageBrowsedElementHolder: function provideSingletonSchemaPageBrowsedElementHolder(
        schemaPageBrowsedElement : elementmodel.ElementSelection
    ) : elementviewmodel.ElementHolder {

        return new elementviewmodel.ElementHolder( schemaPageBrowsedElement );

    },

    /**
     * Provides a controller for the Browse tab.
     * @param schemaPageBrowsedElement the browsed element that is to be the parent package of newly created elements.
     * @param metamodelCommandFactory the factory for making commands.
     * @returns {BrowseTabController}
     */
    provideSchemaPageBrowseTabController: function provideSchemaPageBrowseTabController(
        schemaPageBrowsedElement : elementmodel.ElementSelection,
        metamodelCommandFactory : api_commands.IMetamodelCommandFactory
    ) : browsetabcontroller.BrowseTabController {

        return new browsetabcontroller.BrowseTabController( schemaPageBrowsedElement, metamodelCommandFactory );

    },

    /**
     * Provides the view model for the browse tab.
     * @param schemaPageBrowsedElement the browsed element that is to have its related elements shown.
     * @returns {BrowseTabEntries}
     */
    provideSchemaPageBrowseTabEntries: function provideSchemaPageBrowseTabEntries(
        schemaPageBrowsedElement : elementmodel.ElementSelection
    ) : browsetabviewmodel.BrowseTabEntries {

        return new browsetabviewmodel.BrowseTabEntries( schemaPageBrowsedElement );

    },

    /**
     * Provides a schema page left nav controller for given tab selection model.
     * @param schemaPageLeftTabSelection the tab selection model to control.
     * @returns {LeftNavController} the newly created controller.
     */
    provideSchemaPageLeftNavController: function provideSchemaPageLeftNavController(
        schemaPageLeftTabSelection : leftnavmodel.LeftTabSelection
    ) : leftnavcontroller.LeftNavController {

        return new leftnavcontroller.LeftNavController( schemaPageLeftTabSelection );

    },

    /**
     * Creates or returns the one and only schema page left tab selection model, loading it when first requested.
     * @returns {LeftTabSelection} the left tab selection.
     */
    provideSingletonSchemaPageLeftTabSelection: function provideSingletonSchemaPageLeftTabSelection() : leftnavmodel.LeftTabSelection {

        return new leftnavmodel.LeftTabSelection();

    },

    /**
     * Provides the singleton page visibilities viewmodel.
     * @returns the viewmodel instance.
     */
    provideSchemaPageLeftTabVisibilities: function provideSchemaPageLeftTabVisibilities(
        schemaPageLeftTabSelection : leftnavmodel.LeftTabSelection
    ) : leftnavviewmodel.LeftTabVisibilities {

        return new leftnavviewmodel.LeftTabVisibilities( schemaPageLeftTabSelection );

    },

    /**
     * Provides the controller for the properties tab.
     * @param schemaPageBrowsedElement the browsed element that is to have its properties changed.
     * @param metamodelCommandFactory the factory for making commands.
     * @returns {PropertiesTabFields}
     */
    provideSchemaPagePropertiesTabController: function provideSchemaPagePropertiesTabController(
        schemaPageBrowsedElement : elementmodel.ElementSelection,
        metamodelCommandFactory : api_commands.IMetamodelCommandFactory
    ) : propertiestabcontroller.PropertiesTabController {

        return new propertiestabcontroller.PropertiesTabController( schemaPageBrowsedElement, metamodelCommandFactory );

    },

    /**
     * Provides the view model for the properties tab.
     * @param schemaPageBrowsedElement the browsed element that is to have its properties shown.
     * @returns {PropertiesTabFields}
     */
    provideSchemaPagePropertiesTabFields: function provideSchemaPagePropertiesTabFields(
        schemaPageBrowsedElement : elementmodel.ElementSelection
    ) : propertiestabviewmodel.PropertiesTabFields {

        return new propertiestabviewmodel.PropertiesTabFields( schemaPageBrowsedElement );

    },

    /**
     * Provides a schema page right nav controller for given tab selection model.
     * @param schemaPageRightTabSelection the tab selection model to control.
     * @returns {RightNavController} the newly created controller.
     */
    provideSchemaPageRightNavController: function provideSchemaPageRightNavController(
        schemaPageRightTabSelection : rightnavmodel.RightTabSelection
    ) : rightnavcontroller.RightNavController {

        return new rightnavcontroller.RightNavController( schemaPageRightTabSelection );

    },

    /**
     * Creates or returns the one and only schema page right tab selection model, loading it when first requested.
     * @returns {RightTabSelection} the right tab selection.
     */
    provideSingletonSchemaPageRightTabSelection: function provideSingletonSchemaPageRightTabSelection() : rightnavmodel.RightTabSelection {

        return new rightnavmodel.RightTabSelection();

    },

    /**
     * Loads the page visibilities viewmodel asynchronously.
     * @returns a promise for the viewmodel instance.
     */
    provideSchemaPageRightTabVisibilities: function provideSchemaPageRightTabVisibilities(
        schemaPageRightTabSelection : rightnavmodel.RightTabSelection
    ) : rightnavviewmodel.RightTabVisibilities {

        return new rightnavviewmodel.RightTabVisibilities( schemaPageRightTabSelection );

    }

};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

