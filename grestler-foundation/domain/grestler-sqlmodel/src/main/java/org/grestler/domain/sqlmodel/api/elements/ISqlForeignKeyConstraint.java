//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

/**
 * A constraint representing a foreign key.
 */
public interface ISqlForeignKeyConstraint
    extends ISqlConstraint {

    /** @return the relatedTable. */
    ISqlTable getRelatedTable();

    /** @return whether the child record should be cascase deleted with the parent. */
    boolean isCascadeDelete();

}
