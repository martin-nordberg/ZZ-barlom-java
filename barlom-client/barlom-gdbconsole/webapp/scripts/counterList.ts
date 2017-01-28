"use strict";

import {Handler, VNode, button, div, hr} from './infrastructure/snabbdom-wrapper'
import {
    view as viewCounter,
    update as updateCounter,
    Action as CounterAction,
    Model as CounterModel
} from './counter';


// ACTIONS

export interface Action_Add {
    kind : 'Action_Add'
}
export interface Action_Update {
    kind : 'Action_Update', id : number, counterAction : CounterAction
}
export interface Action_Remove {
    kind : 'Action_Remove', id : number
}
export interface Action_Reset {
    kind : 'Action_Reset'
}

export type Action = Action_Add | Action_Update | Action_Remove | Action_Reset;


// MODEL

export interface Model {
    readonly nextID : number,
    readonly counters : CounterModel[]
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
                {
                    on: { click: () => handler( { kind: 'Action_Add' } ) }
                }, "Add"
            ),
            button(
                {
                    on: { click: () => handler( { kind: 'Action_Reset' } ) }
                }, "Reset"
            ),
            hr(),
            div( '.counter-list', model.counters.map( item => counterItemView( item, handler ) ) )
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
        '.counter-item', { key: item.id }, [
            button(
                '.remove', {
                    on: { click: () => handler( { kind: 'Action_Remove', id: item.id } ) }
                }, 'Remove'
            ),
            viewCounter( item, a => handler( { kind: 'Action_Update', id: item.id, counterAction: a } ) ),
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
            const removeAction = <Action_Remove>action;
            return removeCounter( model, removeAction.id );
        case 'Action_Update':
            const updateAction = <Action_Update>action;
            return updateCounterRow( model, updateAction.id, updateAction.counterAction );
    }

}

function addCounter( model : Model ) : Model {
    const newCounter : CounterModel = { id: model.nextID, count: 0 };
    return {
        counters: model.counters.concat( newCounter ),
        nextID: model.nextID + 1
    };
}


function resetCounters( model : Model ) : Model {
    return {
        nextID: model.nextID,
        counters: model.counters.map(
            ( item : CounterModel ) => ({
                id: item.id,
                count: 0
            })
        )
    };
}

function removeCounter( model : Model, id : number ) : Model {
    return {
        nextID: model.nextID,
        counters: model.counters.filter( item => item.id !== id )
    };
}

function updateCounterRow( model : Model, id : number, action : CounterAction ) : Model {
    return {
        nextID: model.nextID,
        counters: model.counters.map(
            item =>
                item.id !== id ?
                    item
                    : {
                        id: item.id,
                        count: updateCounter( item, action ).count
                    }
        )
    };
}

