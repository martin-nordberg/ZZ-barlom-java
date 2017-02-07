"use strict";

import {Handler, Update} from '../infrastructure/tselmenite/core'
import {VNode, button, div, hr} from '../infrastructure/webloop/vdom'
import {
    Action as CounterAction,
    Model as CounterModel,
    update as updateCounter,
    view as viewCounter
} from './counter';


// ACTIONS

const ACTION_ADD : 'Action_Add' = 'Action_Add';
const ACTION_REMOVE : 'Action_Remove' = 'Action_Remove';
const ACTION_RESET : 'Action_Reset' = 'Action_Reset';
const ACTION_UPDATE : 'Action_Update' = 'Action_Update';

export class Action_Add {
    constructor(
        readonly kind = ACTION_ADD
    ) {
    }
}
export class Action_Update {
    constructor(
        readonly id : number,
        readonly counterAction : CounterAction,
        readonly kind = ACTION_UPDATE
    ) {
    }
}
export class Action_Remove {
    constructor(
        readonly id : number,
        readonly kind = ACTION_REMOVE
    ) {
    }
}
export class Action_Reset {
    constructor(
        readonly kind = ACTION_RESET
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

export function update( model : Model, action : Action ) : Update<Model,Action> {

    switch ( action.kind ) {
        case ACTION_ADD:
            return new Update( addCounter( model ) );
        case ACTION_RESET:
            return new Update( resetCounters( model ) );
        case ACTION_REMOVE:
            const removeAction = action as Action_Remove;
            return new Update( removeCounter( model, removeAction.id ) );
        case ACTION_UPDATE:
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

function updateCounterRow( model : Model, id : number, action : CounterAction ) : Update<Model,Action> {
    return new Update(
        new Model(
            model.nextID,
            model.counters.map(
                item => item.id !== id ? item : updateCounter( item, action ).model
            )
        )
    );
}

