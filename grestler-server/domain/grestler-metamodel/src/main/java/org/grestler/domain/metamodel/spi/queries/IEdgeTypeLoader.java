//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.queries;

/**
 * Interface defining edge type queries.
 */
@FunctionalInterface
public interface IEdgeTypeLoader {

    /**
     * Loads all edge types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllEdgeTypes( IMetamodelRepositorySpi repository );

}
