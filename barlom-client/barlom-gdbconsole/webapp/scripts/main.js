/**
 * Barlom-GDB Console main program.
 */

"use strict";

import { stuff, moreStuff } from './junk/stuff.js'

import snabbdomx from './lib/snabbdom/snabbdom.js';
import h from './lib/snabbdom/h.js';

import snabbdomClass from './lib/snabbdom/snabbdom-class.js';
import snabbdomProps from './lib/snabbdom/snabbdom-props.js';
import snabbdomStyle from './lib/snabbdom/snabbdom-style.js';
import snabbdomEventListeners from './lib/snabbdom/snabbdom-eventlisteners.js';



console.log( "Stuff: ", stuff );

console.log( "More Stuff: ", moreStuff() );

console.log( snabbdom );


const patch = snabbdom.init([
                              snabbdomClass,
                              snabbdomProps,
                              snabbdomStyle,
                              snabbdomEventListeners
                            ]);


var vnode = h('div', {style: {fontWeight: 'bold'}}, 'Hello world');
patch(document.getElementById('app'), vnode);



