"use strict";

import {init as snabbdomInit} from './lib/snabbdom/src/snabbdom';
import {classModule} from './lib/snabbdom/src/modules/class';
import {propsModule} from './lib/snabbdom/src/modules/props';
import {styleModule} from './lib/snabbdom/src/modules/style';
import {eventListenersModule} from './lib/snabbdom/src/modules/eventlisteners';
import {VNode} from "./lib/snabbdom/src/vnode";

import {view as viewCounterList, update as updateCounterList, Model as CounterListModel, Action as CounterListAction, ActionHandler as CounterListActionHandler} from './counterList';

// Get a Snabbdom patch function with the normal HTML modules.
const patch = snabbdomInit(
    [
        classModule,
        propsModule,
        styleModule,
        eventListenersModule
    ]
);

// view function type
type ViewFunction = ( model : CounterListModel, handler : CounterListActionHandler ) => VNode;

type UpdateFunction = ( model : CounterListModel, action : CounterListAction ) => CounterListModel;

/**
 * Runs one cycle of the Snabbdom event loop.
 * @param state the latest model state.
 * @param oldVnode the prior virtual DOM or the real DOM first time through.
 * @param view the function to compute the new virtual DOM from the model.
 * @param update the function to compute the new model state from the old state and a pending action.
 */
function main( state: CounterListModel, oldVnode : VNode | Element, view : ViewFunction, update : UpdateFunction ) : void {

    let eventHandler = ( action : CounterListAction ) => {
        const newState = update( state, action );
        main( newState, newVnode, view, update );
    };

    const newVnode = view( state, eventHandler );

    patch( oldVnode, newVnode );

}

/**
 * Initializes the application state.
 * @returns {{nextID: number, counters: Array}}
 */
function initState() : CounterListModel {
    return { nextID: 5, counters: [] };
}

// ---------------------------------------------------------------------------

// Find the DOM node to drop our app in.
let domNode = document.getElementById( 'app' );

if ( domNode == null ) {
    // Abandon hope if the real DOM node is not found.
    console.log( "Cannot find application DOM node." );
}
else {
    // Fire off the first loop of the lifecycle.
    main( initState(), domNode, viewCounterList, updateCounterList );
}
