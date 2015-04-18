//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/metamodel/elementlinkview
 *
 * View class for clickable element link.
 */

define(
    [
        'scripts/js-gen/org/grestler/presentation/metamodel/elementviewmodel',
        'dependencies',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/metamodel/elementlink.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/metamodel/elementlink.css'
    ],
    function (
        elementviewmodel,
        dependencies,
        Ractive,
        $,
        elementLinkTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.ElementLinkView = Ractive.extend(
            {
                data: {
                    elementHandle: '<constructed>',
                    id: '<parameter>',
                    targetElement: '<constructed>',
                    targetElementName: '<parameter>'
                },
                isolated: true,
                magic: true,
                template: elementLinkTemplate,

                onconstruct: function( options ) {

                    console.log( options );

                    // TODO: establish a handle to the given element ID
                    // - get the metamodel repository from the context
                    // - look up the element by ID
                    // - create the element handle from the element

                    var metamodelRepository = dependencies.context.get( 'metamodelRepository' );

                    // TODO: find arbitrary element, not package in particular
                    var pkg = metamodelRepository.findPackageById( options.data.id );

                    options.data.elementHandle = new elementviewmodel.ElementHandle( pkg );

                    // TODO: make it work for different target element holders
                    options.data.targetElement = dependencies.context.get( options.data.targetElementName );

                },

                oninit: function () {

                    // Define the behavior (event handlers).

                    // TODO ...

                    this.get( 'elementHandle' ).observeModelChanges();

                },

                onteardown: function() {

                    this.get( 'elementHandle' ).unobserveModelChanges();

                }
            }
        );

    }
);


