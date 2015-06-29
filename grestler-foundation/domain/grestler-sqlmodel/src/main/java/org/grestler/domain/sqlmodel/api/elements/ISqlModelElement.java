//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * An abstract SQL model element.
 */
public interface ISqlModelElement {

    /** @return the description. */
    String getDescription();

    /** @return the parent. */
    @SuppressWarnings( "ClassReferencesSubclass" )
    ISqlNamedModelElement getParent();

    /** @return the schema (top level parent). */
    @SuppressWarnings( "ClassReferencesSubclass" )
    ISqlSchema getSchema();

}
