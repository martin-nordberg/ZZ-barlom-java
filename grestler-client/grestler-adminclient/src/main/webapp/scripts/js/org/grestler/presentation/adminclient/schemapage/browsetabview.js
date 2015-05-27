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
        'text!templates/org/grestler/presentation/adminclient/schemapage/browsetab.html.mustache',
        'exports',

        'css!styles/css-gen/org/grestler/presentation/adminclient/schemapage/browsetab.css'
    ],
    function (
        elementlinkview,
        ractiveinjection,
        browseTabTemplate,
        exports
    ) {

        // Define the Ractive view
        exports.BrowseTabView = ractiveinjection.InjectedRactive.extend(
            {

                components: {
                    "grestler-element-link": elementlinkview.ElementLinkView
                },

                controllerNames: [
                    'schemaPageBrowseTabController'
                ],

                template: browseTabTemplate,

                viewModelNames: {
                    browsedElementHolder: 'schemaPageBrowsedElementHolder',
                    browseTabEntries: 'schemaPageBrowseTabEntries'
                },

                onconstruct: function ( options ) {

                    // Do the default thing.
                    this._super( options );

                    // Extra constant data
                    options.data.addIconText = "<i class=\"fa fa-plus-square\"></i>";
                    options.data.openIconText = "<i class=\"fa fa-folder-open\"></i>";
                }

            }
        );

    }
);


