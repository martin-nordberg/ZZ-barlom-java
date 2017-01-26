"use strict";

import { VNode } from './lib/snabbdom/src/vnode';
import h from './lib/snabbdom/src/h';

const INC = 'inc';
const DEC = 'dec';
const INIT = 'init';


// model : Number
function view( count: number, handler ) : VNode {
    return h(
        'div', [
            h(
                'button', {
                    on: { click: handler.bind( null, { type: INC } ) }
                }, '+'
            ),
            h(
                'button', {
                    on: { click: handler.bind( null, { type: DEC } ) }
                }, '-'
            ),
            h( 'div', `Count : ${count}` ),
        ]
    );
}


function update( count, action ) {
    return action.type === INC ? count + 1
         : action.type === DEC ? count - 1
         : action.type === INIT ? 0
         : count;
}

export default { view, update, INIT }
