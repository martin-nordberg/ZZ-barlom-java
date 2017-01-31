"use strict";

import {init as snabbdomInit} from '../../lib/snabbdom/src/snabbdom';
import {classModule} from '../../lib/snabbdom/src/modules/class';
import {propsModule} from '../../lib/snabbdom/src/modules/props';
import {styleModule} from '../../lib/snabbdom/src/modules/style';
import {eventListenersModule} from '../../lib/snabbdom/src/modules/eventlisteners';
import {VNode} from "../../lib/snabbdom/src/vnode";

import {Action, Model, initState, update, view} from './main-window';

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
function main( state : Model, oldVnode : VNode | Element ) : void {

    let eventHandler = ( action : Action ) => {
        const newState = update( state, action );
        main( newState, newVnode );
    };

    const newVnode = view( state, eventHandler );

    patch( oldVnode, newVnode );

}

// Find the DOM node to drop our app in.
let domNode = document.getElementById( 'app' );

if ( domNode == null ) {
    // Abandon hope if the real DOM node is not found.
    console.log( "Cannot find application DOM node." );
}
else {
    // Fire off the first loop of the lifecycle and let it cycle via handler callbacks from then on.
    main( initState(), domNode );
}
