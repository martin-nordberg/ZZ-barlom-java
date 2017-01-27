"use strict";

import {VNode} from './lib/snabbdom/src/vnode';
import h from './lib/snabbdom/src/h';

export interface Action_Increment { kind : 'increment' }
export interface Action_Decrement { kind : 'decrement' }
export interface Action_Initialize { kind : 'initialize' }

export type Action = Action_Increment | Action_Decrement | Action_Initialize;

export const actionInitialize : Action_Initialize = { kind: 'initialize' };

export type ActionHandler = (action:Action) => void;


// model : Number
export function view( count : number, handler : ActionHandler ) : VNode {
    return h(
        'div', [
            h(
                'button', {
                    on: { click: handler.bind( null, { kind: 'increment' } ) }
                }, '+'
            ),
            h(
                'button', {
                    on: { click: handler.bind( null, { kind: 'decrement' } ) }
                }, '-'
            ),
            h( 'div', `Count : ${count}` ),
        ]
    );
}


export function update( count : number, action : Action ) : number {
    switch ( action.kind ) {
        case 'increment':
            return count + 1;
        case 'decrement':
            return count - 1;
        case 'initialize':
            return 0;
    }
}

