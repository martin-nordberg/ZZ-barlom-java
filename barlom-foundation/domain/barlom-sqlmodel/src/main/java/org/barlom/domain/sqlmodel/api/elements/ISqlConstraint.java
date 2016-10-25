//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.api.elements;

import org.barlom.infrastructure.utilities.collections.IIndexable;

/**
 * A constraint on the columns of a table.
 */
public interface ISqlConstraint
    extends ISqlNamedModelElement {

    /** @return the constrained columns. */
    IIndexable<ISqlTableColumn> getConstrainedColumns();

    @Override
    ISqlTable getParent();

}
