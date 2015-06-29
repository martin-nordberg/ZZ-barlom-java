//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ESqlDataType;
import org.grestler.domain.sqlmodel.api.elements.ISqlAttributeColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlForeignKeyColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlForeignKeyConstraint;
import org.grestler.domain.sqlmodel.api.elements.ISqlIndex;
import org.grestler.domain.sqlmodel.api.elements.ISqlPrimaryKeyColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlPrimaryKeyConstraint;
import org.grestler.domain.sqlmodel.api.elements.ISqlRecord;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;
import org.grestler.domain.sqlmodel.api.elements.ISqlTableColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlUniquenessConstraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A database table.
 */
public class SqlTable
    extends SqlRelation
    implements ISqlTable {

    /** Constructs a new table. */
    SqlTable( SqlSchema parent, String name, String description ) {
        super( parent, name, description );

        this.attributeColumns = new ArrayList<>();
        this.foreignKeyColumns = new ArrayList<>();
        this.foreignKeyConstraints = new ArrayList<>();
        this.indexes = new ArrayList<>();
        this.indexesByName = new HashMap<>();
        this.primaryKeyColumn = Optional.empty();
        this.primaryKeyConstraint = Optional.empty();
        this.records = new ArrayList<>();
        this.uniquenessConstraints = new ArrayList<>();

        parent.onAddChild( this );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public ISqlAttributeColumn addAttributeColumn(
        String name,
        String attributeName,
        String description,
        ESqlDataType type,
        int size,
        int precision,
        boolean isNullable,
        Object defaultValue
    ) {
        return new SqlAttributeColumn(
            this,
            name,
            attributeName,
            description,
            type,
            size,
            precision,
            isNullable,
            defaultValue
        );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public ISqlForeignKeyColumn addForeignKeyColumn(
        String name,
        String relationshipName,
        String description,
        boolean isNullable
    ) {
        return new SqlForeignKeyColumn( this, name, relationshipName, description, isNullable );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public ISqlForeignKeyConstraint addForeignKeyConstraint(
        String name,
        String description,
        ISqlTableColumn foreignKeyColumn,
        ISqlTable relatedTable,
        boolean isCascadeDelete
    ) {
        return new SqlForeignKeyConstraint(
            this,
            name,
            description,
            foreignKeyColumn,
            relatedTable,
            isCascadeDelete
        );
    }

    @Override
    public ISqlIndex addIndex( ISqlColumn column ) {
        return new SqlIndex( this, column );
    }

    @Override
    public ISqlPrimaryKeyColumn addPrimaryKeyColumn( String name ) {
        return new SqlPrimaryKeyColumn( this, name );
    }

    @Override
    public ISqlPrimaryKeyConstraint addPrimaryKeyConstraint( String name ) {
        return new SqlPrimaryKeyConstraint( this, name, "Primary key", this.primaryKeyColumn.get() );
    }

    @Override
    public ISqlRecord addRecord( Map<String, Object> values, Boolean isUnitTestValue ) {
        return new SqlRecord( this, values, isUnitTestValue );
    }

    @Override
    public ISqlUniquenessConstraint addUniquenessConstraint( ISqlTableColumn uniqueColumn ) {
        String name = SqlNamedModelElement.makeSqlName( this.getName(), uniqueColumn.getName() );
        String description = "Unique column: " + uniqueColumn.getSqlName();

        return this.addUniquenessConstraint(
            name,
            description,
            SqlConstraint.makeListOfOneColumn( uniqueColumn )
        );
    }

    @Override
    public ISqlUniquenessConstraint addUniquenessConstraint(
        String name,
        String description,
        ISqlTableColumn uniqueColumn1,
        ISqlTableColumn uniqueColumn2
    ) {
        return new SqlUniquenessConstraint(
            this,
            name,
            description,
            SqlConstraint.makeListOfTwoColumns( uniqueColumn1, uniqueColumn2 )
        );
    }

    @Override
    public ISqlUniquenessConstraint addUniquenessConstraint(
        String name,
        String description,
        List<ISqlTableColumn> uniqueColumns
    ) {
        return new SqlUniquenessConstraint( this, name, description, uniqueColumns );
    }

    @Override
    public List<ISqlAttributeColumn> getAttributeColumns() {
        return this.attributeColumns;
    }

    @Override
    public ISqlTableColumn getColumnByName( String name ) {
        return (ISqlTableColumn) super.getColumnByName( name );
    }

    @Override
    public List<ISqlForeignKeyColumn> getForeignKeyColumns() {
        return this.foreignKeyColumns;
    }

    @Override
    public List<ISqlForeignKeyConstraint> getForeignKeyConstraints() {
        return this.foreignKeyConstraints;
    }

    @Override
    public List<ISqlIndex> getIndexes() {
        return this.indexes;
    }

    @Override
    public Optional<ISqlPrimaryKeyColumn> getPrimaryKeyColumn() {
        return this.primaryKeyColumn;
    }

    @Override
    public Optional<ISqlPrimaryKeyConstraint> getPrimaryKeyConstraint() {
        return this.primaryKeyConstraint;
    }

    @Override
    public List<ISqlRecord> getRecords() {
        return this.records;
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( this.getName() );
    }

    @Override
    public List<ISqlUniquenessConstraint> getUniquenessConstraints() {
        return this.uniquenessConstraints;
    }

    /**
     * Creates a new column within this relation.
     */
    void onAddChild( ISqlAttributeColumn column ) {
        super.onAddChild( column );

        this.attributeColumns.add( column );
    }

    /**
     * Creates a new column within this relation.
     */
    void onAddChild( ISqlForeignKeyColumn column ) {
        super.onAddChild( column );

        this.foreignKeyColumns.add( column );
    }

    /**
     * Creates the primary key column within this table.
     */
    void onAddChild( ISqlForeignKeyConstraint foreignKeyConstraint ) {
        super.onAddChild( foreignKeyConstraint );

        this.foreignKeyConstraints.add( foreignKeyConstraint );
    }

    /**
     * Creates a new index on the given column within this table.
     */
    void onAddChild( ISqlIndex index ) {
        String indexName = index.getSqlName();

        assert this.indexesByName.get( indexName ) == null : "Duplicate index name: " + indexName;

        super.onAddChild( index );

        this.indexes.add( index );
        this.indexesByName.put( indexName, index );
    }

    /**
     * Creates the primary key column within this table.
     */
    void onAddChild( ISqlPrimaryKeyColumn primaryKeyCol ) {
        assert this.primaryKeyColumn == null;

        super.onAddChild( primaryKeyCol );

        this.primaryKeyColumn = Optional.of( primaryKeyCol );
    }

    /**
     * Creates the primary key column within this table.
     */
    void onAddChild( ISqlPrimaryKeyConstraint primaryKeyCon ) {
        assert this.primaryKeyConstraint == null;

        super.onAddChild( primaryKeyCon );

        this.primaryKeyConstraint = Optional.of( primaryKeyCon );
    }

    /**
     * Creates a new record within this table.
     */
    void onAddChild( ISqlRecord record ) {
        super.onAddChild( record );

        this.records.add( record );
    }

    /**
     * Creates a foreign key within this table.
     */
    void onAddChild( ISqlUniquenessConstraint constraint ) {
        super.onAddChild( constraint );

        this.uniquenessConstraints.add( constraint );
    }

    private final List<ISqlAttributeColumn> attributeColumns;

    private final List<ISqlForeignKeyColumn> foreignKeyColumns;

    private final List<ISqlForeignKeyConstraint> foreignKeyConstraints;

    private final List<ISqlIndex> indexes;

    private final Map<String, ISqlIndex> indexesByName;

    private Optional<ISqlPrimaryKeyColumn> primaryKeyColumn;

    private Optional<ISqlPrimaryKeyConstraint> primaryKeyConstraint;

    private final List<ISqlRecord> records;

    private final List<ISqlUniquenessConstraint> uniquenessConstraints;

}
