//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/infrastructure/reflection/functioninstrospection
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to one parameter of a function.
 */
export interface IParameterDescription {

    name : string;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to the name and parameters of a function.
 */
export interface IFunctionDescription {

    name : string;

    parameters : IParameterDescription[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class ParameterDescription implements IParameterDescription {

    constructor( name : string ) {
        this._name = name.trim();
    }

    public get name() : string {
        return this._name;
    }

    public set name( value : string ) {
        throw new Error( "Read only property 'name'." );
    }

    private _name : string;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class FunctionDescription implements IFunctionDescription {

    constructor( fn : Function ) {

        var parse = fn.toString().match( FunctionDescription.FN_ARGS );

        this._name = parse[1];   // TODO: not sure it's ever there

        this._parameters = [];

        var args = parse[2].split( ',' );

        args.forEach(
            ( arg : string ) => {
                if ( arg.length ) {
                    this._parameters.push( new ParameterDescription( arg ) )
                }
            }
        );

    }

    public get name() : string {
        return this._name;
    }

    public set name( value : string ) {
        throw new Error( "Read only property 'name'." );
    }

    public get parameters() : IParameterDescription[] {
        return this._parameters;
    }

    public set parameters( value : IParameterDescription[] ) {
        throw new Error( "Read only property 'name'." );
    }

    private static FN_ARGS = /^function\s*([^\(\s]*)\s*\(\s*([^\)]*)\)/m;

    private _name : string;

    private _parameters : IParameterDescription[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export function describeFunction( fn : Function ) : IFunctionDescription {
    return new FunctionDescription( fn );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
