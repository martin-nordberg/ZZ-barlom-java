"use strict";

import {init as snabbdomInit} from '../../lib/snabbdom/src/snabbdom';
import {classModule} from '../../lib/snabbdom/src/modules/class';
import {propsModule} from '../../lib/snabbdom/src/modules/props';
import {styleModule} from '../../lib/snabbdom/src/modules/style';
import {eventListenersModule} from '../../lib/snabbdom/src/modules/eventlisteners';
import {VNode} from '../../lib/snabbdom/src/vnode'

import {Command,Handler} from "./core";

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
    update : ( model : Model, action : Action ) => [ Model, Command<Action>|null ]
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
            let result : [ Model, Command<Action>|null ] = update( state, action );
            if ( result[1] !== null ) {
                result[1]!( eventHandler );
            }
            loop( result[0], newVnode );
        };

        // Compute the next virtual DOM.
        const newVnode = view( state, eventHandler );

        // Update the real DOM.
        patch( oldVnode, newVnode );

    }

    // Fire up the recursive view/update loop.
    loop( state, appElement );

}

