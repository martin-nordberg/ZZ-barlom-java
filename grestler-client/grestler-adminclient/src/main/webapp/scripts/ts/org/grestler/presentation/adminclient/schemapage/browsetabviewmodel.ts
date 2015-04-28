//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/adminclient/schemapage/browsetabviewmodel
 */

import api_elements = require( '../../../domain/metamodel/api/elements' );
import elementmodel = require( '../../metamodel/elementmodel' )

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Schema page browse tab panel sections.
 */
export class BrowseTabEntries {

    /**
     * Constructs a new schema page visibilities object.
     */
    constructor( elementSelection : elementmodel.ElementSelection ) {

        var me = this;

        me.browseSections = [];

        me._element = null;
        me._elementSelection = elementSelection;

        me._modelObserver = function ( changes ) {
            me._updateBrowseSections( me._elementSelection.elementSelection );
        };

        me._selectionObserver = function ( changes ) {
            changes.forEach(
                function ( change ) {
                    console.log( change );
                    me._updateElementSelection( change.newValue );
                }
            )
        };

    }

    /**
     * Starts observing the associated model for selection changes.
     */
    public observeSelectionChanges() {

        console.log( "observeSelectionChanges" );
        this._updateElementSelection( this._elementSelection.elementSelection );

        Object['observe'](
            this._elementSelection,
            this._selectionObserver,
            ['change.elementSelection']
        );

    }

    /**
     * Stops observing the associated model for selection changes.
     */
    public unobserveSelectionChanges() {

        console.log( "unobserveSelectionChanges" );
        Object['unobserve']( this._elementSelection, this._selectionObserver );

    }

    /**
     * Starts observing the associated model for changes.
     */
    private _observeModelChanges( element : api_elements.IDocumentedElement ) {

        console.log( "_observeModelChanges: ", element );
        if ( element != null ) {
            Object['observe'](
                element,
                this._modelObserver,
                ['change.childElements']
            );
        }

    }

    /**
     * Stops observing model element changes.
     */
    private _unobserveModelChanges( element : api_elements.IDocumentedElement ) {

        console.log( "_unobserveModelChanges: ", element );
        if ( element != null ) {
            Object['unobserve']( element, this._modelObserver );
        }

    }

    /**
     * Responds when the details of the browse tab need updating.
     * @param element the new or changed model element.
     * @private
     */
    private _updateBrowseSections( element : api_elements.IDocumentedElement ) {

        console.log( "_updateBrowseSections: ", element );
        this.browseSections = [];

        // Package
        if ( element != null && element.typeName.indexOf( 'Package' ) >= 0 ) {
            var pkg = <api_elements.IPackage> element;

            var subPackagesSection = {
                title: "Sub-Packages",
                elements: []
            };

            pkg.childPackages.forEach(
                function ( subPkg ) {
                    subPackagesSection.elements.push( subPkg );
                }
            );

            this.browseSections.push( subPackagesSection );
        }

    }

    /**
     * Responds when what's selected for browsing changes.
     * @param element the newly selected model element.
     * @private
     */
    private _updateElementSelection( element : api_elements.IDocumentedElement ) {

        console.log( "_updateElementSelection: ", element );
        this._unobserveModelChanges( this._element );

        this._element = element;

        this._updateBrowseSections( element );

        this._observeModelChanges( element );

    }

    public browseSections : any;

    /* The currently selected model element. */
    private _element : api_elements.IDocumentedElement;

    /** The selected element to be watched. */
    private _elementSelection : elementmodel.ElementSelection;

    /** Observer function. */
    private _modelObserver : ( changes : any ) => void;

    /** The observer function for selection changes. */
    private _selectionObserver : ( changes : any ) => void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

