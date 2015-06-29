//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlRelation;
import org.grestler.domain.sqlmodel.api.elements.ISqlSchema;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;
import org.grestler.domain.sqlmodel.api.elements.ISqlView;

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
    public SqlSchema( String name, String description ) {
        super( null, name, description );

        this.relationsByName = new HashMap<>();
        this.tables = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    @Override
    public ISqlTable addTable( String name, String description ) {
        return new SqlTable( this, name, description );
    }

    @Override
    public ISqlView addView( String name, String description ) {
        return new SqlView( this, name, description );
    }

    /**
     * Returns the modelDomain.
     */
    @Override
    public SqlSchema getParent() {
        return (SqlSchema) super.getParent();
    }

    /** Returns the table or view within this schema with given name. */
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

    /** Returns the table or view within this schema with given name. */
    @Override
    public Optional<ISqlTable> getTableByName( String name ) {
        Optional<ISqlRelation> result = this.getRelationByName( name );

        if ( result.isPresent() && result.get() instanceof ISqlTable ) {
            return Optional.of( (ISqlTable) result.get() );
        }

        return Optional.empty();
    }

    @Override
    public List<ISqlTable> getTables() {
        return this.tables;
    }

    @Override
    public List<ISqlView> getViews() {
        return this.views;
    }

    /** Registers the addition of a table to this domain. */
    void onAddChild( ISqlTable table ) {
        String tableName = table.getSqlName();

        assert this.getParent().getRelationByName( tableName ) == null : "Duplicate table/view name: "
            + tableName;

        super.onAddChild( table );

        this.getParent().putRelationByName( tableName, table );
        this.tables.add( table );
    }

    /** Registers the addition of a view to this domain. */
    void onAddChild( ISqlView view ) {
        String viewName = view.getSqlName();

        assert this.getParent().getRelationByName( viewName ) == null : "Duplicate table/view name: "
            + viewName;

        super.onAddChild( view );

        this.getParent().putRelationByName( viewName, view );
        this.views.add( view );
    }

    /** Sets the table or view within this schema with given name. */
    void putRelationByName( String name, ISqlRelation relation ) {
        this.relationsByName.put( SqlNamedModelElement.makeSqlName( name ), relation );
    }

    private final Map<String, ISqlRelation> relationsByName;

    private final List<ISqlTable> tables;

    private final List<ISqlView> views;

}
