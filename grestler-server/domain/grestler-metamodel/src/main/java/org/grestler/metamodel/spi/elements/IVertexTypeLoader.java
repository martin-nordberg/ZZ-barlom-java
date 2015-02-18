//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi.elements;

import org.grestler.metamodel.spi.cmdquery.IMetamodelRepositorySpi;

/**
 * Interface defining vertex type queries.
 */
@FunctionalInterface
public interface IVertexTypeLoader {

    /**
     * Loads all vertex types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllVertexTypes( IMetamodelRepositorySpi repository );

}
