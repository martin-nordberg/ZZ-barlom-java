define(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/schemapagenavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/schemapagenavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/navigation/schemapagenav.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/navigation/schemapagenav.css'
    ],
    function ( schemapagenavviewmodel, schemapagenavcontroller, Ractive, $, schemaPageNavTemplate, exports ) {

        // Load the page visibilities for the schema page.
        var schemaPageVisibilities = schemapagenavviewmodel.loadPageVisibilities();

        var SchemaPageView = Ractive.extend(
            {

                data: schemaPageVisibilities,
                isolated: true,
                magic: true,
                template: schemaPageNavTemplate,

                oninit: function() {

                    // Define the behavior (event handlers).
                    var controller = new schemapagenavcontroller.SchemaPageNavController( schemaPageVisibilities.schemaPageSelections );

                    this.on(
                        'bookmarksTabClicked', function ( event ) {
                            controller.onBookmarksTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'browseTabClicked', function ( event ) {
                            controller.onBrowseTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'diagramsTabClicked', function ( event ) {
                            controller.onDiagramsTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'documentationTabClicked', function ( event ) {
                            controller.onDocumentationTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'propertiesTabClicked', function ( event ) {
                            controller.onPropertiesTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'recentTabClicked', function ( event ) {
                            controller.onRecentTabClicked( event );
                            return false;
                        }
                    );

                    this.on(
                        'searchTabClicked', function ( event ) {
                            controller.onSearchTabClicked( event );
                            return false;
                        }
                    );

                    // Turn on the wiring between model and viewmodel.
                    this.data.observeModelChanges();

                }
            }
        );

        exports.SchemaPageView = SchemaPageView;
    }
);


