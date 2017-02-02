"use strict";

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Generic handler type for update parameters. */
export type Handler<Action> = ( action : Action ) => void;

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Abstract base class for all side effects.
 */
export abstract class Command<Action> {

    /**
     * Constructs a new command of given type.
     * @param type the namespaced type of this command - a unique string identifier.
     */
    protected constructor(
        readonly type : string
    ) {}

    /**
     * Executes the side effect of this command with any resulting actions being sent to the given action handler.
     * @param handler the handler for receiving command outputs.
     */
    abstract execute( handler : Handler<Action> ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * A general purpose do-nothing command.
 */
export class Command_Noop<Action>
    extends Command<Action> {

    constructor() {
        super( "Command_Noop" )
    }

    execute( handler : Handler<Action> ) {
        // do nothing
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The result type for an update function taking model and action parameters of the given types.
 */
export class Update<Model,Action> {

    /**
     * Constructs a new result.
     * @param model the updated model.
     * @param command a command to be executed as a side effect of this update (default NO-OP).
     */
    constructor(
        readonly model : Model,
        readonly command : Command<Action> = new Command_Noop()
    ) {}

}

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Wraps a command giving a certain action with an outer command that wraps the action.
 */
export class Command_Wrapper<Action,InnerAction>
    extends Command<Action> {

    /**
     * Constructs a new command that wraps a given command with given mapping from inner action to outer action.
     * @param innerCommand the command to wrap.
     * @param actionWrapper a function that wraps the inner action by an outer action.
     */
    constructor(
        private innerCommand : Command<InnerAction>,
        private actionWrapper : (a:InnerAction) => Action
    ) {
        super( "Command_Wrapper" )
    }

    /**
     * Executes the side effect of this command by delegating to the inner command.
     * @param handler the handler to receive the wrapped action when the command completes.
     */
    execute( handler : Handler<Action> ) {
        this.innerCommand.execute( (a) => handler( this.actionWrapper(a) ) );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////

