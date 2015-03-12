require(
    ['ractive', 'jquery', 'text!templates/org/grestler/presentation/adminclient/navigation.html.mustache'],
    function ( Ractive, $, navigationTemplate ) {

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
        view.on( 'editClicked', function ( event ) {
            alert( 'TODO: Edit Clicked!' );
        });

        view.on( 'reviewClicked', function ( event ) {
            alert( 'TODO: Review Clicked!' );
        });

        view.on( 'generateClicked', function ( event ) {
            alert( 'TODO: Generate Clicked!' );
        });

        view.on( 'searchClicked', function ( event ) {
            alert( 'TODO: Search Clicked!' );
        });

        $( document ).ready(
            function () {
                var menuToggle = $( '#js-mobile-menu' ).unbind();
                $( '#js-navigation-menu' ).removeClass( "show" );

                menuToggle.on(
                    'click', function ( e ) {
                        var menu = $( '#js-navigation-menu' );

                        e.preventDefault();
                        menu.slideToggle(
                            function () {
                                if ( menu.is( ':hidden' ) ) {
                                    menu.removeAttr( 'style' );
                                }
                            }
                        );
                    }
                );
            }
        );

    }
);


