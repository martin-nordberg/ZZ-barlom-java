//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlColumn;
import org.grestler.domain.sqlmodel.api.elements.ISqlRelation;
import org.grestler.domain.sqlmodel.api.elements.ISqlSchema;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * An abstract relation (table, view, etc.).
 */
public abstract class SqlRelation
    extends SqlNamedModelElement
    implements ISqlRelation {

    /**
     * Constructs a new table or view.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    SqlRelation( SqlSchema parent, String name, String description ) {
        super( parent, name, description );

        this.columns = new ArrayList<>();
        this.columnsByName = new HashMap<>();
    }

    @Override
    public Optional<? extends ISqlColumn> getColumnByName( String name ) {
        return Optional.ofNullable( this.columnsByName.get( SqlNamedModelElement.makeSqlName( name ) ) );
    }

    @Override
    public IIndexable<ISqlColumn> getColumns() {
        return new ReadOnlyListAdapter<>( this.columns );
    }

    @Override
    public ISqlSchema getParent() {
        return (ISqlSchema) super.getParent();
    }

    /**
     * Creates a new column within this relation.
     */
    protected void addColumn( ISqlColumn column ) {
        assert this.columnsByName.get( column.getSqlName() ) == null : "Duplicate column name: "
            + column.getSqlName();

        this.columns.add( column );
        this.columnsByName.put( column.getSqlName(), column );
    }

    /**
     * Creates a new column within this relation.
     */
    void onAddChild( ISqlColumn column ) {
        String columnName = column.getSqlName();

        assert this.columnsByName.get( columnName ) == null : "Duplicate column name: "
            + columnName;

        super.onAddChild( column );

        this.columns.add( column );
        this.columnsByName.put( columnName, column );
    }

    private final List<ISqlColumn> columns;

    private final Map<String, ISqlColumn> columnsByName;

}
