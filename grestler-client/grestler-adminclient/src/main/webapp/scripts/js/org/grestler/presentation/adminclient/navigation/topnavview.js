require(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/topnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/topnavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/navigation/topnav.html.mustache',
        'css!styles/css-gen/org/grestler/presentation/adminclient/navigation/topnav.css'
    ],
    function ( topnavviewmodel, topnavcontroller, Ractive, $, navigationTemplate ) {

        /**
         * Defines the top navigation view after the panel visibilities view model is ready.
         * @param panelVisibilities the viewmodel for panel visibilities.
         */
        var defineTopNavigationView = function ( panelVisibilities ) {

            // Define the view.
            var view = new Ractive(
                {
                    data: panelVisibilities,
                    el: 'navigation-id',
                    magic: true,
                    template: navigationTemplate
                }
            );

            // Define the behavior (event handlers).
            var controller = new topnavcontroller.TopNavController( panelVisibilities.panelSelections );

            view.on(
                'queriesClicked', function ( event ) {
                    controller.onQueriesClicked( event );
                }
            );
            view.on(
                'schemaClicked', function ( event ) {
                    controller.onSchemaClicked( event );
                }
            );
            view.on(
                'searchClicked', function ( event ) {
                    controller.onSearchClicked( event );
                }
            );
            view.on(
                'serverClicked', function ( event ) {
                    controller.onServerClicked( event );
                }
            );

            // Initialize the view.
            // TODO, if needed

        };

        // Load the viewmodel (panel visibilities) then initialize the view.
        topnavviewmodel.loadPanelVisibilities().then( defineTopNavigationView );

    }
);


