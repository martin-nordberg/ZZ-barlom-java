//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/infrastructure/dependencies/injection
 */

import functionintrospection = require( '../reflection/functionintrospection')

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface for a dependency injection context.
 */
export interface IContext {

    /**
     * Gets an object with given name from among the providers in this context.
     * @param objectName the name of the type or instance of an object to be provided.
     */
    get( objectName : string ) : any;

    /**
     * Augments this context with an additional provider. A provider function must be named provideXxx, where Xxx
     * is the name of the object the function provides. Xxx or xxx is then usable as the name of an argument to be
     * injected into other provider functions of the new context.
     * @param providerFunction the provider of named objects.
     */
    plus( providerFunction : Function ) : IContext;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Class representing the ability to provide an object of given name. Captures the function that will construct
 * the object plus the object names (parameter names) of the arguments needed by that function.
 */
class Provider {

    /**
     * Constructs a new provider for given function.
     * @param providerFunction a function named provideXxx that will construct an object with type or name Xxx.
     */
    constructor( providerFunction : Function ) {

        var providerDescription = functionintrospection.describeFunction( providerFunction );

        // Extract and validate the object name from the function name.
        var objectName = providerDescription.name;
        if ( !objectName || !objectName.length ) {
            throw new Error( "Provider function may not be anonymous." );
        }
        if ( objectName.length < 8 || objectName.indexOf( 'provide' ) != 0 ) {
            throw new Error( "Provider function " + objectName + " expected to be named provide[objectName].")
        }

        this._objectName = objectName.charAt( 7 ).toLowerCase() + objectName.substring( 8 );

        // Retrieve the names of the provider function parameters to become object names needed.
        this._providerArgObjectNames = [];
        providerDescription.parameters.forEach( param => this._providerArgObjectNames.push( param.name ) );

        this._providerFunction = providerFunction;

    }

    /**
     * @returns {string} The object name provided by this provider.
     */
    get objectName() : string {
        return this._objectName;
    }

    /**
     * Provides an object. Uses the given context to recursively provide any arguments needed.
     * @param context the provider context for dependencies.
     * @returns {any} the provided object.
     */
    provide( context : IContext ) : any {
        var args = [];

        this._providerArgObjectNames.forEach( function(objectName) {
            args.push( context.get( objectName ) );
        });

        return this._providerFunction.apply( context, args );
    }

    private _objectName : string;

    private _providerArgObjectNames : string[];

    private _providerFunction : Function;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of IContext.
 */
class Context implements IContext {

    /**
     * Constructs a new context that encompasses an inner context plus one additional provider.
     * @param innerContext the context to build upon.
     * @param providerFunction the additional provider function available in the new context.
     */
    constructor(
        innerContext : Context,
        providerFunction : Function
    ) {
        var provider = new Provider( providerFunction );

        this._innerContext = innerContext;
        this._providers = {};
        this._providers[ provider.objectName ] = provider;
    }

    /**
     * Constructs an object with given name or type name.
     * @param objectName the name of the kind of object to construct, using a registered provider in the context.
     * @returns {any}
     */
    public get( objectName : string ) : any {
        var provider = this.findProvider( objectName );
        return provider.provide( this );
    }

    /**
     * Constructs a new context that adds one additional provider to this context.
     * @param providerFunction the provider function to add.
     * @returns {Context} the new context.
     */
    public plus( providerFunction : Function ) : IContext {
        return new Context( this, providerFunction );
    }

    /**
     * Finds the provider for a given object name.
     * @param objectName the name of the kind of object needing provision.
     * @returns {Provider} the provider found (exception if not found).
     */
    private findProvider( objectName : string ) : Provider {

        // Try this context itself.
        var result : Provider = this._providers[ objectName ];

        // Try the inner context if necessary.
        if ( !result && this._innerContext ) {
            result = this._innerContext.findProvider( objectName );
        }

        // Exception if not found.
        if ( !result ) {
            throw new Error( "No provider for object named '" + objectName + "'." );
        }

        return result;
    }

    private _innerContext : Context;

    private _providers : {};

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Constructs a new context that encompasses an inner context plus one additional provider.
 * @param providerFunction the first provider function available in the new context.
 */
export function makeContext( providerFunction : Function ) {
    return new Context( null, providerFunction );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

