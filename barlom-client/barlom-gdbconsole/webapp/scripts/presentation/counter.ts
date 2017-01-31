"use strict";

import {Handler} from '../infrastructure/tselmenite/util'
import {VNode, button, div} from '../infrastructure/tselmenite/vdom'


// ACTIONS

export interface Action_Increment {
    kind : 'Action_Increment'
}
export interface Action_Decrement {
    kind : 'Action_Decrement'
}

export type Action = Action_Increment | Action_Decrement;


// MODEL

export interface Model {
    readonly id : number,
    readonly count : number
}


// VIEW

export function view( model : Model, handler : Handler<Action> ) : VNode {
    return div(
        '#counter-' + model.id, [
            button(
                {
                    on: { click: () => handler( { kind: 'Action_Increment' } ) }
                }, "+"
            ),
            button(
                {
                    on: { click: () => handler( { kind: 'Action_Decrement' } ) }
                }, "-"
            ),
            div( `Count : ${model.count}` ),
        ]
    );
}


// UPDATE

export function update( model : Model, action : Action ) : Model {
    switch ( action.kind ) {
        case 'Action_Increment':
            return { id: model.id, count: model.count + 1 };
        case 'Action_Decrement':
            return { id: model.id, count: model.count - 1 };
    }
}

