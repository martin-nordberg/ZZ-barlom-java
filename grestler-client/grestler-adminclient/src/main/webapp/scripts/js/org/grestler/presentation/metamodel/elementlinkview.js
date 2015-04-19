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
        'text!templates/org/grestler/presentation/metamodel/elementlink.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/metamodel/elementlink.css'
    ],
    function (
        elementviewmodel,
        dependencies,
        Ractive,
        elementLinkTemplate,
        exports
    ) {

        // Define the Ractive view.
        exports.ElementLinkView = Ractive.extend(
            {
                data: {
                    element: '<parameter>',
                    elementHandle: '<constructed>',
                    targetElement: '<constructed>',
                    targetElementName: '<parameter>'
                },
                isolated: true,
                magic: true,
                template: elementLinkTemplate,

                onconstruct: function( options ) {

                    console.log( options );

                    // Set the element linked to.
                    options.data.elementHandle = new elementviewmodel.ElementHandle( options.data.element );

                    // Set the element model to be changed when the link is clicked.
                    options.data.targetElement = dependencies.context.get( options.data.targetElementName );

                },

                oninit: function () {

                    // Define the behavior (event handlers).

                    this.on(
                        'linkClicked', function ( event ) {
                            this.get( 'targetElement' ).elementSelection = this.get( 'element' );
                            return false;
                        }
                    );


                    this.get( 'elementHandle' ).observeModelChanges();

                },

                onteardown: function() {

                    this.get( 'elementHandle' ).unobserveModelChanges();

                }
            }
        );

    }
);


