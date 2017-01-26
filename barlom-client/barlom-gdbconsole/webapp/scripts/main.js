"use strict";

import snabbdom from 'snabbdom';
import counterList from './counterList';

const patch = snabbdom.init(
  [
    require( 'snabbdom/modules/class' ),          // makes it easy to toggle classes
    require( 'snabbdom/modules/props' ),          // for setting properties on DOM elements
    require( 'snabbdom/modules/style' ),          // handles styling on elements with support for animations
    require( 'snabbdom/modules/eventlisteners' ), // attaches event listeners
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
