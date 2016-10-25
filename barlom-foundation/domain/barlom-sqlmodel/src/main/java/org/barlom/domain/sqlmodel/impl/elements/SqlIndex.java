//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlColumn;
import org.barlom.domain.sqlmodel.api.elements.ISqlIndex;

/**
 * An index.
 */
public class SqlIndex
    extends SqlNamedModelElement
    implements ISqlIndex {

    /**
     * Constructs a new index.
     */
    SqlIndex( SqlTable parent, ISqlColumn column ) {
        super( parent, column.getName(), "Index for " + column.getDescription() );

        this.column = column;

        parent.onAddChild( this );
    }

    @Override
    public ISqlColumn getColumn() {
        return this.column;
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( "IDX", this.getParent().getName(), this.getName() );
    }

    @Override
    public void setColumn( ISqlColumn column ) {
        this.column = column;
    }

    private ISqlColumn column;

}
