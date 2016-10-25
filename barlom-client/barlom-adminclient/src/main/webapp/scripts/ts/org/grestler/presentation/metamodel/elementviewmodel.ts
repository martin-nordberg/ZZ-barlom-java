//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/metamodel/elementviewmodel
 */

import api_elements = require( '../../domain/metamodel/api/elements' );
import elementmodel = require( './elementmodel' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining a handle to a metamodel element.
 */
export interface IElementHandle {

    /** The element held by the handle. */
    element : api_elements.IDocumentedElement;

    /** Generic revision number tracking all attribute changes to the element. */
    revision : number;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Handle to a metamodel element.
 */
export class ElementHandle implements IElementHandle {

    /**
     * Constructs a handle to a given element.
     * @param element the metamodel element to be watched.
     */
    constructor( element : api_elements.IDocumentedElement ) {

        var me = this;

        me.element = element;
        me.revision = 1;

        me._modelObserver = function ( changes ) {
            changes.forEach(
                function ( change ) {
                    console.log( change );
                    me.revision += 1;
                }
            )
        };

    }

    /**
     * Starts observing the associated model for changes.
     */
    public observeModelChanges() {

        if ( this.element != null ) {
            Object['observe'](
                this.element,
                this._modelObserver,
                ['change']  // TODO: not sure how to observe all changes; may need element help or just generic change event
            );
        }

    }

    /**
     * Stops observing model element changes.
     */
    public unobserveModelChanges() {
        if ( this.element != null ) {
            Object['unobserve']( this.element, this._modelObserver );
        }
    }

    /** The metamodel element referenced by this handle. */
    public element : api_elements.IDocumentedElement;

    /** Revision number for tracking arbitrary changes to the metamodel element. */
    public revision : number;

    /** Observer function. */
    private _modelObserver : ( changes : any ) => void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Extension of an element handle to a holder - where the element held can change along with its attributes.
 */
export class ElementHolder extends ElementHandle {

    /**
     * Constructs a new element holder starting with the given element.
     * @param elementSelection
     */
    constructor( elementSelection : elementmodel.ElementSelection ) {

        super( elementSelection.elementSelection );

        var me = this;

        me._elementSelection = elementSelection;

        me._selectionObservationCount = 0;

        me._selectionObserver = function ( changes ) {
            changes.forEach(
                function ( change ) {
                    console.log( change );
                    me.unobserveModelChanges();
                    me._setElement( change.newValue );
                    me.revision += 1;
                    me.observeModelChanges();
                }
            )
        };

    }

    /**
     * Starts observing the associated model for selection changes.
     */
    public observeSelectionChanges() {

        if ( this._selectionObservationCount == 0 ) {
            this._setElement( this._elementSelection.elementSelection );

            super.observeModelChanges();

            Object['observe'](
                this._elementSelection,
                this._selectionObserver,
                ['change.elementSelection']
            );
        }

        this._selectionObservationCount += 1;

    }

    /**
     * Stops observing the associated model for selection changes.
     */
    public unobserveSelectionChanges() {

        this._selectionObservationCount -= 1;

        if ( this._selectionObservationCount == 0 ) {
            Object['unobserve']( this._elementSelection, this._selectionObserver );

            super.unobserveModelChanges();
        }

    }

    /**
     * Changes what element is held.
     * @param element the new element to hold.
     * @private
     */
    private _setElement( element : api_elements.IDocumentedElement ) {
        this.element = element;
        this.isPackage = this.element && this.element.isA( api_elements.PACKAGE );

        switch ( this.element ? this.element.typeName : "NULL" ) {
            case api_elements.BOOLEAN_ATTRIBUTE_TYPE:
                this.typeDisplayName = "Attribute Type";
                break;
            case api_elements.DATE_TIME_ATTRIBUTE_TYPE:
                this.typeDisplayName = "Date/Time Attribute Type";
                break;
            case api_elements.DIRECTED_EDGE_TYPE:
                this.typeDisplayName = "Directed Edge Type";
                break;
            case api_elements.FLOAT64_ATTRIBUTE_TYPE:
                this.typeDisplayName = "64-Bit Floating Point Attribute Type";
                break;
            case api_elements.INTEGER32_ATTRIBUTE_TYPE:
                this.typeDisplayName = "32-Bit Integer Attribute Type";
                break;
            case api_elements.PACKAGE:
                this.typeDisplayName = "Package";
                break;
            case api_elements.PACKAGE_DEPENDENCY:
                this.typeDisplayName = "Package Dependency";
                break;
            case api_elements.ROOT_DIRECTED_EDGE_TYPE:
                this.typeDisplayName = "Root Directed Edge Type";
                break;
            case api_elements.ROOT_PACKAGE:
                this.typeDisplayName = "Root Package";
                break;
            case api_elements.ROOT_UNDIRECTED_EDGE_TYPE:
                this.typeDisplayName = "Root Undirected Edge Type";
                break;
            case api_elements.ROOT_VERTEX_TYPE:
                this.typeDisplayName = "Root Vertex Type";
                break;
            case api_elements.STRING_ATTRIBUTE_TYPE:
                this.typeDisplayName = "String Attribute Type";
                break;
            case api_elements.UNDIRECTED_EDGE_TYPE:
                this.typeDisplayName = "Undirected Edge Type";
                break;
            case api_elements.UUID_ATTRIBUTE_TYPE:
                this.typeDisplayName = "UUID Attribute Type";
                break;
            case api_elements.VERTEX_TYPE:
                this.typeDisplayName = "Vertex Type";
                break;
            case "NULL":
                this.typeDisplayName = null;
                break;
            default:
                this.typeDisplayName = this.element.typeName;
                break;
        }
    }

    /** Whether the selected element is a package. */
    public isPackage : boolean = false;

    /** Human-friendly type name of the element. */
    public typeDisplayName : string = null;

    /** The selected element to be watched. */
    private _elementSelection : elementmodel.ElementSelection;

    /** Number of time observeSelectionChanges has been called. */
    private _selectionObservationCount : number;

    /** The observer function for selection changes. */
    private _selectionObserver : ( changes : any ) => void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

