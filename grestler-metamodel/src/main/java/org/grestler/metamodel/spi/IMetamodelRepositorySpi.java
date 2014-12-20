//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi;

import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.UUID;

/**
 * Interface to a metamodel repository including SPI methods..
 */
public interface IMetamodelRepositorySpi
    extends IMetamodelRepository {

    /**
     * Loads a queried edge type into the repository.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the parent package for the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type of the edge type.
     * @param fromVertexType the vertex type at the start of edges of the new edge type.
     * @param toVertexType   the vertex type at the end of edges of the new edge type.
     *
     * @return the loaded edge type.
     */
    IEdgeType loadEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    );

    /**
     * Loads a queried package into the repository.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package for the package.
     * @param name          the name of the package.
     *
     * @return the loaded package.
     */
    IPackage loadPackage( UUID id, IPackage parentPackage, String name );

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the parent package for the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type of the vertex type.
     *
     * @return the loaded vertex type.
     */
    IVertexType loadVertexType( UUID id, IPackage parentPackage, String name, IVertexType superType );

}
