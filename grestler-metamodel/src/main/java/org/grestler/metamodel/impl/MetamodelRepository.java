//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.impl.elements.EdgeType;
import org.grestler.metamodel.impl.elements.VertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.utilities.revisions.VList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The main metamodel repository.
 */
public class MetamodelRepository
    implements IMetamodelRepositorySpi {

    /**
     * Constructs a new metamodel repository.
     */
    public MetamodelRepository() {
        this.vertexTypes = new VList<>();
        this.edgeTypes = new VList<>();
    }

    @Override
    public Optional<IEdgeType> findEdgeTypeById( UUID id ) {

        // Search for the edge type with given UUID. -- TODO: may be worth map by ID
        for ( IEdgeType e : this.edgeTypes.get() ) {
            if ( e.getId().equals( id ) ) {
                return Optional.of( e );
            }
        }

        return Optional.empty();

    }

    @Override
    public Optional<IEdgeType> findEdgeTypeByName( String name ) {

        // Search for the edge type with given UUID. -- TODO: may be worth map by name
        for ( IEdgeType e : this.edgeTypes.get() ) {
            if ( e.getName().equals( name ) ) {
                return Optional.of( e );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IEdgeType> findEdgeTypesAll() {
        return this.edgeTypes.get();
    }

    @Override
    public Optional<IVertexType> findVertexTypeById( UUID id ) {

        // Search for the vertex type with given UUID. -- TODO: may be worth map by ID
        for ( IVertexType v : this.vertexTypes.get() ) {
            if ( v.getId().equals( id ) ) {
                return Optional.of( v );
            }
        }

        return Optional.empty();

    }

    @Override
    public Optional<IVertexType> findVertexTypeByName( String name ) {

        // Search for the vertex type with given UUID. -- TODO: may be worth map by name
        for ( IVertexType v : this.vertexTypes.get() ) {
            if ( v.getName().equals( name ) ) {
                return Optional.of( v );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IVertexType> findVertexTypesAll() {
        return this.vertexTypes.get();
    }

    @Override
    public IEdgeType loadEdgeType(
        UUID id,
        String name,
        Optional<IEdgeType> superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    ) {

        IEdgeType result = new EdgeType( id, name, superType, fromVertexType, toVertexType );

        this.edgeTypes.add( result );

        return result;

    }

    @Override
    public IVertexType loadVertexType( UUID id, String name, Optional<IVertexType> superType ) {

        IVertexType result = new VertexType( id, name, superType );

        this.vertexTypes.add( result );

        return result;

    }

    private final VList<IEdgeType> edgeTypes;

    private final VList<IVertexType> vertexTypes;

}
