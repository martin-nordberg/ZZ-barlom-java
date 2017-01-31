"use strict";

import {Handler} from '../../infrastructure/tselmenite/util'
import {VNode, div, main} from '../../infrastructure/tselmenite/vdom'
import {
    Action as CounterListAction,
    Model as CounterListModel,
    initState as initCounterList,
    update as updateCounterList,
    view as viewCounterList
} from '../counter-list';
import {
    // Action as TopNavActionAction,
    // Model as TopNavModel
    // update as updateTopNavBar,
    view as viewTopNavBar
} from '../navigation/top-navbar';


// ACTIONS

export interface Action_ListUpdate {
    kind : 'Action_ListUpdate', counterListAction : CounterListAction
}

export interface Action_TopNav {
    kind : 'Action_TopNav'
}

export type Action = Action_ListUpdate | Action_TopNav;


// MODEL

export interface Model {
    readonly counterList : CounterListModel
}

/**
 * Initializes the application state.
 */
export function initState() : Model {
    return { counterList: initCounterList() };
}


// VIEW

/**
 * Generates the mark up for the list of counters.
 * @param model the state of the counters.
 * @param handler event handling.
 */
export function view( model : Model, handler : Handler<Action> ) : VNode {
    return main(
        '.o-grid.o-grid--no-gutter.o-panel',
        [
            div(
                '.o-grid__cell--width-100.o-panel-container', [
                    viewTopNavBar( {}, a => handler( { kind: 'Action_TopNav', counterListAction: a } ) ),
                    viewCounterList(
                        model.counterList,
                        a => handler( { kind: 'Action_ListUpdate', counterListAction: a } )
                    )
                ]
            )
        ]
    );
}

// UPDATE

export function update( model : Model, action : Action ) : Model {

    switch ( action.kind ) {
        case 'Action_ListUpdate':
            const updateAction = <Action_ListUpdate>action;
            return { counterList: updateCounterList( model.counterList, updateAction.counterListAction ) };
        case 'Action_TopNav':
            // const topNavAction = <Action_TopNav>action;
            return model;
    }

}

