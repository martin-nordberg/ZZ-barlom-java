//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * An abstract SQL model element.
 */
public interface ISqlModelElement {

    /** Returns the description. */
    String getDescription();

    /** Returns the parent. */
    ISqlNamedModelElement getParent();

    /** Returns the schema (top level parent). */
    ISqlSchema getSchema();

}
