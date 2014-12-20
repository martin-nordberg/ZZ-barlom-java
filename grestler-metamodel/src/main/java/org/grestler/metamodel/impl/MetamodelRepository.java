//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl;

import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.impl.elements.EdgeType;
import org.grestler.metamodel.impl.elements.Package;
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

        this.packages = new VList<>();
        this.vertexTypes = new VList<>();
        this.edgeTypes = new VList<>();

        this.packages.add( IPackage.ROOT_PACKAGE );
        this.vertexTypes.add( IVertexType.BASE_VERTEX_TYPE );
        this.edgeTypes.add( IEdgeType.BASE_EDGE_TYPE );

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
    public List<IEdgeType> findEdgeTypesAll() {
        return this.edgeTypes.get();
    }

    @Override
    public Optional<IPackage> findPackageById( UUID id ) {

        // Search for the vertex type with given UUID. -- TODO: may be worth map by ID
        for ( IPackage p : this.packages.get() ) {
            if ( p.getId().equals( id ) ) {
                return Optional.of( p );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IPackage> findPackagesAll() {
        return this.packages.get();
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
    public List<IVertexType> findVertexTypesAll() {
        return this.vertexTypes.get();
    }

    @Override
    public IEdgeType loadEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    ) {

        IEdgeType result = new EdgeType( id, parentPackage, name, superType, fromVertexType, toVertexType );

        this.edgeTypes.add( result );

        return result;

    }

    @Override
    public IPackage loadPackage( UUID id, IPackage parentPackage, String name ) {

        IPackage result = new Package( id, parentPackage, name );

        this.packages.add( result );

        return result;

    }

    @Override
    public IVertexType loadVertexType( UUID id, IPackage parentPackage, String name, IVertexType superType ) {

        IVertexType result = new VertexType( id, parentPackage, name, superType );

        this.vertexTypes.add( result );

        return result;

    }

    private final VList<IEdgeType> edgeTypes;

    private final VList<IPackage> packages;

    private final VList<IVertexType> vertexTypes;

}
