//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.api.elements;

import org.barlom.infrastructure.utilities.collections.IIndexable;

import java.util.Map;
import java.util.Optional;

/**
 * A database table.
 */
public interface ISqlTable
    extends ISqlRelation {

    /**
     * Creates an attribute column within this table.
     */
    @SuppressWarnings( "BooleanParameter" )
    ISqlAttributeColumn addAttributeColumn(
        String name,
        String attributeName,
        String description,
        ESqlDataType type,
        int size,
        int precision,
        boolean isNullable,
        Object defaultValue
    );

    /**
     * Creates a foreign key within this table.
     */
    @SuppressWarnings( "BooleanParameter" )
    ISqlForeignKeyColumn addForeignKeyColumn(
        String name,
        String relationshipName,
        String description,
        boolean isNullable
    );

    /**
     * @param name             the name of the new constraint.
     * @param description      a description of the new constraint.
     * @param foreignKeyColumn the column serving as foreign key.
     * @param relatedTable     the related table.
     * @param isCascadeDelete  whether to delete child records when a parent key is deleted.
     *
     * @return The newly added constraint
     */
    @SuppressWarnings( "BooleanParameter" )
    ISqlForeignKeyConstraint addForeignKeyConstraint(
        String name,
        String description,
        ISqlTableColumn foreignKeyColumn,
        ISqlTable relatedTable,
        boolean isCascadeDelete
    );

    /**
     * Creates a new index on the given column within this table.
     */
    ISqlIndex addIndex( ISqlColumn column );

    /**
     * Creates the primary key column within this table.
     */
    ISqlPrimaryKeyColumn addPrimaryKeyColumn( String name );

    /**
     * Creates the primary key column within this table.
     */
    ISqlPrimaryKeyConstraint addPrimaryKeyConstraint( String name );

    /**
     * Creates a pre-defined record within this table.
     */
    ISqlRecord addRecord( Map<String, Object> values );

    /**
     * Creates a foreign key within this table.
     */
    ISqlUniquenessConstraint addUniquenessConstraint( ISqlTableColumn uniqueColumn );

    /**
     * Creates a foreign key within this table.
     */
    ISqlUniquenessConstraint addUniquenessConstraint(
        String name,
        String description,
        ISqlTableColumn uniqueColumn1,
        ISqlTableColumn uniqueColumn2
    );

    /**
     * Creates a foreign key within this table.
     */
    ISqlUniquenessConstraint addUniquenessConstraint(
        String name,
        String description,
        IIndexable<ISqlTableColumn> uniqueColumns
    );

    /** @return the attributeColumns. */
    IIndexable<ISqlAttributeColumn> getAttributeColumns();

    /** @return the table column with given name. */
    @Override
    Optional<ISqlTableColumn> getColumnByName( String name );

    /** @return the foreignKeyColumns. */
    IIndexable<ISqlForeignKeyColumn> getForeignKeyColumns();

    /** @return the foreign key constraints. */
    IIndexable<ISqlForeignKeyConstraint> getForeignKeyConstraints();

    /** @return the indexes. */
    IIndexable<ISqlIndex> getIndexes();

    /** @return the primaryKeyColumn. */
    Optional<ISqlPrimaryKeyColumn> getPrimaryKeyColumn();

    /** @return the primaryKeyConstraint. */
    Optional<ISqlPrimaryKeyConstraint> getPrimaryKeyConstraint();

    /** @return the records. */
    IIndexable<ISqlRecord> getRecords();

    /** @return the uniqueness constraints. */
    IIndexable<ISqlUniquenessConstraint> getUniquenessConstraints();

}
