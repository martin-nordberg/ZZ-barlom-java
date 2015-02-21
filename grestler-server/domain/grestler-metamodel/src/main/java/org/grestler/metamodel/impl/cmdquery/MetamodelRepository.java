//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.cmdquery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.attributes.IBooleanAttributeType;
import org.grestler.metamodel.api.attributes.IDateTimeAttributeType;
import org.grestler.metamodel.api.attributes.IFloat64AttributeType;
import org.grestler.metamodel.api.attributes.IInteger32AttributeType;
import org.grestler.metamodel.api.attributes.IStringAttributeType;
import org.grestler.metamodel.api.attributes.IUuidAttributeType;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.ELabelDefaulting;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;
import org.grestler.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.metamodel.impl.attributes.BooleanAttributeType;
import org.grestler.metamodel.impl.attributes.DateTimeAttributeType;
import org.grestler.metamodel.impl.attributes.Float64AttributeType;
import org.grestler.metamodel.impl.attributes.Integer32AttributeType;
import org.grestler.metamodel.impl.attributes.StringAttributeType;
import org.grestler.metamodel.impl.attributes.UuidAttributeType;
import org.grestler.metamodel.impl.elements.BaseDirectedEdgeType;
import org.grestler.metamodel.impl.elements.BaseUndirectedEdgeType;
import org.grestler.metamodel.impl.elements.BaseVertexType;
import org.grestler.metamodel.impl.elements.DirectedEdgeType;
import org.grestler.metamodel.impl.elements.EdgeAttributeDecl;
import org.grestler.metamodel.impl.elements.Package;
import org.grestler.metamodel.impl.elements.PackageDependency;
import org.grestler.metamodel.impl.elements.RootPackage;
import org.grestler.metamodel.impl.elements.UndirectedEdgeType;
import org.grestler.metamodel.impl.elements.VertexAttributeDecl;
import org.grestler.metamodel.impl.elements.VertexType;
import org.grestler.metamodel.spi.attributes.IAttributeTypeLoader;
import org.grestler.metamodel.spi.cmdquery.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.elements.IAttributeDeclLoader;
import org.grestler.metamodel.spi.elements.IEdgeTypeLoader;
import org.grestler.metamodel.spi.elements.IPackageDependencyLoader;
import org.grestler.metamodel.spi.elements.IPackageLoader;
import org.grestler.metamodel.spi.elements.IVertexTypeLoader;
import org.grestler.utilities.collections.IIndexable;
import org.grestler.utilities.instrumentation.OperationTimeLogger;
import org.grestler.utilities.revisions.StmTransactionContext;
import org.grestler.utilities.revisions.V;
import org.grestler.utilities.revisions.VArray;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * The main metamodel repository.
 */
public final class MetamodelRepository
    implements IMetamodelRepositorySpi {

    /**
     * Constructs a new metamodel repository. Loads it with packages, attribute types, vertex types, and edge types from
     * the given sources.
     *
     * @param packageLoader       the loader used to initialize the packages into the metamodel repository.
     * @param attributeTypeLoader the loader used to initialize the attribute types into the metamodel repository.
     * @param vertexTypeLoader    the loader used to initialize the vertex types into the metamodel repository.
     * @param edgeTypeLoader      the loader used to initialize the edge types into the metamodel repository.
     * @param attributeDeclLoader the loader used to initialize attribute declarations into the metamodel repository.
     */
    @Inject
    public MetamodelRepository(
        IPackageLoader packageLoader,
        IPackageDependencyLoader packageDependencyLoader,
        IAttributeTypeLoader attributeTypeLoader,
        IVertexTypeLoader vertexTypeLoader,
        IEdgeTypeLoader edgeTypeLoader,
        IAttributeDeclLoader attributeDeclLoader
    ) {

        try {
            StmTransactionContext.beginReadWriteTransaction();

            this.packages = new VArray<>();
            this.vertexTypes = new VArray<>();
            this.edgeTypes = new VArray<>();
            this.attributeTypes = new VArray<>();

            try (
                OperationTimeLogger ignored = new OperationTimeLogger(
                    MetamodelRepository.LOG, "Metamodel repository loaded in {}."
                )
            ) {
                packageLoader.loadAllPackages( this );
                packageDependencyLoader.loadAllPackageDependencies( this );
                attributeTypeLoader.loadAllAttributeTypes( this );
                vertexTypeLoader.loadAllVertexTypes( this );
                edgeTypeLoader.loadAllEdgeTypes( this );
                attributeDeclLoader.loadAllAttributeDecls( this );
            }

            StmTransactionContext.commitTransaction();
        }
        catch ( Exception e ) {
            StmTransactionContext.abortTransaction( Optional.of( e ) );
            throw e;
        }

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
    public IIndexable<IAttributeType> findAttributeTypesAll() {
        return this.attributeTypes;
    }

    @Override
    public Optional<IDirectedEdgeType> findDirectedEdgeTypeBase() {
        if ( this.baseDirectedEdgeType == null ) {
            return Optional.empty();
        }
        return Optional.of( this.baseDirectedEdgeType.get() );
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
    public IIndexable<IEdgeType> findEdgeTypesAll() {
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
    public Optional<IPackage> findPackageRoot() {
        if ( this.rootPackage == null ) {
            return Optional.empty();
        }
        return Optional.of( this.rootPackage.get() );
    }

    @Override
    public IIndexable<IPackage> findPackagesAll() {
        return this.packages;
    }

    @Override
    public Optional<IUndirectedEdgeType> findUndirectedEdgeTypeBase() {
        if ( this.baseUndirectedEdgeType == null ) {
            return Optional.empty();
        }
        return Optional.of( this.baseUndirectedEdgeType.get() );
    }

    @Override
    public Optional<IVertexType> findVertexTypeBase() {
        if ( this.baseVertexType == null ) {
            return Optional.empty();
        }
        return Optional.of( this.baseVertexType.get() );
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
    public IIndexable<IVertexType> findVertexTypesAll() {
        return this.vertexTypes;
    }

    @Override
    public IDirectedEdgeType loadBaseDirectedEdgeType( UUID id, IPackage parentPackage ) {

        IDirectedEdgeType result = new BaseDirectedEdgeType( id, parentPackage, this.baseVertexType.get() );

        this.edgeTypes.add( result );
        this.baseDirectedEdgeType = new V<>( result );

        return result;

    }

    @Override
    public IUndirectedEdgeType loadBaseUndirectedEdgeType(
        UUID id, IPackage parentPackage
    ) {

        IUndirectedEdgeType result = new BaseUndirectedEdgeType( id, parentPackage, this.baseVertexType.get() );

        this.edgeTypes.add( result );
        this.baseUndirectedEdgeType = new V<>( result );

        return result;

    }

    @Override
    public IVertexType loadBaseVertexType( UUID id, IPackage parentPackage ) {

        IVertexType result = new BaseVertexType( id, parentPackage );

        this.vertexTypes.add( result );
        this.baseVertexType = new V<>( result );

        return result;

    }

    @Override
    public IBooleanAttributeType loadBooleanAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Boolean> defaultValue
    ) {

        IBooleanAttributeType result = new BooleanAttributeType( id, parentPackage, name, defaultValue );

        this.attributeTypes.add( result );

        return result;

    }

    @Override
    public IDateTimeAttributeType loadDateTimeAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Instant> minValue, Optional<Instant> maxValue
    ) {

        IDateTimeAttributeType result = new DateTimeAttributeType( id, parentPackage, name, minValue, maxValue );

        this.attributeTypes.add( result );

        return result;

    }

    @Override
    public IDirectedEdgeType loadDirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType tailVertexType,
        IVertexType headVertexType,
        Optional<String> tailRoleName,
        Optional<String> headRoleName,
        OptionalInt minTailOutDegree,
        OptionalInt maxTailOutDegree,
        OptionalInt minHeadInDegree,
        OptionalInt maxHeadInDegree
    ) {

        IDirectedEdgeType result = new DirectedEdgeType(
            id,
            parentPackage,
            name,
            superType,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping,
            tailVertexType,
            headVertexType,
            tailRoleName,
            headRoleName,
            minTailOutDegree,
            maxTailOutDegree,
            minHeadInDegree,
            maxHeadInDegree
        );

        this.edgeTypes.add( result );

        return result;

    }

    @Override
    public IEdgeAttributeDecl loadEdgeAttributeDecl(
        UUID id, IEdgeType parentEdgeType, String name, IAttributeType type, EAttributeOptionality optionality
    ) {
        return new EdgeAttributeDecl( id, parentEdgeType, name, type, optionality );
    }

    @Override
    public IFloat64AttributeType loadFloat64AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalDouble minValue,
        OptionalDouble maxValue,
        OptionalDouble defaultValue
    ) {
        IFloat64AttributeType result = new Float64AttributeType(
            id, parentPackage, name, minValue, maxValue, defaultValue
        );

        this.attributeTypes.add( result );

        return result;
    }

    @Override
    public IInteger32AttributeType loadInteger32AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minValue,
        OptionalInt maxValue,
        OptionalInt defaultValue
    ) {
        IInteger32AttributeType result = new Integer32AttributeType(
            id, parentPackage, name, minValue, maxValue, defaultValue
        );

        this.attributeTypes.add( result );

        return result;
    }

    @Override
    public IPackage loadPackage( UUID id, IPackage parentPackage, String name ) {

        IPackage result = new Package( id, parentPackage, name );

        this.packages.add( result );

        return result;

    }

    @Override
    public IPackageDependency loadPackageDependency(
        UUID id, IPackage clientPackage, IPackage supplierPackage
    ) {
        return new PackageDependency( id, clientPackage, supplierPackage );
    }

    @Override
    public IPackage loadRootPackage( UUID id ) {

        IPackage result = new RootPackage( id );

        this.packages.add( result );
        this.rootPackage = new V<>( result );

        return result;

    }

    @Override
    public IStringAttributeType loadStringAttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minLength,
        int maxLength,
        Optional<String> regexPattern
    ) {
        IStringAttributeType result = new StringAttributeType(
            id, parentPackage, name, minLength, maxLength, regexPattern
        );

        this.attributeTypes.add( result );

        return result;
    }

    @Override
    public IUndirectedEdgeType loadUndirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType vertexType,
        OptionalInt minDegree,
        OptionalInt maxDegree
    ) {

        IUndirectedEdgeType result = new UndirectedEdgeType(
            id,
            parentPackage,
            name,
            superType,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping,
            vertexType,
            minDegree,
            maxDegree
        );

        this.edgeTypes.add( result );

        return result;

    }

    @Override
    public IUuidAttributeType loadUuidAttributeType(
        UUID id, IPackage parentPackage, String name
    ) {
        IUuidAttributeType result = new UuidAttributeType( id, parentPackage, name );

        this.attributeTypes.add( result );

        return result;
    }

    @Override
    public IVertexAttributeDecl loadVertexAttributeDecl(
        UUID id,
        IVertexType parentVertexType,
        String name,
        IAttributeType type,
        EAttributeOptionality optionality,
        ELabelDefaulting labelDefaulting
    ) {
        return new VertexAttributeDecl( id, parentVertexType, name, type, optionality, labelDefaulting );
    }

    @Override
    public IVertexType loadVertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType, EAbstractness abstractness
    ) {

        IVertexType result = new VertexType( id, parentPackage, name, superType, abstractness );

        this.vertexTypes.add( result );

        return result;

    }

    private static final Logger LOG = LogManager.getLogger();

    private final VArray<IAttributeType> attributeTypes;

    private final VArray<IEdgeType> edgeTypes;

    private final VArray<IPackage> packages;

    private final VArray<IVertexType> vertexTypes;

    private V<IDirectedEdgeType> baseDirectedEdgeType = null;

    private V<IUndirectedEdgeType> baseUndirectedEdgeType = null;

    private V<IVertexType> baseVertexType = null;

    private V<IPackage> rootPackage = null;

}
