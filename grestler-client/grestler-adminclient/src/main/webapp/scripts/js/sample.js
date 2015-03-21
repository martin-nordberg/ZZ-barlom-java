require(
    [
        'scripts/js-gen/example',
        'scripts/js-gen/org/grestler/presentation/main',
        'ractive',
        'jquery',
        'bootstrap',
        'text!templates/helloworld.html.mustache'
    ],
    function ( example, presentation_main, Ractive, $, bootstrap, helloWorldTemplate ) {

        // View
        var view = new Ractive(
            {

                computed: {
                    fullName: '${modifier} + " " + ${name}'
                },

                data: example.theExampleVM,

                el: 'container',

                magic: true,

                template: helloWorldTemplate

            }
        );

        // Change the model
        example.theExample.modifier = "big wide";
        example.theExample.name = "globe";

        // Make sure jQuery works
        $( 'h1.grestler' ).html( "Grestler!" );

        // load the application metamodel and surrounding apparatus
        $( document ).ready(
            function () {
                presentation_main.initializeApplication();
            }
        );

    }
);

