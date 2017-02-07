"use strict";

//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

///////////////////////////////////////////////////////////////////////////////////////////////////

export function assert( condition : boolean, message : string | (() => string) ) {
    if ( !condition ) {
        if ( typeof message === 'string' ) {
            throw new Error( message );
        }
        else {
            throw new Error( message() );
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////

export { assert as postcondition };

///////////////////////////////////////////////////////////////////////////////////////////////////

export { assert as precondition };

///////////////////////////////////////////////////////////////////////////////////////////////////

export function checkTypeName( typeName : string, namespace : string ) {

    const pattern = new RegExp( '^' + namespace + '[/\.]' + '[a-zA-Z0-9/\.]+$' );

    if ( !pattern.test( typeName ) ) {
        throw new Error( "Invalid type name for " + namespace + ": '" + typeName + "'." );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////


