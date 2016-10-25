//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlDataModel;
import org.barlom.domain.sqlmodel.api.elements.ISqlRelation;
import org.barlom.domain.sqlmodel.api.elements.ISqlSchema;
import org.barlom.domain.sqlmodel.api.elements.ISqlTable;
import org.barlom.domain.sqlmodel.api.elements.ISqlView;
import org.barlom.infrastructure.utilities.collections.IIndexable;
import org.barlom.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A full schema of tables, views, etc.
 */
public class SqlSchema
    extends SqlNamedModelElement
    implements ISqlSchema {

    /**
     * Constructs a new schema.
     */
    SqlSchema( SqlDataModel parent, String name, String description ) {
        super( parent, name, description );

        this.relationsByName = new HashMap<>();
        this.tables = new ArrayList<>();
        this.views = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public ISqlTable addTable( String name, String description ) {
        return new SqlTable( this, name, description );
    }

    @Override
    public ISqlView addView( String name, String description ) {
        return new SqlView( this, name, description );
    }

    @Override
    public ISqlDataModel getParent() {
        return (ISqlDataModel) super.getParent();
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String getPathWithSchema() {
        return this.getSqlName();
    }

    @Override
    public Optional<ISqlRelation> getRelationByName( String name ) {
        return Optional.ofNullable( this.relationsByName.get( SqlNamedModelElement.makeSqlName( name ) ) );
    }

    @Override
    public SqlSchema getSchema() {
        return this;
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( this.getName() );
    }

    @Override
    public Optional<ISqlTable> getTableByName( String name ) {
        Optional<ISqlRelation> result = this.getRelationByName( name );

        if ( result.isPresent() && result.get() instanceof ISqlTable ) {
            return Optional.of( (ISqlTable) result.get() );
        }

        return Optional.empty();
    }

    @Override
    public IIndexable<ISqlTable> getTables() {
        return new ReadOnlyListAdapter<>( this.tables );
    }

    @Override
    public IIndexable<ISqlView> getViews() {
        return new ReadOnlyListAdapter<>( this.views );
    }

    /** Registers the addition of a table to this schema. */
    void onAddChild( ISqlTable table ) {

        assert this.getRelationByName( table.getSqlName() ) == null : "Duplicate table/view name: "
            + table.getSqlName();

        super.onAddChild( table );

        this.relationsByName.put( table.getSqlName(), table );
        this.tables.add( table );

    }

    /** Registers the addition of a view to this schema. */
    void onAddChild( ISqlView view ) {

        assert this.getRelationByName( view.getSqlName() ) == null : "Duplicate table/view name: "
            + view.getSqlName();

        super.onAddChild( view );

        this.relationsByName.put( view.getSqlName(), view );
        this.views.add( view );

    }

    private final Map<String, ISqlRelation> relationsByName;

    private final List<ISqlTable> tables;

    private final List<ISqlView> views;

}
