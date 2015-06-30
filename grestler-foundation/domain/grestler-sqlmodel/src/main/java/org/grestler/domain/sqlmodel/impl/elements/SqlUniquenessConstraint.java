//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlTableColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlUniquenessConstraint;
import org.grestler.infrastructure.utilities.collections.IIndexable;

/**
 * A constraint enforcing column uniqueness.
 */
public class SqlUniquenessConstraint
    extends SqlConstraint
    implements ISqlUniquenessConstraint {

    /**
     * Constructs a new uniqueness constraint.
     */
    SqlUniquenessConstraint(
        SqlTable parent,
        String name,
        String description,
        IIndexable<ISqlTableColumn> uniqueColumns
    ) {
        super( parent, name, description, uniqueColumns );

        parent.onAddChild( this );
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( "UQ", this.getName() );
    }

}
