//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/utilities/ractiveinjection
 *
 * Base class extending Ractive to automatically inject controller(s) and view model(s).
 *
 * An extending component may define the following:
 *   controllerNames - an array of strings giving the names used to create one or more controllers from the dependency
 *                     injection context.
 *   viewModelNames  - a map of strings giving the names for injected view model objects keyed by their data object
 *                     names.
 *
 * Alternatively or in addition the extending component can override onconstruct to define specialized view model
 * instances and oninit to define specialized controllers.
 */

define(
    [
        'dependencies',
        'ractive',
        'exports'
    ],
    function (
        dependencies,
        Ractive,
        exports
    ) {

        // Define the Ractive view
        exports.InjectedRactive = Ractive.extend(
            {

                data: {},

                isolated: true,

                magic: true,

                twoway: false,

                onconstruct: function ( options ) {

                    // Instantiate the injected view models (if any) for this component.
                    if ( this.viewModelNames ) {
                        for ( var viewModelName in this.viewModelNames ) {
                            if ( this.viewModelNames.hasOwnProperty( viewModelName ) ) {
                                options.data[viewModelName] = dependencies.context.get( this.viewModelNames[viewModelName] );
                            }
                        }
                    }

                },

                oninit: function () {

                    var me = this;

                    // Wires a controller event handler to an event of the same name (minus "on").
                    var respondToEvent = function ( controller, methodName ) {

                        // Compute the event name. E.g. method "onSomeEvent" handles event "someEvent".
                        var eventName = methodName[2].toLowerCase() + methodName.substring( 3 );

                        // Wire up the event handler.
                        me.on(
                            eventName, function ( event ) {
                                controller[methodName]( event, me );
                                return false;
                            }
                        );
                    };

                    // A derived instance can also provide controllers it constructs by custom means.
                    var controllers = me.controllers || [];

                    // Instantiate the injected controllers (if any) for this component.
                    if ( me.controllerNames ) {
                        me.controllerNames.forEach(
                            function ( controllerName ) {
                                controllers.push( dependencies.context.get( controllerName ) );
                            }
                        );
                    }

                    // Wire up the controller event handlers.
                    controllers.forEach(
                        function ( controller ) {
                            for ( var methodName in controller ) {
                                if ( methodName.indexOf( 'on' ) == 0 ) {
                                    respondToEvent( controller, methodName );
                                }
                            }
                        }
                    );

                    // Turn on the wiring between model and viewmodel.
                    var data = me.get('.');
                    for ( var viewModelName in data ) {
                        if ( data.hasOwnProperty( viewModelName ) ) {
                            var viewModel = me.get( viewModelName );
                            if ( viewModel.observeSelectionChanges ) {
                                viewModel.observeSelectionChanges();
                            }
                            else if ( viewModel.observeModelChanges ) {
                                viewModel.observeModelChanges();
                            }
                        }
                    }

                },

                onteardown: function () {

                    // Turn off the wiring between model and viewmodel.
                    var data = this.get('.');
                    for ( var viewModelName in data ) {
                        if ( data.hasOwnProperty( viewModelName ) ) {
                            var viewModel = this.get( viewModelName );
                            if ( viewModel.observeSelectionChanges ) {
                                viewModel.unobserveSelectionChanges();
                            }
                            else if ( viewModel.observeModelChanges ) {
                                viewModel.unobserveModelChanges();
                            }
                        }
                    }

                }

            }
        );

    }
);


