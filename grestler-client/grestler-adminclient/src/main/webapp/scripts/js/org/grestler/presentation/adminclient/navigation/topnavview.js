require(
    [
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/topnavviewmodel',
        'scripts/js-gen/org/grestler/presentation/adminclient/navigation/topnavcontroller',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/navigation/topnav.html.mustache',
        'css!styles/css-gen/org/grestler/presentation/adminclient/navigation/topnav.css'
    ],
    function ( topnavviewmodel, topnavcontroller, Ractive, $, topNavTemplate ) {

        /**
         * Defines the top navigation view after the page visibilities view model is ready.
         * @param pageVisibilities the viewmodel for page visibilities.
         */
        var defineTopNavView = function ( pageVisibilities ) {

            // Define the view.
            var view = new Ractive(
                {
                    data: pageVisibilities,
                    el: 'top-nav-id',
                    magic: true,
                    template: topNavTemplate
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
        topnavviewmodel.loadPageVisibilities().then( defineTopNavView );

    }
);


