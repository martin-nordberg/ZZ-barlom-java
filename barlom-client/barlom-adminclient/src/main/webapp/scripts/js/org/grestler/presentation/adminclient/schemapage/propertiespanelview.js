//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/propertiespanelview
 *
 * View class for the Schema page Browse tab properties panel component.
 */

define(
    [
        'scripts/js/org/grestler/presentation/metamodel/elementlinkview',
        'scripts/js/org/grestler/presentation/utilities/ractiveinjection',
        'text!templates/org/grestler/presentation/adminclient/schemapage/propertiespanel.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/propertiespanel.css'
    ],
    function (
        elementlinkview,
        ractiveinjection,
        propertiesPanelTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.PropertiesPanelView = ractiveinjection.InjectedRactive.extend(
            {

                components: {},

                controllerNames: [
                    'schemaPagePropertiesPanelController'
                ],

                template: propertiesPanelTemplate,

                viewModelNames: {
                    propertiesPanelFields: 'schemaPagePropertiesPanelFields'
                }

            }
        );

    }
);


