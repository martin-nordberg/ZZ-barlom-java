require(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/schemapagenavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/schemapagenavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/navigation/schemapagenav.html.mustache',
        'css!styles/css-gen/org/grestler/presentation/adminclient/navigation/schemapagenav.css'
    ],
    function ( schemapagenavviewmodel, schemapagenavcontroller, Ractive, $, schemaPageNavTemplate ) {

        /**
         * Defines the schema page navigation view after the page visibilities view model is ready.
         * @param schemaPageVisibilities the viewmodel for page visibilities.
         */
        var defineSchemaPageNavView = function ( schemaPageVisibilities ) {

            // Define the view.
            var view = new Ractive(
                {
                    data: schemaPageVisibilities,
                    el: 'schema-page-id',
                    magic: true,
                    template: schemaPageNavTemplate
                }
            );

            // Define the behavior (event handlers).
            var controller = new schemapagenavcontroller.SchemaPageNavController( schemaPageVisibilities.schemaPageSelections );

            view.on(
                'bookmarksTabClicked', function ( event ) {
                    controller.onBookmarksTabClicked( event );
                    return false;
                }
            );

            view.on(
                'browseTabClicked', function ( event ) {
                    controller.onBrowseTabClicked( event );
                    return false;
                }
            );

            view.on(
                'diagramsTabClicked', function ( event ) {
                    controller.onDiagramsTabClicked( event );
                    return false;
                }
            );

            view.on(
                'documentationTabClicked', function ( event ) {
                    controller.onDocumentationTabClicked( event );
                    return false;
                }
            );

            view.on(
                'propertiesTabClicked', function ( event ) {
                    controller.onPropertiesTabClicked( event );
                    return false;
                }
            );

            view.on(
                'recentTabClicked', function ( event ) {
                    controller.onRecentTabClicked( event );
                    return false;
                }
            );

            view.on(
                'searchTabClicked', function ( event ) {
                    controller.onSearchTabClicked( event );
                    return false;
                }
            );

            // Initialize the view.
            // TODO, if needed

        };

        // Load the viewmodel (page visibilities) then initialize the view.
        schemapagenavviewmodel.loadPageVisibilities().then( defineSchemaPageNavView );

    }
);


