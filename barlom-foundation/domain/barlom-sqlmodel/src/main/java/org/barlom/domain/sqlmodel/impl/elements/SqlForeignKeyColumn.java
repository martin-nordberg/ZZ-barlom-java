//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ESqlDataType;
import org.barlom.domain.sqlmodel.api.elements.ISqlForeignKeyColumn;
import org.barlom.domain.sqlmodel.api.elements.ISqlTable;

/**
 * A column acting as a foreign key.
 */
public class SqlForeignKeyColumn
    extends SqlTableColumn
    implements ISqlForeignKeyColumn {

    /**
     * Constructs a new attribute column.
     */
    @SuppressWarnings( "AssignmentToNull" )
    SqlForeignKeyColumn(
        SqlTable parent,
        String name,
        String relationshipName,
        String description,
        boolean isNullable
    ) {
        super( parent, name, description, ESqlDataType.INTEGER, null, null, isNullable, null );

        this.relatedTable = null;
        this.relationshipName = relationshipName;

        parent.onAddChild( this );
    }

    @Override
    public String getConstraintName() {
        return SqlNamedModelElement.makeSqlName(
            "FK",
            this.getParent().getSqlName(),
            this.getSqlName()
        );
    }

    @Override
    public ISqlTable getRelatedTable() {
        return this.relatedTable;
    }

    @Override
    public String getRelationshipName() {
        return this.relationshipName;
    }

    /**
     * Sets the relatedTable.
     *
     * @param relatedTable The new value for relatedTable.
     */
    public void setRelatedTable( ISqlTable relatedTable ) {
        this.relatedTable = relatedTable;
    }

    private ISqlTable relatedTable;

    private final String relationshipName;

}
