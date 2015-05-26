//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/schemapageview
 *
 * View class for the Schema page component.
 */

define(
    [
        'scripts/js/org/grestler/presentation/adminclient/schemapage/browsetabview',
        'scripts/js/org/grestler/presentation/adminclient/schemapage/propertiestabview',
        'scripts/js/org/grestler/presentation/utilities/ractiveinjection',
        'text!templates/org/grestler/presentation/adminclient/schemapage/schemapage.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/schemapage.css'
    ],
    function (
        browsetabview,
        propertiestabview,
        ractiveinjection,
        schemaPageTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.SchemaPageView = ractiveinjection.InjectedRactive.extend(
            {

                components: {
                    'grestler-browse-tab': browsetabview.BrowseTabView,
                    'grestler-properties-tab': propertiestabview.PropertiesTabView
                },

                controllerNames: [
                    'schemaPageLeftNavController',
                    'schemaPageRightNavController'
                ],

                template: schemaPageTemplate,

                viewModelNames: {
                    leftTabVisibilities: 'schemaPageLeftTabVisibilities',
                    rightTabVisibilities: 'schemaPageRightTabVisibilities'
                }

            }
        );

    }
);


