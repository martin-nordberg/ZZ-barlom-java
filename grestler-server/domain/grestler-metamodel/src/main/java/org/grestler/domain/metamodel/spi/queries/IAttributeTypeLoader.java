//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.queries;

/**
 * Interface defining attribute type queries.
 */
@FunctionalInterface
public interface IAttributeTypeLoader {

    /**
     * Loads all attribute types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllAttributeTypes( IMetamodelRepositorySpi repository );

}
