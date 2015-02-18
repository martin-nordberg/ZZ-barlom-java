//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi.elements;

import org.grestler.metamodel.spi.cmdquery.IMetamodelRepositorySpi;

/**
 * Interface defining package dependency queries.
 */
@FunctionalInterface
public interface IPackageDependencyLoader {

    /**
     * Loads all package dependencies into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllPackageDependencies( IMetamodelRepositorySpi repository );

}
