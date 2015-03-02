require(['scripts/ts/example', 'ractive', 'jquery', 'text!templates/helloworld.html.mustache'],
    function (example, Ractive, $, helloWorldTemplate) {

        // Model (external TypeScript)
        var model = new example.Example();

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
        model.modifier = "wide viewmodel";
        model.name = "globe";

        // Make sure jQuery works
        $('h1.grestler').html("Grestler!");

    }
);

