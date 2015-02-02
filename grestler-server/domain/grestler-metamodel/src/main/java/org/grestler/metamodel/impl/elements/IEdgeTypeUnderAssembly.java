//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;

/**
 * Internal interface for edge types.
 */
@SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
interface IEdgeTypeUnderAssembly {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    void addAttribute( IEdgeAttributeDecl attribute );

}
