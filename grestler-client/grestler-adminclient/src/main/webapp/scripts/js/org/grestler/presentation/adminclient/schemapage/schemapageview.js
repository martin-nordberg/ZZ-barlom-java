//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/schemapageview
 *
 * View class for the Schema page component.
 */

define(
    [
        'scripts/js/org/grestler/presentation/adminclient/schemapage/browsetabview',
        'dependencies',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/schemapage/schemapage.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/schemapage.css'
    ],
    function (
        browsetabview,
        dependencies,
        Ractive,
        $,
        schemaPageTemplate,
        exports
    ) {

        // Load the tab visibilities for the schema page.
        var leftTabVisibilities = dependencies.context.get( 'schemaPageLeftTabVisibilities' );
        var rightTabVisibilities = dependencies.context.get( 'schemaPageRightTabVisibilities' );

        // Define the Ractive view
        exports.SchemaPageView = Ractive.extend(
            {
                components: {
                    "grestler-browse-tab": browsetabview.BrowseTabView
                },
                data: {
                    leftTabVisibilities: leftTabVisibilities,
                    rightTabVisibilities: rightTabVisibilities
                },
                isolated: true,
                magic: true,
                template: schemaPageTemplate,

                oninit: function () {

                    // Define the behavior (event handlers).
                    var leftTabsController = dependencies.context.get( 'schemaPageLeftNavController' );
                    var rightTabsController = dependencies.context.get( 'schemaPageRightNavController' );

                    this.on(
                        'bookmarksTabClicked', function ( event ) {
                            leftTabsController.onBookmarksTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'browseTabClicked', function ( event ) {
                            leftTabsController.onBrowseTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'diagramsTabClicked', function ( event ) {
                            rightTabsController.onDiagramsTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'documentationTabClicked', function ( event ) {
                            rightTabsController.onDocumentationTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'propertiesTabClicked', function ( event ) {
                            rightTabsController.onPropertiesTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'recentTabClicked', function ( event ) {
                            leftTabsController.onRecentTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'searchTabClicked', function ( event ) {
                            leftTabsController.onSearchTabClicked( event );
                            return false;
                        }
                    );

                    // Turn on the wiring between model and viewmodel.
                    leftTabVisibilities.observeModelChanges();
                    rightTabVisibilities.observeModelChanges();

                }

                // TODO: onteardown

            }
        );

    }
);


