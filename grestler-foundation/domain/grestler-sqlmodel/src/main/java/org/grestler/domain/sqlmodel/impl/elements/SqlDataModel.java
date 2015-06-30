package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlDataModel;
import org.grestler.domain.sqlmodel.api.elements.ISqlNamedModelElement;
import org.grestler.domain.sqlmodel.api.elements.ISqlSchema;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of a top level data model.
 */
public class SqlDataModel
    extends SqlNamedModelElement
    implements ISqlDataModel {

    public SqlDataModel() {
        super( null, "", "" );
        this.schemas = new ArrayList<>();
        this.schemasByName = new HashMap<>();
    }

    @Override
    public ISqlSchema addSchema( final String name, final String description ) {
        return new SqlSchema( this, name, description );
    }

    @Override
    public String getDescription() {
        return "[Top level data model]";
    }

    @Override
    public ISqlNamedModelElement getParent() {
        throw new UnsupportedOperationException( "SQL data model has no parent." );
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException( "SQL data model has no schema." );
    }

    @Override
    public String getPathWithSchema() {
        throw new UnsupportedOperationException( "SQL data model has no schema." );
    }

    @Override
    public ISqlSchema getSchema() {
        throw new UnsupportedOperationException( "SQL data model has no schema." );
    }

    @Override
    public Optional<ISqlSchema> getSchemaByName( final String name ) {
        return Optional.ofNullable( this.schemasByName.get( name ) );
    }

    @Override
    public IIndexable<ISqlSchema> getSchemas() {
        return new ReadOnlyListAdapter<>( this.schemas );
    }

    @Override
    public String getSqlName() {
        return "";
    }

    void onAddChild( ISqlSchema schema ) {

        String schemaName = schema.getSqlName();

        assert this.schemasByName.get( schemaName ) == null : "Duplicate schema name: " + schemaName;

        super.onAddChild( schema );

        this.schemas.add( schema );
        this.schemasByName.put( schemaName, schema );

    }

    private final List<ISqlSchema> schemas;

    private final Map<String, ISqlSchema> schemasByName;


}
