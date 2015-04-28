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
        'scripts/js/org/grestler/presentation/utilities/ractiveinjection',
        'text!templates/org/grestler/presentation/adminclient/schemapage/propertiestab.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/propertiestab.css'
    ],
    function (
        elementlinkview,
        ractiveinjection,
        propertiesTabTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.PropertiesTabView = ractiveinjection.InjectedRactive.extend(
            {

                components: {
                },

                controllerNames: [
                ],

                template: propertiesTabTemplate,

                viewModelNames: {
                    propertiesTabFields: 'schemaPagePropertiesTabFields'
                }

            }
        );

    }
);


