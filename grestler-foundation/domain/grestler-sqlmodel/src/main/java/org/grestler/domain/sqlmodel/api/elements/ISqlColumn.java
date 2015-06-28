//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A column of a table or view.
 */
public interface ISqlColumn
    extends ISqlNamedModelElement {

    @Override
    ISqlRelation getParent();

}
