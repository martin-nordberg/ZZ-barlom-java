//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi.elements;

import org.grestler.metamodel.spi.IMetamodelRepositorySpi;

/**
 * Interface defining vertex type queries.
 */
public interface IVertexTypeLoader {

    /**
     * Loads all vertex types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    void loadAllVertexTypes( IMetamodelRepositorySpi repository );

}
