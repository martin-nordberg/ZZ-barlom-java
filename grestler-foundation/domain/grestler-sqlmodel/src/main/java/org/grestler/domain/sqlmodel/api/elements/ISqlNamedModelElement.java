//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

/**
 * An abstract model element with a name.
 */
public interface ISqlNamedModelElement
    extends ISqlModelElement {

    /** @return the children. */
    IIndexable<ISqlModelElement> getChildren();

    /** @return the name. */
    String getName();

    /** @return the path of this named element not including the schema. */
    String getPath();

    /** @return the path of this named element including the schema. */
    String getPathWithSchema();

    /** @return the name of this element for SQL purposes. */
    String getSqlName();

}
