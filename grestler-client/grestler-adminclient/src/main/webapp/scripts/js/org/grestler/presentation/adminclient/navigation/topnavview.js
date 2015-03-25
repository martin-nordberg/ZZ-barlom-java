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
        var defineTopNavigationView = function( panelVisibilities ) {

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
            view.on( 'queriesClicked', topnavcontroller.onQueriesClicked );
            view.on( 'schemaClicked', topnavcontroller.onSchemaClicked );
            view.on( 'searchClicked', topnavcontroller.onSearchClicked );
            view.on( 'serverClicked', topnavcontroller.onServerClicked );

            // Initialize the view.
            // TODO, if needed

        };

        // Load the viewmodel (panel visibilities) then initialize the view.
        topnavviewmodel.loadPanelVisibilities().then( defineTopNavigationView );

    }
);


