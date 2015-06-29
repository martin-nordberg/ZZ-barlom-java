//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A column for an attribute.
 */
public interface ISqlAttributeColumn
    extends ISqlTableColumn {

    /** @return the attribute name. */
    String getAttributeName();

}
