//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi;

import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a metamodel repository including SPI methods..
 */
public interface IMetamodelRepositorySpi
    extends IMetamodelRepository {

    /**
     * Loads a queried edge type into the repository.
     *
     * @param id        the unique ID of the edge type.
     * @param name      the name of the edge type.
     * @param superType the super type of the edge type.
     *
     * @return the loaded edge type.
     */
    IEdgeType loadEdgeType(
        UUID id,
        String name,
        Optional<IEdgeType> superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    );

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param id        the unique ID of the vertex type.
     * @param name      the name of the vertex type.
     * @param superType the super type of the vertex type.
     *
     * @return the loaded vertex type.
     */
    IVertexType loadVertexType( UUID id, String name, Optional<IVertexType> superType );

}
