"use strict";

import {init as snabbdomInit} from './lib/snabbdom/src/snabbdom';
import {classModule} from './lib/snabbdom/src/modules/class';
import {propsModule} from './lib/snabbdom/src/modules/props';
import {styleModule} from './lib/snabbdom/src/modules/style';
import {eventListenersModule} from './lib/snabbdom/src/modules/eventlisteners';

import counterList from './counterList';

const patch = snabbdomInit(
    [
        classModule,
        propsModule,
        styleModule,
        eventListenersModule
    ]
);


function main( state, oldVnode, { view, update } ) {

    let eventHandler = e => {
        const newState = update( state, e );
        main( newState, newVnode, { view, update } );
    };

    const newVnode = view( state, eventHandler );

    patch( oldVnode, newVnode );

}

function initState() {
    return { nextID: 1, counters: [] };
}

main(
    initState(),
    document.getElementById( 'app' ),
    counterList
);
