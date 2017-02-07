"use strict";

import {Handler, Command_Wrapper, Update} from '../../infrastructure/tselmenite/core'
import {VNode, div, main} from '../../infrastructure/webloop/vdom'
import {
    Action as CounterListAction,
    Model as CounterListModel,
    initState as initCounterList,
    update as updateCounterList,
    view as viewCounterList
} from '../counter-list';
import {
    Action as TopNavAction,
    update as updateTopNavBar,
    view as viewTopNavBar
} from '../navigation/top-navbar';


// ACTIONS

export class Action_ListUpdate {
    constructor(
        readonly counterListAction : CounterListAction,
        readonly kind : 'Action_ListUpdate' = 'Action_ListUpdate'
    ) {}
}

export class Action_TopNav {
    constructor(
        readonly topNavAction : TopNavAction,
        readonly kind : 'Action_TopNav' = 'Action_TopNav'
    ) {}
}

export type Action = Action_ListUpdate | Action_TopNav;


// MODEL

export class Model {
    constructor(
        readonly counterList : CounterListModel
    ) {}
}

/**
 * Initializes the application state.
 */
export function initState() : Model {
    return new Model( initCounterList() );
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
                    viewTopNavBar( {}, a => handler( new Action_TopNav( a ) ) ),
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

export function update( model : Model, action : Action ) : Update<Model,Action> {

    switch ( action.kind ) {
        case 'Action_ListUpdate':
            const updateAction = action as Action_ListUpdate;
            return new Update( new Model( updateCounterList( model.counterList, updateAction.counterListAction ).model ) );
        case 'Action_TopNav':
            const topNavAction = action as Action_TopNav;
            const u = updateTopNavBar(model,topNavAction.topNavAction);
            return new Update( model, new Command_Wrapper( u.command, (a) => new Action_TopNav(a) ) );
    }

}

