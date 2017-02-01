"use strict";

import {VNode, VNodeData} from '../../lib/snabbdom/src/vnode';
import h from '../../lib/snabbdom/src/h';

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Re-export the Snabbdom type. */
export {VNode};

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Wraps Snabbdom to construct one regular VNode element from variably typed arguments.
 */
function makeVNode( elementName : string, a? : any, b? : any, c? : any ) : VNode {
    if ( c !== undefined ) {
        return h( elementName + a, b, c );
    }
    else if ( b !== undefined ) {
        if ( typeof a === 'string' ) {
            return h( elementName + a, b );
        }
        return h( elementName, a, b );
    }
    else if ( a != undefined ) {
        if ( typeof a === 'string' ) {
            return h( elementName + a );
        }
        return h( elementName, a );
    }
    return h( elementName );
}

///////////////////////////////////////////////////////////////////////////////////////////////////

type Arg1Of3 = string | VNodeData | Array<VNode|string>;
type Arg2Of3 = VNodeData | Array<VNode|string>;
type Arg3Of3 = Array<VNode|string>

/** Variably typed VNode constructor function. */
export type VNodeBuilder = ( a? : Arg1Of3, b? : Arg2Of3, c? : Arg3Of3 ) => VNode;

/** Curried VNode builder function. */
function makeVNodeBuilder( elementName : string ) : VNodeBuilder {
    return function ( a? : Arg1Of3, b? : Arg2Of3, c? : Arg3Of3 ) : VNode {
        return makeVNode( elementName, a, b, c );
    }
}

type Arg1Of2 = string | VNodeData;
type Arg2Of2 = VNodeData;

/** Variably typed childless VNode constructor function. */
export type ChildlessVNodeBuilder = ( a? : Arg1Of2, b? : Arg2Of2 ) => VNode;

/** Curried childless VNode builder function. */
function makeChildlessVNodeBuilder( elementName : string ) : ChildlessVNodeBuilder {
    return function ( a? : Arg1Of2, b? : Arg2Of2 ) : VNode {
        return makeVNode( elementName, a, b );
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////

// TODO: Add many more element types as needed ...

/** Constructs a button element. */
export const button = makeVNodeBuilder( 'button' );

/** Constructs a div element. */
export const div = makeVNodeBuilder( 'div' );

/** Constructs an hr element. */
export const hr = makeChildlessVNodeBuilder( 'hr' );

/** Constructs a main element. */
export const main = makeVNodeBuilder( 'main' );

/** Constructs a nav element. */
export const nav = makeVNodeBuilder( 'nav' );

/** Constructs a span element. */
export const span = makeVNodeBuilder( 'span' );

///////////////////////////////////////////////////////////////////////////////////////////////////

