//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/browsebreadcrumbsview
 *
 * View class for the Schema page browsed element breadcrumbs component.
 */

define(
    [
        'scripts/js/org/grestler/presentation/metamodel/elementlinkview',
        'scripts/js/org/grestler/presentation/utilities/ractiveinjection',
        'text!templates/org/grestler/presentation/adminclient/schemapage/browsebreadcrumbs.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/browsebreadcrumbs.css'
    ],
    function (
        elementlinkview,
        ractiveinjection,
        browseTabTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.BrowseBreadCrumbsView = ractiveinjection.InjectedRactive.extend(
            {

                components: {
                    "grestler-element-link": elementlinkview.ElementLinkView
                },

                controllerNames: [],

                template: browseTabTemplate,

                viewModelNames: {
                    browsedElementHolder: 'schemaPageBrowsedElementHolder'
                }

            }
        );

    }
);


