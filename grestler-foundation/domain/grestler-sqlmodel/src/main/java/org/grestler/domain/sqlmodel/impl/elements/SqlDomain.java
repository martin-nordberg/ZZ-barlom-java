//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlDomain;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;
import org.grestler.domain.sqlmodel.api.elements.ISqlView;

import java.util.ArrayList;
import java.util.List;

/**
 * A domain is a related subset of a schema.
 */
public class SqlDomain
    extends SqlNamedModelElement
    implements ISqlDomain {

    /**
     * Constructs a new schema.
     */
    SqlDomain(
        SqlSchema parent,
        String name,
        String description,
        boolean hasSqlCustomizations
    ) {
        super( parent, name, description );

        this.tables = new ArrayList<>();
        this.views = new ArrayList<>();
        this.hasSqlCustomizations = hasSqlCustomizations;

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

    /**
     * Returns the modelDomain.
     */
    @Override
    public SqlSchema getParent() {
        return (SqlSchema) super.getParent();
    }

    @Override
    public String getSqlName() {
        return SqlNamedModelElement.makeSqlName( this.getName() );
    }

    @Override
    public List<ISqlTable> getTables() {
        return this.tables;
    }

    @Override
    public List<ISqlView> getViews() {
        return this.views;
    }

    @Override
    public boolean hasSqlCustomizations() {
        return this.hasSqlCustomizations;
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

    private boolean hasSqlCustomizations = false;

    private final List<ISqlTable> tables;

    private final List<ISqlView> views;
}
