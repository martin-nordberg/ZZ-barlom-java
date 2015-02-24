//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.metamodel.api.elements.*;
import org.grestler.metamodel.impl.elements.*;
import org.grestler.metamodel.impl.elements.Package;
import org.grestler.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.metamodel.spi.queries.IAttributeTypeLoader;
import org.grestler.metamodel.spi.queries.IEdgeTypeLoader;
import org.grestler.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.metamodel.spi.queries.IPackageLoader;
import org.grestler.metamodel.spi.queries.IVertexTypeLoader;
import org.grestler.utilities.collections.IIndexable;
import org.grestler.utilities.instrumentation.OperationTimeLogger;
import org.grestler.utilities.revisions.StmTransactionContext;
import org.grestler.utilities.revisions.V;
import org.grestler.utilities.revisions.VArray;
import org.grestler.utilities.revisions.VHashMap;

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
            this.directedEdgeTypes = new VArray<>();
            this.undirectedEdgeTypes = new VArray<>();
            this.attributeTypes = new VArray<>();

            this.packagesById = new VHashMap<>( 500 );
            this.vertexTypesById = new VHashMap<>( 500 );
            this.edgeTypesById = new VHashMap<>( 500 );
            this.directedEdgeTypesById = new VHashMap<>( 500 );
            this.undirectedEdgeTypesById = new VHashMap<>( 500 );
            this.attributeTypesById = new VHashMap<>( 500 );

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
        return this.attributeTypesById.get( id );
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
    public Optional<IDirectedEdgeType> findDirectedEdgeTypeById( UUID id ) {
        return this.directedEdgeTypesById.get( id );
    }

    @Override
    public IIndexable<IDirectedEdgeType> findDirectedEdgeTypesAll() {
        return this.directedEdgeTypes;
    }

    @Override
    public Optional<IEdgeType> findEdgeTypeById( UUID id ) {
        return this.edgeTypesById.get( id );
    }

    @Override
    public IIndexable<IEdgeType> findEdgeTypesAll() {
        return this.edgeTypes;
    }

    @Override
    public Optional<IPackage> findPackageById( UUID id ) {
        return this.packagesById.get( id );
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
    public Optional<IUndirectedEdgeType> findUndirectedEdgeTypeById( UUID id ) {
        return this.undirectedEdgeTypesById.get( id );
    }

    @Override
    public IIndexable<IUndirectedEdgeType> findUndirectedEdgeTypesAll() {
        return this.undirectedEdgeTypes;
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
        return this.vertexTypesById.get( id );
    }

    @Override
    public IIndexable<IVertexType> findVertexTypesAll() {
        return this.vertexTypes;
    }

    @Override
    public IDirectedEdgeType loadBaseDirectedEdgeType( UUID id, IPackage parentPackage ) {

        IDirectedEdgeType result = new BaseDirectedEdgeType( id, parentPackage, this.baseVertexType.get() );

        this.edgeTypes.add( result );
        this.edgeTypesById.put( id, result );
        this.directedEdgeTypes.add( result );
        this.directedEdgeTypesById.put( id, result );
        this.baseDirectedEdgeType = new V<>( result );

        return result;

    }

    @Override
    public IUndirectedEdgeType loadBaseUndirectedEdgeType(
        UUID id, IPackage parentPackage
    ) {

        IUndirectedEdgeType result = new BaseUndirectedEdgeType( id, parentPackage, this.baseVertexType.get() );

        this.edgeTypes.add( result );
        this.edgeTypesById.put( id, result );
        this.undirectedEdgeTypes.add( result );
        this.undirectedEdgeTypesById.put( id, result );
        this.baseUndirectedEdgeType = new V<>( result );

        return result;

    }

    @Override
    public IVertexType loadBaseVertexType( UUID id, IPackage parentPackage ) {

        IVertexType result = new BaseVertexType( id, parentPackage );

        this.vertexTypes.add( result );
        this.vertexTypesById.put( id, result );
        this.baseVertexType = new V<>( result );

        return result;

    }

    @Override
    public IBooleanAttributeType loadBooleanAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Boolean> defaultValue
    ) {

        IBooleanAttributeType result = new BooleanAttributeType( id, parentPackage, name, defaultValue );

        this.attributeTypes.add( result );
        this.attributeTypesById.put( id, result );

        return result;

    }

    @Override
    public IDateTimeAttributeType loadDateTimeAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Instant> minValue, Optional<Instant> maxValue
    ) {

        IDateTimeAttributeType result = new DateTimeAttributeType( id, parentPackage, name, minValue, maxValue );

        this.attributeTypes.add( result );
        this.attributeTypesById.put( id, result );

        return result;

    }

    @Override
    public IDirectedEdgeType loadDirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IDirectedEdgeType superType,
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
        this.edgeTypesById.put( id, result );
        this.directedEdgeTypes.add( result );
        this.directedEdgeTypesById.put( id, result );

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
        this.attributeTypesById.put( id, result );

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
        this.attributeTypesById.put( id, result );

        return result;
    }

    @Override
    public IPackage loadPackage( UUID id, IPackage parentPackage, String name ) {

        IPackage result = new Package( id, parentPackage, name );

        this.packages.add( result );
        this.packagesById.put( id, result );

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
        this.packagesById.put( id, result );
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
        this.attributeTypesById.put( id, result );

        return result;
    }

    @Override
    public IUndirectedEdgeType loadUndirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IUndirectedEdgeType superType,
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
        this.edgeTypesById.put( id, result );
        this.undirectedEdgeTypes.add( result );
        this.undirectedEdgeTypesById.put( id, result );

        return result;

    }

    @Override
    public IUuidAttributeType loadUuidAttributeType(
        UUID id, IPackage parentPackage, String name
    ) {
        IUuidAttributeType result = new UuidAttributeType( id, parentPackage, name );

        this.attributeTypes.add( result );
        this.attributeTypesById.put( id, result );

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
        this.vertexTypesById.put( id, result );

        return result;

    }

    private static final Logger LOG = LogManager.getLogger();

    private final VArray<IAttributeType> attributeTypes;

    private final VHashMap<UUID, IAttributeType> attributeTypesById;

    private final VArray<IDirectedEdgeType> directedEdgeTypes;

    private final VHashMap<UUID, IDirectedEdgeType> directedEdgeTypesById;

    private final VArray<IEdgeType> edgeTypes;

    private final VHashMap<UUID, IEdgeType> edgeTypesById;

    private final VArray<IPackage> packages;

    private final VHashMap<UUID, IPackage> packagesById;

    private final VArray<IUndirectedEdgeType> undirectedEdgeTypes;

    private final VHashMap<UUID, IUndirectedEdgeType> undirectedEdgeTypesById;

    private final VArray<IVertexType> vertexTypes;

    private final VHashMap<UUID, IVertexType> vertexTypesById;

    private V<IDirectedEdgeType> baseDirectedEdgeType = null;

    private V<IUndirectedEdgeType> baseUndirectedEdgeType = null;

    private V<IVertexType> baseVertexType = null;

    private V<IPackage> rootPackage = null;

}
