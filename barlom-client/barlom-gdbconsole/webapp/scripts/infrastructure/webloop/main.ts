"use strict";

//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

///////////////////////////////////////////////////////////////////////////////////////////////////

import {init as snabbdomInit} from '../../lib/snabbdom/src/snabbdom';
import {classModule} from '../../lib/snabbdom/src/modules/class';
import {propsModule} from '../../lib/snabbdom/src/modules/props';
import {styleModule} from '../../lib/snabbdom/src/modules/style';
import {eventListenersModule} from '../../lib/snabbdom/src/modules/eventlisteners';
import {VNode} from '../../lib/snabbdom/src/vnode'

import {Update, Init, View, Action} from "./core";

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Executes the main loop of a Tselmenite program.
 * @param init function computing the initial state of the program.
 * @param appElement the DOM element to put the application in.
 * @param view the topmost view function.
 * @param update the topmost update function.
 */
export function main<UiState,Model>(
    init : Init<UiState,Model>,
    appElement : Element,
    view : View<UiState,Model>,
    update : Update<UiState,Model>
) {

    // Get a Snabbdom patch function with the normal HTML modules.
    const patch = snabbdomInit(
        [
            classModule,
            propsModule,
            styleModule,
            eventListenersModule
        ]
    );

    /**
     * Runs one cycle of the Snabbdom event loop.
     * @param uiState the latest UI state.
     * @param model the latest model state.
     * @param oldVnode the prior virtual DOM or the real DOM first time through.
     */
    function loop( uiState: UiState, model : Model, oldVnode : VNode | Element ) : void {

        /** Execute one update cycle recursively. */
        let eventHandler = ( action : Action ) => {

            // Update the state from the latest action.
            let result = update( uiState, model, action );

            result.revisions.forEach( rev => rev.apply( model ) );

            // Fire off the commands returned from the update.
            result.commands.forEach( cmd => cmd.execute( eventHandler ) );

            // Recursively update the view.
            loop( result.uiState, model, newVnode );
        };

        // Compute the next virtual DOM.
        const newVnode = view( uiState, model, eventHandler );

        // Update the real DOM.
        patch( oldVnode, newVnode );

    }

    // Compute the starting state of the application.
    let initialState = init();

    // Fire up the recursive update/view loop.
    loop( initialState.uiState, initialState.model, appElement );

}

///////////////////////////////////////////////////////////////////////////////////////////////////

