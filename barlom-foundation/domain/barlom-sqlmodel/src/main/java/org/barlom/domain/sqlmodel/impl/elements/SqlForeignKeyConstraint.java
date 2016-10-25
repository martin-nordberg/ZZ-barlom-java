//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlForeignKeyConstraint;
import org.barlom.domain.sqlmodel.api.elements.ISqlTable;
import org.barlom.domain.sqlmodel.api.elements.ISqlTableColumn;

/**
 * A constraint representing a foreign key.
 */
public class SqlForeignKeyConstraint
    extends SqlConstraint
    implements ISqlForeignKeyConstraint {

    /**
     * Constructs a new primary key constraint.
     */
    SqlForeignKeyConstraint(
        SqlTable parent,
        String name,
        String description,
        ISqlTableColumn foreignKeyColumn,
        ISqlTable relatedTable,
        boolean isCascadeDelete
    ) {
        super( parent, name, description, SqlConstraint.makeListOfOneColumn( foreignKeyColumn ) );

        assert relatedTable != null : "Missing related table for " + parent.getSqlName() + "."
            + name;

        this.relatedTable = relatedTable;
        this.isCascadeDelete = isCascadeDelete;

        if ( foreignKeyColumn instanceof SqlForeignKeyColumn ) {
            ( (SqlForeignKeyColumn) foreignKeyColumn ).setRelatedTable( relatedTable );
        }

        parent.onAddChild( this );
    }

    @Override
    public ISqlTable getRelatedTable() {
        return this.relatedTable;
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( "FK", this.getParent().getName(), this.getName() );
    }

    @Override
    public boolean isCascadeDelete() {
        return this.isCascadeDelete;
    }

    private final boolean isCascadeDelete;

    private final ISqlTable relatedTable;

}
