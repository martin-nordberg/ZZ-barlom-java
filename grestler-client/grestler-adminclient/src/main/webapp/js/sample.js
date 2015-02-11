
require( [ 'ractive', 'text!templates/helloworld.html.mustache' ],
         function( Ractive, helloWorldTemplate ) {

    var model = {
        name: "world"
    };

    var ractive = new Ractive({

        el: 'container',

        template: helloWorldTemplate,

        data: model,

        magic: true

    });

    model.name = "cruel world";

});