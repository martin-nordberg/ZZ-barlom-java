//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlPrimaryKeyConstraint;
import org.barlom.domain.sqlmodel.api.elements.ISqlTableColumn;

/**
 * A constraint for a primary key.
 */
public class SqlPrimaryKeyConstraint
    extends SqlConstraint
    implements ISqlPrimaryKeyConstraint {

    /**
     * Constructs a new primary key constraint.
     */
    SqlPrimaryKeyConstraint(
        SqlTable parent,
        String name,
        String description,
        ISqlTableColumn primaryKeyColumn
    ) {
        super( parent, name, description, SqlConstraint.makeListOfOneColumn( primaryKeyColumn ) );

        parent.onAddChild( this );
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( "PK", this.getParent().getName() );
    }
}
