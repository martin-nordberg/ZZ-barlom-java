//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.spi.queries;

/**
 * Interface for a back end attribute declaration loader.
 */
@FunctionalInterface
public interface IAttributeDeclLoader {

    /**
     * Loads all attribute declarations into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllAttributeDecls( IMetamodelRepositorySpi repository );

}
