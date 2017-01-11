/**
 * Navigation reducers.
 */

import {ActionType, AnyAction, ChangeBrowserRouteAction, OtherAction} from '../actions/actiontypes'

export function barlomGdbConsole(
    state = '',
    action : AnyAction = OtherAction
) {

    switch ( action.type ) {
        case ActionType.ChangeBrowserRoute:
            return (<ChangeBrowserRouteAction>action).browserRoute;
        default:
            return state;
    }

}
