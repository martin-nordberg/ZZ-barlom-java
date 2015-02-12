
require( [ 'ractive', 'text!templates/helloworld.html.mustache' ],
         function( Ractive, helloWorldTemplate ) {

    var model = {
        modifier: "cruel",
        name: "world"
    };

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
