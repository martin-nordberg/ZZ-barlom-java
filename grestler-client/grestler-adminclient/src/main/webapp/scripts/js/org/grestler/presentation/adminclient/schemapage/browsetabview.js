//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/browsetabview
 *
 * View class for the Schema page Browse tab component.
 */

define(
    [
        'scripts/js/org/grestler/presentation/metamodel/elementlinkview',
        'dependencies',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/schemapage/browsetab.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/browsetab.css'
    ],
    function (
        elementlinkview,
        dependencies,
        Ractive,
        $,
        browseTabTemplate,
        exports
    ) {

        // Load the focused element of the schema page.
        var browsedElementHolder = dependencies.context.get( 'schemaPageBrowsedElementHolder' );

        // Define the Ractive view
        exports.BrowseTabView = Ractive.extend(
            {
                components: {
                    "grestler-element-link": elementlinkview.ElementLinkView
                },
                data: {
                    browsedElementHolder: browsedElementHolder
                },
                isolated: true,
                magic: true,
                template: browseTabTemplate,

                oninit: function () {

                    // Define the behavior (event handlers).

                    // TODO ...

                    browsedElementHolder.observeSelectionChanges();

                },

                onteardown: function() {

                    browsedElementHolder.unobserveSelectionChanges();

                }
            }
        );

    }
);


