"use strict";

//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: Barlom infrastructure/modeling/elements.ts
 */

///////////////////////////////////////////////////////////////////////////////////////////////////

import {precondition, checkTypeName} from '../utilities/assertions'
import {isUuid} from "../utilities/uuids";

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared top level base interface for metadata elements.
 */
export interface IElement {

    /** The concrete type name of this element. */
    readonly typeName : string;

    /** The unique ID of this element */
    readonly uuid : string;

    /** Whether this element has the given type. */
    isA( typeName : string ) : boolean;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared top level abstract base class for metadata elements.
 */
export abstract class Element implements IElement {

    /**
     * Constructs a new abstract element.
     * @param typeName the name of the element type.
     * @param uuid the unique ID of the element.
     */
    constructor(
        readonly typeName : string,
        readonly uuid : string
    ) {
        checkTypeName( typeName, "Element" );
        precondition( isUuid( uuid ), () => "Invalid UUID: " + uuid );
    }

    /** Whether this element has the given type. */
    isA( typeName : string ) : boolean {
        return this.typeName === typeName;
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * An editable attribute that tracks the last saved value and the new unsaved value.
 */
export class Attr<T> {

    constructor() {
        this._savedValue = null;
        this._unsavedValue = null;
        this._validationErrors = [];
    }

    hasSavedValue() {
        return !!this._savedValue;
    }

    hasUnsavedValue() {
        return !!this._unsavedValue;
    }

    isValid() {
        return this._validationErrors.length === 0;
    }

    load( value : T | null ) {
        this._savedValue = value;
        this._unsavedValue = null;
    }

    revise( newValue : T | null ) {
        this._unsavedValue = newValue;
    }

    validate( validationErrors : string[] ) {
        this._validationErrors = Object.assign( [], validationErrors );
    }

    get validationErrors() : string[] {
        return this._validationErrors;
    }

    get value() : T | null {
        if ( this._unsavedValue ) {
            return this._unsavedValue;
        }
        return this._savedValue;
    }

    private _savedValue : T | null;

    private _unsavedValue : T | null;

    private _validationErrors : string[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * An editable relationship that tracks the last saved value, the new unsaved value, or just a UUID to be resolved
 * when a batch of edits have been completed.
 */
export class Rel<T extends IElement> extends Attr<T> {

    constructor() {
        super();
    }

    loadByUuid( findByUuid : ( uuid : string ) => T, uuid : string | null ) {

        if ( !!uuid ) {
            this.load( findByUuid( uuid ) );
        }
        else {
            this.load( null );
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

