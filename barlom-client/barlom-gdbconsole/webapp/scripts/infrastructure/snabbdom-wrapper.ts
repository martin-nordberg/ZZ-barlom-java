"use strict";

import {VNode, VNodeData} from '../lib/snabbdom/src/vnode';
import h from '../lib/snabbdom/src/h';

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Re-export the Snabbdom type. */
export { VNode };

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Generic handler type for update parameters. */
export type Handler<Action> = ( action : Action ) => void;

///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Wraps Snabbdom to construct one regular VNode element from variably typed arguments.
 */
function makeVNode( elementName : string, a? : any, b? : any, c? : any ) : VNode {
    if ( c !== undefined ) {
        return h( elementName + a, b, c );
    }
    else if ( b !== undefined ) {
        if ( typeof a === 'string' && ( a.startsWith( '.' ) || a.startsWith( '#' ) ) ) {
            return h( elementName + a, b );
        }
        return h( elementName, a, b );
    }
    else if ( a != undefined ) {
        if ( typeof a === 'string' && ( a.startsWith( '.' ) || a.startsWith( '#' ) ) ) {
            return h( elementName + a );
        }
        return h( elementName, a );
    }
    return h( elementName );
}

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Variably typed VNode constructor function. */
export type VNodeBuilder = ( a? : string|VNodeData|Array<VNode>, b? : string|VNodeData|Array<VNode>, c? : string|Array<VNode> ) => VNode;

/** Curried VNode builder function. */
function makeVNodeBuilder( elementName : string ) : VNodeBuilder {
    return function( a? : string|VNodeData|Array<VNode>, b? : string|VNodeData|Array<VNode>, c? : string|Array<VNode> ) : VNode {
        return makeVNode( elementName, a, b, c );
    }
}

/** Variably typed childless VNode constructor function. */
export type ChildlessVNodeBuilder = ( a? : string|VNodeData, b? : VNodeData ) => VNode;

/** Curried childless VNode builder function. */
function makeChildlessVNodeBuilder( elementName : string ) : ChildlessVNodeBuilder {
    return function( a? : string|VNodeData, b? : VNodeData ) : VNode {
        return makeVNode( elementName, a, b );
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////

/** Constructs a button element. */
export const button = makeVNodeBuilder( 'button' );

/** Constructs a div element. */
export const div = makeVNodeBuilder( 'div' );

/** Constructs an hr element. */
export const hr = makeChildlessVNodeBuilder( 'hr' );

/** Constructs a span element. */
export const span = makeVNodeBuilder( 'span' );

///////////////////////////////////////////////////////////////////////////////////////////////////

