
require( [ 'scripts/example', 'ractive', 'text!templates/helloworld.html.mustache' ],
         function( example, Ractive, helloWorldTemplate ) {

    var model = new example.Example();

    var view = new Ractive({

        computed : {
            fullName: '${modifier} + " " + ${name}'
        },

        data: model,

        el: 'container',

        magic: true,

        template: helloWorldTemplate

    });

    model.modifier = "big";

});
