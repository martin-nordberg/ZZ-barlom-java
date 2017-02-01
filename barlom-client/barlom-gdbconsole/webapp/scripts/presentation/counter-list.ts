"use strict";

import {Handler} from '../infrastructure/tselmenite/util'
import {VNode, button, div, hr} from '../infrastructure/tselmenite/vdom'
import {
    Action as CounterAction,
    Model as CounterModel,
    update as updateCounter,
    view as viewCounter
} from './counter';


// ACTIONS

export class Action_Add {
    constructor(
        readonly kind : 'Action_Add' = 'Action_Add'
    ) {
    }
}
export class Action_Update {
    constructor(
        readonly id : number,
        readonly counterAction : CounterAction,
        readonly kind : 'Action_Update' = 'Action_Update'
    ) {
    }
}
export class Action_Remove {
    constructor(
        readonly id : number,
        readonly kind : 'Action_Remove' = 'Action_Remove'
    ) {
    }
}
export class Action_Reset {
    constructor(
        readonly kind : 'Action_Reset' = 'Action_Reset'
    ) {
    }
}

export type Action = Action_Add | Action_Update | Action_Remove | Action_Reset;


// MODEL

export class Model {
    constructor(
        readonly nextID : number,
        readonly counters : CounterModel[]
    ) {
    }
}

/**
 * Initializes the application state.
 */
export function initState() : Model {
    return { nextID: 1, counters: [] };
}


// VIEW

/**
 * Generates the mark up for the list of counters.
 * @param model the state of the counters.
 * @param handler event handling.
 */
export function view( model : Model, handler : Handler<Action> ) : VNode {
    return div(
        [
            button(
                { on: { click: () => handler( new Action_Add() ) } },
                ["Add"]
            ),
            button(
                { on: { click: () => handler( new Action_Reset() ) } },
                ["Reset"]
            ),
            hr(),
            div(
                '.counter-list',
                model.counters.map( item => counterItemView( item, handler ) )
            )
        ]
    );
}

/**
 * Generates the mark up for one counter plus a remove button.
 * @param item one entry in the counters array.
 * @param handler the master event handler
 */
function counterItemView( item : CounterModel, handler : Handler<Action> ) {
    return div(
        '.counter-item',
        { key: item.id },
        [
            button(
                '.remove',
                { on: { click: () => handler( new Action_Remove( item.id ) ) } },
                ["Remove"]
            ),
            viewCounter( item, a => handler( new Action_Update( item.id, a ) ) ),
            hr()
        ]
    );
}

// UPDATE

export function update( model : Model, action : Action ) : Model {

    switch ( action.kind ) {
        case 'Action_Add':
            return addCounter( model );
        case 'Action_Reset':
            return resetCounters( model );
        case 'Action_Remove':
            const removeAction = action as Action_Remove;
            return removeCounter( model, removeAction.id );
        case 'Action_Update':
            const updateAction = action as Action_Update;
            return updateCounterRow( model, updateAction.id, updateAction.counterAction );
    }

}

function addCounter( model : Model ) : Model {
    return new Model(
        model.nextID + 1,
        model.counters.concat( new CounterModel( model.nextID, 0 ) ),
    );
}

function resetCounters( model : Model ) : Model {
    return new Model(
        model.nextID,
        model.counters.map(
            ( item : CounterModel ) => new CounterModel( item.id, 0 )
        )
    );
}

function removeCounter( model : Model, id : number ) : Model {
    return new Model(
        model.nextID,
        model.counters.filter( item => item.id !== id )
    );
}

function updateCounterRow( model : Model, id : number, action : CounterAction ) : Model {
    return new Model(
        model.nextID,
        model.counters.map(
            item => item.id !== id ? item : new CounterModel( item.id, updateCounter( item, action ).count )
        )
    );
}

