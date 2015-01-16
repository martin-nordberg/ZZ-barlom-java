//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl;

import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.attributes.IBooleanAttributeType;
import org.grestler.metamodel.api.attributes.IDateTimeAttributeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.impl.attributes.BooleanAttributeType;
import org.grestler.metamodel.impl.attributes.DateTimeAttributeType;
import org.grestler.metamodel.impl.elements.EdgeType;
import org.grestler.metamodel.impl.elements.Package;
import org.grestler.metamodel.impl.elements.VertexType;
import org.grestler.metamodel.spi.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.metamodel.spi.elements.IPackageLoader;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;

import javax.inject.Inject;
import java.util.*;

/**
 * The main metamodel repository.
 */
public class MetamodelRepository
    implements IMetamodelRepositorySpi {

    /**
     * Constructs a new metamodel repository. Loads it with packages, attribute types, vertex types, and edge types from
     * the given sources.
     *
     * @param packageLoader       the loader used to initialize the packages into the metamodel repository.
     * @param attributeTypeLoader the loader used to initialize the attribute types into the metamodel repository.
     * @param vertexTypeLoader    the loader used to initialize the vertex types into the metamodel repository.
     * @param edgeTypeLoader      the loader used to initialize the edge types into the metamodel repository.
     */
    @Inject
    public MetamodelRepository(
        IPackageLoader packageLoader,
        IAttributeTypeLoader attributeTypeLoader,
        IVertexTypeLoader vertexTypeLoader,
        IEdgeTypeLoader edgeTypeLoader
    ) {

        this.packages = new ArrayList<>();
        this.vertexTypes = new ArrayList<>();
        this.edgeTypes = new ArrayList<>();
        this.attributeTypes = new ArrayList<>();

        this.packages.add( IPackage.ROOT_PACKAGE );
        this.vertexTypes.add( IVertexType.BASE_VERTEX_TYPE );
        this.edgeTypes.add( IEdgeType.BASE_EDGE_TYPE );

        packageLoader.loadAllPackages( this );
        attributeTypeLoader.loadAllAttributeTypes( this );
        vertexTypeLoader.loadAllVertexTypes( this );
        edgeTypeLoader.loadAllEdgeTypes( this );

    }

    @Override
    public Optional<IAttributeType> findAttributeTypeById( UUID id ) {

        // Search for the edge type with given UUID. -- TODO: may be worth map by ID
        for ( IAttributeType e : this.attributeTypes ) {
            if ( e.getId().equals( id ) ) {
                return Optional.of( e );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IAttributeType> findAttributeTypesAll() {
        return this.attributeTypes;
    }

    @Override
    public Optional<IEdgeType> findEdgeTypeById( UUID id ) {

        // Search for the edge type with given UUID. -- TODO: may be worth map by ID
        for ( IEdgeType e : this.edgeTypes ) {
            if ( e.getId().equals( id ) ) {
                return Optional.of( e );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IEdgeType> findEdgeTypesAll() {
        return this.edgeTypes;
    }

    @Override
    public Optional<IPackage> findPackageById( UUID id ) {

        // Search for the vertex type with given UUID. -- TODO: may be worth map by ID
        for ( IPackage p : this.packages ) {
            if ( p.getId().equals( id ) ) {
                return Optional.of( p );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IPackage> findPackagesAll() {
        return this.packages;
    }

    @Override
    public Optional<IVertexType> findVertexTypeById( UUID id ) {

        // Search for the vertex type with given UUID. -- TODO: may be worth map by ID
        for ( IVertexType v : this.vertexTypes ) {
            if ( v.getId().equals( id ) ) {
                return Optional.of( v );
            }
        }

        return Optional.empty();

    }

    @Override
    public List<IVertexType> findVertexTypesAll() {
        return this.vertexTypes;
    }

    @Override
    public IBooleanAttributeType loadBooleanAttributeType(
        UUID id, IPackage parentPackage, String name
    ) {

        IBooleanAttributeType result = new BooleanAttributeType( id, parentPackage, name );

        this.attributeTypes.add( result );

        return result;

    }

    @Override
    public IDateTimeAttributeType loadDateTimeAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Date> minValue, Optional<Date> maxValue
    ) {

        IDateTimeAttributeType result = new DateTimeAttributeType( id, parentPackage, name, minValue, maxValue );

        this.attributeTypes.add( result );

        return result;

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

    private final List<IAttributeType> attributeTypes;

    private final List<IEdgeType> edgeTypes;

    private final List<IPackage> packages;

    private final List<IVertexType> vertexTypes;

}
