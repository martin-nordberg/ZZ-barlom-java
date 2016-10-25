//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/presentation/metamodel/elementcontroller
 */

import elementmodel = require( './elementmodel' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Controller for modifying a selected element.
 */
export class ElementController {

    constructor( elementSelection : elementmodel.ElementSelection ) {
        this._elementSelection = elementSelection;
    }

    /**
     * Responds to clicking the element link.
     * @param event the Ractive event.
     * @param view the Ractive view.
     */
    public onLinkClicked( event : any, view : any ) : void {
        view.get( 'targetElement' ).elementSelection = view.get( 'element' );
    }

    /** The selected element to be watched. */
    private _elementSelection : elementmodel.ElementSelection;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
