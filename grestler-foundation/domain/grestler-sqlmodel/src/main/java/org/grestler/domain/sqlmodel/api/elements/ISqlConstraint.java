//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.List;

/**
 * A constraint on the columns of a table.
 */
public interface ISqlConstraint
    extends ISqlNamedModelElement {

    /** @return the constrained columns. */
    List<ISqlTableColumn> getConstrainedColumns();

    @Override
    ISqlTable getParent();

}
