//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/console/consoleview
 *
 * Main view class for the whole Grestler Admin Console.
 */

require(
    [
        'scripts/js/org/grestler/presentation/adminclient/schemapage/schemapageview',
        'dependencies',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/console/console.html.mustache',

        'css!styles/css-gen/org/grestler/presentation/adminclient/console/console.css'
    ],
    function ( schemapageview, dependencies, Ractive, $, consoleTemplate ) {

        // Load the viewmodel (page visibilities).
        var pageVisibilities = dependencies.context.get( 'topNavViewModelPageVisibilities' );

        // Define the view.
        var view = new Ractive(
            {

                components: {
                    "grestler-schema-page": schemapageview.SchemaPageView
                },

                data: pageVisibilities,

                el: 'console-id',

                magic: true,

                template: consoleTemplate,

                oninit: function() {

                    // Define the behavior (event handlers).
                    var controller = dependencies.context.get( 'topNavController' );

                    this.on(
                        'queriesClicked', function ( event ) {
                            controller.onQueriesClicked( event );
                            return false;
                        }
                    );
                    this.on(
                        'schemaClicked', function ( event ) {
                            controller.onSchemaClicked( event );
                            return false;
                        }
                    );
                    this.on(
                        'searchClicked', function ( event ) {
                            controller.onSearchClicked( event );
                            return false;
                        }
                    );
                    this.on(
                        'serverClicked', function ( event ) {
                            controller.onServerClicked( event );
                            return false;
                        }
                    );

                    /** Wire the viewmodel to the model. */
                    pageVisibilities.observeModelChanges();

                },

                onteardown: function() {

                    // Turn off the wiring between model and viewmodel.
                    pageVisibilities.unobserveModelChanges();

                }

            }
        );


    }
);


