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
        'dependencies',
        'ractive',
        'jquery',
        'text!templates/org/grestler/presentation/adminclient/schemapage/browsetab.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/browsetab.css'
    ],
    function (
        dependencies,
        Ractive,
        $,
        browseTabTemplate,
        exports
    ) {

        // Load the focused element of the schema page.  -- TODO: not the whole metamodel
        var metamodelRepository = dependencies.context.get( 'metamodelRepository' );

        // Define the Ractive view
        exports.BrowseTabView = Ractive.extend(
            {
                data: {
                    metamodelRepository: metamodelRepository
                },
                isolated: true,
                magic: true,
                template: browseTabTemplate,

                oninit: function () {

                    // Define the behavior (event handlers).

                    // TODO ...

                }
            }
        );

    }
);


