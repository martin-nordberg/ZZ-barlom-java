require(['scripts/js-gen/example', 'ractive', 'jquery', 'text!templates/helloworld.html.mustache'],
    function (example, Ractive, $, helloWorldTemplate) {

        // Model (external TypeScript)
        var model = new example.Example();

        // Modelview
        var modelview = new example.ExampleVM( model );

        // View
        var view = new Ractive({

            computed: {
                fullName: '${modifier} + " " + ${name}'
            },

            data: modelview,

            el: 'container',

            magic: true,

            template: helloWorldTemplate

        });

        // Change the model
        model.modifier = "big wide";
        model.name = "globe";

        // Make sure jQuery works
        $('h1.grestler').html("Grestler!");

    }
);

