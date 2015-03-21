require(
    [
        'scripts/js-gen/org/grestler/application/model/repository',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/navigation.html.mustache',
        'css!styles/css-gen/org/grestler/presentation/adminclient/navigation.css'
    ],
    function ( application_model_repository, Ractive, $, navigationTemplate ) {

        // Modelview -- TODO probably will eventually have some state to it
        var modelview = {};

        // View
        var view = new Ractive(
            {

                data: modelview,

                el: 'navigation-id',

                magic: true,

                template: navigationTemplate

            }
        );

        // Behavior
        view.on(
            'serverClicked', function ( event ) {
                alert( 'TODO: Server Clicked!' );
            }
        );

        view.on(
            'schemaClicked', function ( event ) {

                // TODO: experimentation
                var pkgs = application_model_repository.metamodelRepository.findAllPackages();
                pkgs.forEach( function( pkg ) {
                    console.log( pkg );
                });


                alert( 'TODO: Schema Clicked!' );
            }
        );

        view.on(
            'queriesClicked', function ( event ) {
                alert( 'TODO: Queries Clicked!' );
            }
        );

        view.on(
            'searchClicked', function ( event ) {
                alert( 'TODO: Search Clicked!' );
            }
        );

        // Initialization
        $( document ).ready(
            // TODO
        );

    }
);


