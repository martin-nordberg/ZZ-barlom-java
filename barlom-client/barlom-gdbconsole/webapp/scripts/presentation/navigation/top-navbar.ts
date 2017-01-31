"use strict";

import {Handler, VNode, div, span, nav} from '../../infrastructure/snabbdom-wrapper'


// ACTIONS

export interface Action_ActivateBrowse {
    kind : 'Action_ActivateBrowse'
}
export interface Action_ActivateDiagrams {
    kind : 'Action_ActivateDiagrams'
}

export type Action = Action_ActivateBrowse | Action_ActivateDiagrams;


// MODEL

export interface Model {
}


// VIEW

export function view( model : Model, handler : Handler<Action> ) : VNode {
    return nav(
        '.c-nav.c-nav--inline.c-nav--high', [
            div( '.c-nav__content', "Barlom-GDB Console" ),
            viewNavItem( "Browse", handler ),
            viewNavItem( "Diagrams", handler ),
            viewExitNavItem( handler )
        ]
    );
}


/** Generates the markup for the Exit link on the right side of the top nav bar. */
function viewExitNavItem( handler : Handler<Action> ) : VNode {
    return span(
        '.c-nav__item.c-nav__item--right', [
            "Exit"
        ]
    );
}


/** Generates markup for a single top nav item with given text, route, and on-click message. */
function viewNavItem( title : string, handler : Handler<Action> ) : VNode {
    return span(
        '.c-nav__item.c-nav__item--active', [
            title
        ]
    );
}


// UPDATE

export function update( model : Model, action : Action ) : Model {
    switch ( action.kind ) {
        case 'Action_ActivateBrowse':
            return {};
        case 'Action_ActivateDiagrams':
            return {};
    }
}

