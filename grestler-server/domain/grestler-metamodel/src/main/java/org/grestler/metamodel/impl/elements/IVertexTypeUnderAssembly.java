//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IVertexAttributeDecl;

/**
 * Internal interface for vertex types.
 */
@SuppressWarnings( "InterfaceMayBeAnnotatedFunctional" )
interface IVertexTypeUnderAssembly {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    void addAttribute( IVertexAttributeDecl attribute );

}
