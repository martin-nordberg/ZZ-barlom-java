"use strict";

import h from './lib/snabbdom/src/h';
import {VNode} from './lib/snabbdom/src/vnode';
import {
    view as viewCounter,
    update as updateCounter,
    Action as CounterAction
} from './counter';


// ACTIONS

export interface Action_Add { kind : 'Action_Add' }
export interface Action_Update { kind : 'Action_Update', id : number, counterAction : CounterAction }
export interface Action_Remove { kind : 'Action_Remove', id : number }
export interface Action_Reset { kind : 'Action_Reset' }

export type Action = Action_Add | Action_Update | Action_Remove | Action_Reset;

export type ActionHandler = (action:Action) => void;


// MODEL

export interface CounterItem {
    id : number,
    count : number
}

export interface Model {
    nextID : number,
    counters : CounterItem[]
}

/**
 * Initializes the application state.
 */
export function initState() : Model {
    return { nextID: 5, counters: [] };
}


// VIEW

/**
 * Generates the mark up for the list of counters.
 * @param model the state of the counters.
 * @param handler event handling.
 */
export function view( model : Model, handler : ActionHandler ) : VNode {
    return h(
        'div', [
            h(
                'button', {
                    on: { click: () => handler( { kind: 'Action_Add' } ) }
                }, 'Add'
            ),
            h(
                'button', {
                    on: { click: () => handler( { kind: 'Action_Reset' } ) }
                }, 'Reset'
            ),
            h( 'hr' ),
            h( 'div.counter-list', model.counters.map( item => counterItemView( item, handler ) ) )

        ]
    );
}

/**
 * Generates the mark up for one counter plus a remove button.
 * @param item one entry in the counters array.
 * @param handler the master event handler
 */
function counterItemView( item :CounterItem, handler : ActionHandler ) {
    return h(
        'div.counter-item', { key: item.id }, [
            h(
                'button.remove', {
                    on: { click: () => handler( { kind: 'Action_Remove', id: item.id } ) }
                }, 'Remove'
            ),
            viewCounter( { count: item.count }, a => handler( { kind: 'Action_Update', id: item.id, counterAction: a } ) ),
            h( 'hr' )
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
    const newCounter : CounterItem = { id: model.nextID, count: 0 };
    return {
        counters: model.counters.concat(newCounter),
        nextID: model.nextID + 1
    };
}


function resetCounters( model : Model ) : Model {
    return {
        nextID: model.nextID,
        counters: model.counters.map(
            ( item : CounterItem ) => ({
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

