"use strict";

//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

///////////////////////////////////////////////////////////////////////////////////////////////////

import {checkTypeName} from '../utilities/assertions'
import {VNode} from "./vdom";

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract base class for an action. An action is an immutable representation of some change to
 * be made to the UI state or domain model. An action carries the data representing the change but
 * does not have any behavior itself. An action is passed to an update function along with UI state
 * and domain model. The update function computes the new UI state and a list of revisions to be
 * applied to the domain model.
 */
export abstract class Action {

    /**
     * Constructs a new action.
     * @param typeName the namespaced type of this action - a unique string identifier.
     */
    protected constructor(
        readonly typeName : string
    ) {
        checkTypeName( typeName, "Action" );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Type synonym representing a function that can handle an action. This is the master callback into
 * the main program loop for the action/update/revision/view/patch cycle.
 */
export type ActionHandler = ( action : Action ) => void;

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract base class for a command. A command is a unit of asynchronous execution with potential
 * side effects. It must be serializable in the sense of carrying along all the data it needs to
 * do its work. When the work finishes (always asynchronously), a command gives an action to the
 * handler. A command never makes direct changes to the UI state or domain model.
 */
export abstract class Command {

    /**
     * Constructs a new command of given type.
     * @param typeName the namespaced type of this command - a unique string identifier starting with "command/".
     */
    protected constructor(
        readonly typeName : string
    ) {
        checkTypeName( typeName, "Command" );
    }

    /**
     * Executes the side effect of this command with any resulting actions being sent to the given
     * action handler.
     * @param handler the handler for receiving command outputs.
     */
    abstract execute( handler : ActionHandler ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Type for representing the initial state and first command of a newly started program.
 */
export class InitState<UiState,Model> {

    /**
     * Constructs a new initial state.
     * @param uiState the initial UI state.
     * @param model the initial domain model.
     * @param commands the first commands to fire off (generally for loading non-default state asynchronously)
     */
    constructor(
        readonly uiState : UiState,
        readonly model : Model,
        readonly commands : Command[]
    ) {
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract class representing the data and behavior needed to carry out a domain model revision.
 */
export abstract class Revision<Model> {

    /**
     * Constructs a new revision.
     * @param typeName the name of the type of this revision.
     */
    protected constructor(
        readonly typeName : string
    ) {
        checkTypeName( typeName, "Revision" );
    }

    /**
     * Applies this revision by mutating the given domain model.
     * @param model the domain model to be mutated.
     */
    abstract apply( model : Model ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The result type for an update function taking model and action parameters of the given types.
 */
export class UpdateResult<UiState,Model> {

    /**
     * Constructs a new update result.
     * @param uiState the new UI state.
     * @param revisions a list of revisions to mutate the domain model.
     * @param commands a list of commands to queue and execute asynchronously (in parallel).
     */
    constructor(
        readonly uiState : UiState,
        readonly revisions : Revision<Model>[] = [],
        readonly commands : Command[] = []
    ) {
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Type synonym for the signature of a function that produces the initial state of the application.
 */
export type Init<UiState,Model> =
    () => InitState<UiState,Model>;

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Type synonym representing the signature of an update function. An update function takes in
 * the UI state, the domain model, and one action and returns the new UI state, a list of revisions
 * to the domain model, and a list of commands to be executed asynchronously in parallel.
 */
export type Update<UiState,Model> =
    ( uiState : UiState, model : Model, action : Action ) => UpdateResult<UiState,Model>;

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Type synonym representing the signature of a view function. A view function takes in the UI
 * state, the domain model, and a handler for future actions. It computes and returns the virtual
 * DOM of the application.
 */
export type View<UiState,Model> =
    ( uiState : UiState, model : Model, handler : ActionHandler ) => VNode;

///////////////////////////////////////////////////////////////////////////////////////////////////

