"use strict";

import {VNode} from './lib/snabbdom/src/vnode';
import h from './lib/snabbdom/src/h';


// ACTIONS

export interface Action_Increment { kind : 'Action_Increment' }
export interface Action_Decrement { kind : 'Action_Decrement' }

export type Action = Action_Increment | Action_Decrement;

export type ActionHandler = (action:Action) => void;


// MODEL

export interface Model {
    count : number
}

// VIEW

export function view( model : Model, handler : ActionHandler ) : VNode {
    return h(
        'div', [
            h(
                'button', {
                    on: { click: () => handler( { kind: 'Action_Increment' } ) }
                }, '+'
            ),
            h(
                'button', {
                    on: { click: () => handler( { kind: 'Action_Decrement' } ) }
                }, '-'
            ),
            h( 'div', `Count : ${model.count}` ),
        ]
    );
}


// UPDATE

export function update( model : Model, action : Action ) : Model {
    switch ( action.kind ) {
        case 'Action_Increment':
            return { count: model.count + 1 };
        case 'Action_Decrement':
            return { count: model.count - 1 };
    }
}

