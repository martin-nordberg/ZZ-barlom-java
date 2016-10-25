//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.api.elements;

/**
 * A column acting as a foreign key.
 */
public interface ISqlForeignKeyColumn
    extends ISqlTableColumn {

    /** @return The name of the constraint for this primary key */
    String getConstraintName();

    /** @return the related table. */
    ISqlTable getRelatedTable();

    /** @return the relationshipName. */
    String getRelationshipName();

}
