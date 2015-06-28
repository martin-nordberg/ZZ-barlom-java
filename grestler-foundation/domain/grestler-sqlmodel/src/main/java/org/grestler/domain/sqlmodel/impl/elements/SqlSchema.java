//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.impl.elements;

import org.grestler.domain.sqlmodel.api.elements.ISqlDomain;
import org.grestler.domain.sqlmodel.api.elements.ISqlRelation;
import org.grestler.domain.sqlmodel.api.elements.ISqlSchema;
import org.grestler.domain.sqlmodel.api.elements.ISqlTable;

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

        this.domainsByName = new HashMap<>();
        this.domains = new ArrayList<>();
        this.relationsByName = new HashMap<>();
    }

    /**
     * Creates a new domain within this schema.
     */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public ISqlDomain addDomain(
        String name,
        String description,
        boolean hasSqlCustomizations
    ) {
        return new SqlDomain( this, name, description, hasSqlCustomizations );
    }

    @Override
    public ISqlDomain getDomainByName( String name ) {
        return this.domainsByName.get( name );
    }

    @Override
    public List<ISqlDomain> getDomains() {
        return this.domains;
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

    /** Register the addition of a domain to this schema. */
    void onAddChild( ISqlDomain domain ) {
        String domainName = domain.getName();

        assert this.domainsByName.get( domainName ) == null : "Duplicate domain name: "
            + domainName;

        super.onAddChild( domain );

        this.domainsByName.put( domainName, domain );
        this.domains.add( domain );
    }

    /** Sets the table or view within this schema with given name. */
    void putRelationByName( String name, ISqlRelation relation ) {
        this.relationsByName.put( SqlNamedModelElement.makeSqlName( name ), relation );
    }

    private final List<ISqlDomain> domains;

    private final Map<String, ISqlDomain> domainsByName;

    private final Map<String, ISqlRelation> relationsByName;

}
