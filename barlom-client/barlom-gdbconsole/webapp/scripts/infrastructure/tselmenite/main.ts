"use strict";

import {init as snabbdomInit} from '../../lib/snabbdom/src/snabbdom';
import {classModule} from '../../lib/snabbdom/src/modules/class';
import {propsModule} from '../../lib/snabbdom/src/modules/props';
import {styleModule} from '../../lib/snabbdom/src/modules/style';
import {eventListenersModule} from '../../lib/snabbdom/src/modules/eventlisteners';
import {VNode} from '../../lib/snabbdom/src/vnode'

import {Handler, Update} from "./core";

/**
 * Executes the main loop of a Tselmenite program.
 * @param state the initial state of the program.
 * @param appElement the DOM element to put the application in.
 * @param view the topmost view function.
 * @param update the topmost update function.
 */
export function main<Model,Action>(
    state : Model,
    appElement : Element,
    view : ( model : Model, handler : Handler<Action> ) => VNode,
    update : ( model : Model, action : Action ) => Update<Model,Action>
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
     * @param state the latest model state.
     * @param oldVnode the prior virtual DOM or the real DOM first time through.
     */
    function loop( state : Model, oldVnode : VNode | Element ) : void {

        /** Execute one update cycle recursively. */
        let eventHandler = ( action : Action ) => {

            // Update the state from the latest action.
            let result = update( state, action );

            // Fire off the command returned from the update (usually a NO-OP).
            result.command.execute( eventHandler );

            // Recursively update the view.
            loop( result.model, newVnode );
        };

        // Compute the next virtual DOM.
        const newVnode = view( state, eventHandler );

        // Update the real DOM.
        patch( oldVnode, newVnode );

    }

    // Fire up the recursive update/view loop.
    loop( state, appElement );

}

