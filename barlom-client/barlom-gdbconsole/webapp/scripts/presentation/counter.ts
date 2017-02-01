"use strict";

import {Handler} from '../infrastructure/tselmenite/core'
import {VNode, button, div} from '../infrastructure/tselmenite/vdom'


// ACTIONS

export class Action_Increment {
    constructor(
        readonly kind : 'Action_Increment' = 'Action_Increment'
    ) {
    }
}
export class Action_Decrement {
    constructor(
        readonly kind : 'Action_Decrement' = 'Action_Decrement'
    ) {
    }
}

export type Action = Action_Increment | Action_Decrement;


// MODEL

export class Model {
    constructor(
        readonly id : number,
        readonly count : number
    ) {
    }
}


// VIEW

export function view( model : Model, handler : Handler<Action> ) : VNode {
    return div(
        '#counter-' + model.id, [
            button(
                { on: { click: () => handler( new Action_Increment() ) } },
                ["+"]
            ),
            button(
                { on: { click: () => handler( new Action_Decrement() ) } },
                ["-"]
            ),
            div( [`Count : ${model.count}`] ),
        ]
    );
}


// UPDATE

export function update( model : Model, action : Action ) : Model {
    switch ( action.kind ) {
        case 'Action_Increment':
            return new Model( model.id, model.count + 1 );
        case 'Action_Decrement':
            return new Model( model.id, model.count - 1 );
    }
}

