//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.List;
import java.util.Optional;

/**
 * A full schema of tables, views, etc.
 */
public interface ISqlSchema
    extends ISqlNamedModelElement {

    /**
     * Creates a new domain within this schema.
     */
    ISqlDomain addDomain(
        String name,
        String description,
        boolean hasSqlCustomizations
    );

    /** Returns the domain within this schema with given name. */
    ISqlDomain getDomainByName( String name );

    /** Returns the domains within this schema. */
    List<ISqlDomain> getDomains();

    /** Returns the table or view within this schema with given name. */
    Optional<ISqlRelation> getRelationByName( String name );

    /** Returns the table or view within this schema with given name. */
    Optional<ISqlTable> getTableByName( String name );

}
