//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/main/console
 */

require(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/main/topnavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/main/console.html.mustache',
        'css!styles/css-gen/org/grestler/presentation/adminclient/main/console.css'
    ],
    function ( topnavviewmodel, topnavcontroller, Ractive, $, consoleTemplate ) {

        /**
         * Defines the main view after the page visibilities view model is ready.
         * @param pageVisibilities the viewmodel for page visibilities.
         */
        var defineAdminClientMainView = function ( pageVisibilities ) {

            // Define the view.
            var view = new Ractive(
                {
                    data: pageVisibilities,
                    el: 'console-id',
                    magic: true,
                    template: consoleTemplate
                }
            );

            // Define the behavior (event handlers).
            var controller = new topnavcontroller.TopNavController( pageVisibilities.pageSelection );

            view.on(
                'queriesClicked', function ( event ) {
                    controller.onQueriesClicked( event );
                    return false;
                }
            );
            view.on(
                'schemaClicked', function ( event ) {
                    controller.onSchemaClicked( event );
                    return false;
                }
            );
            view.on(
                'searchClicked', function ( event ) {
                    controller.onSearchClicked( event );
                    return false;
                }
            );
            view.on(
                'serverClicked', function ( event ) {
                    controller.onServerClicked( event );
                    return false;
                }
            );

            // Initialize the view contents.
            require( [ 'scripts/js/org/grestler/presentation/adminclient/navigation/schemapagenavview' ] );

        };

        // Load the viewmodel (page visibilities) then initialize the view.
        topnavviewmodel.loadPageVisibilities().then( defineAdminClientMainView );

    }
);


