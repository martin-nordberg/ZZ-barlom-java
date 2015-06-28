//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlRelation;

/**
 * A column of a table or view.
 */
public abstract class SqlColumn
    extends SqlNamedModelElement
    implements ISqlColumn {

    /**
     * Constructs a new database column.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    SqlColumn( ISqlRelation parent, String name, String description ) {
        super( parent, name, description );
    }

    @Override
    public ISqlRelation getParent() {
        return (ISqlRelation) super.getParent();
    }

}
