"use strict";

import {Handler, Update} from '../infrastructure/tselmenite/core'
import {VNode, button, div} from '../infrastructure/webloop/vdom'


// ACTIONS

const ACTION_INCREMENT : 'Action_Increment' = 'Action_Increment';
const ACTION_DECREMENT : 'Action_Decrement' = 'Action_Decrement';

export class Action_Increment {
    constructor(
        readonly kind = ACTION_INCREMENT
    ) {
    }
}
export class Action_Decrement {
    constructor(
        readonly kind = ACTION_DECREMENT
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

export function update( model : Model, action : Action ) : Update<Model,Action> {
    switch ( action.kind ) {
        case ACTION_INCREMENT:
            return new Update( new Model( model.id, model.count + 1 ) );
        case ACTION_DECREMENT:
            return new Update( new Model( model.id, model.count - 1 ) );
    }
}

