//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/module
 */

import topnavcontroller = require( './topnavcontroller' )
import topnavmodel = require( './topnavmodel' )
import topnavviewmodel = require( './topnavviewmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The one and only page selections instance available asynchronously. */
var thePageSelection : topnavmodel.PageSelection = null;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** The singleton page visibilities viewmodel instance. */
var thePageVisibilities : topnavviewmodel.PageVisibilities;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export var consoleModule = {

    provideTopNavController: function provideTopNavController( topNavModelPageSelection : topnavmodel.PageSelection ) : topnavcontroller.TopNavController {
        return new topnavcontroller.TopNavController( topNavModelPageSelection );
    },

    /**
     * Creates or returns the one and only page selection, loading it when first requested.
     * @returns {PageSelection} the page selection.
     */
    provideTopNavModelPageSelection: function provideTopNavModelPageSelection() : topnavmodel.PageSelection {

        // Create the page selection first time through.
        if ( thePageSelection == null ) {
            thePageSelection = new topnavmodel.PageSelection();
        }

        return thePageSelection;

    },

    /**
     * Provides the page visibilities viewmodel for given model.
     * @returns the viewmodel instance.
     */
    provideTopNavViewModelPageVisibilities: function provideTopNavViewModelPageVisibilities( topNavModelPageSelection : topnavmodel.PageSelection ) : topnavviewmodel.PageVisibilities {

        // Create the singleton on the first time through.
        if ( thePageVisibilities == null ) {
            return new topnavviewmodel.PageVisibilities( topNavModelPageSelection );
        }

        return thePageVisibilities;

    }

};

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

