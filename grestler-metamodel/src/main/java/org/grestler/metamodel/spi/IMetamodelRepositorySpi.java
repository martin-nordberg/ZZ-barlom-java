//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi;

import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface to a metamodel repository including SPI methods..
 */
public interface IMetamodelRepositorySpi
  extends IMetamodelRepository {

    /**
     * Loads a queries vertex type into the repository.
     * @param id the unique ID of the vertex type.
     * @param name the name of the vertex type.
     * @param superType the super type of the vertex type.
     * @return the loaded vertex type.
     */
    IVertexType loadVertexType( UUID id, String name, Optional<IVertexType> superType );

}
