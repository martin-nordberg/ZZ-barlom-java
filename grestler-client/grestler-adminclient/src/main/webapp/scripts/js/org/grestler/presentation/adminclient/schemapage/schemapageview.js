define(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/leftnavcontroller',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/schemapage/rightnavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/schemapage/schemapage.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/schemapagenav.css'
    ],
    function (
        leftnavviewmodel,
        leftnavcontroller,
        rightnavviewmodel,
        rightnavcontroller,
        Ractive,
        $,
        schemaPageTemplate,
        exports
    ) {

        // Load the tab visibilities for the schema page.
        var leftTabVisibilities = leftnavviewmodel.loadLeftTabVisibilities();
        var rightTabVisibilities = rightnavviewmodel.loadRightTabVisibilities();

        exports.SchemaPageView = Ractive.extend(
            {

                data: {
                    leftTabVisibilities: leftTabVisibilities,
                    rightTabVisibilities: rightTabVisibilities
                },
                isolated: true,
                magic: true,
                template: schemaPageTemplate,

                oninit: function () {

                    // Define the behavior (event handlers).
                    var leftTabsController = new leftnavcontroller.LeftNavController( leftTabVisibilities.leftTabSelection );
                    var rightTabsController = new rightnavcontroller.RightNavController( rightTabVisibilities.rightTabSelection );

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
                    this.data.leftTabVisibilities.observeModelChanges();
                    this.data.rightTabVisibilities.observeModelChanges();

                }
            }
        );

    }
);


