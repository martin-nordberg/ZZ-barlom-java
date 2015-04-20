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
        'scripts/js-gen/org/grestler/presentation/metamodel/elementcontroller',
        'scripts/js-gen/org/grestler/presentation/metamodel/elementviewmodel',
        'dependencies',
        'scripts/js/org/grestler/presentation/utilities/ractiveinjection',
        'text!templates/org/grestler/presentation/metamodel/elementlink.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/metamodel/elementlink.css'
    ],
    function (
        elementcontroller,
        elementviewmodel,
        dependencies,
        ractiveinjection,
        elementLinkTemplate,
        exports
    ) {

        // Define the Ractive view.
        exports.ElementLinkView = ractiveinjection.InjectedRactive.extend(
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

                    // Do the default thing.
                    this._super( options );

                    // Set the element linked to.
                    options.data.elementHandle = new elementviewmodel.ElementHandle( options.data.element );

                    // Set the element model to be changed when the link is clicked.
                    options.data.targetElement = dependencies.context.get( options.data.targetElementName );

                    // Create a custom controller.
                    this.controllers = [
                        new elementcontroller.ElementController( options.data.elementHandle )
                    ];

                    // Make note of our custom view models for initialization and tear down.
                    // TODO: would be nice to use something like get('.') and then enumerate the view models w/o knowing their names separately
                    this.viewModelNames.elementHandle = 'custom';
                    this.viewModelNames.targetElement = 'custom';

                }
            }
        );

    }
);


