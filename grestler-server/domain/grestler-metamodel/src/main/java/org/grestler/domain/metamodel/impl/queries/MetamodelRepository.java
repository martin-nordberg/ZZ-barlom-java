//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.queries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.grestler.domain.metamodel.api.elements.*;
import org.grestler.domain.metamodel.api.exceptions.MetamodelException;
import org.grestler.domain.metamodel.impl.elements.*;
import org.grestler.domain.metamodel.impl.elements.Package;
import org.grestler.domain.metamodel.spi.queries.IAttributeDeclLoader;
import org.grestler.domain.metamodel.spi.queries.IAttributeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IEdgeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.domain.metamodel.spi.queries.IPackageLoader;
import org.grestler.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.instrumentation.OperationTimeLogger;
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext;
import org.grestler.infrastructure.utilities.revisions.V;
import org.grestler.infrastructure.utilities.revisions.VArray;
import org.grestler.infrastructure.utilities.revisions.VHashMap;

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
                OperationTimeLogger opTimeLogger = new OperationTimeLogger(
                    MetamodelRepository.LOG, "Metamodel repository loaded in {}."
                )
            ) {
                packageLoader.loadAllPackages( this );
                packageDependencyLoader.loadAllPackageDependencies( this );
                attributeTypeLoader.loadAllAttributeTypes( this );
                vertexTypeLoader.loadAllVertexTypes( this );
                edgeTypeLoader.loadAllEdgeTypes( this );
                attributeDeclLoader.loadAllAttributeDecls( this );

                opTimeLogger.noop();
            }

            StmTransactionContext.commitTransaction();
        }
        catch ( Exception e ) {
            StmTransactionContext.abortTransaction( Optional.of( e ) );
            throw e;
        }

    }

    @Override
    public IIndexable<IAttributeType> findAllAttributeTypes() {
        return this.attributeTypes;
    }

    @Override
    public IIndexable<IDirectedEdgeType> findAllDirectedEdgeTypes() {
        return this.directedEdgeTypes;
    }

    @Override
    public IIndexable<IEdgeType> findAllEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public IIndexable<IPackage> findAllPackages() {
        return this.packages;
    }

    @Override
    public IIndexable<IUndirectedEdgeType> findAllUndirectedEdgeTypes() {
        return this.undirectedEdgeTypes;
    }

    @Override
    public IIndexable<IVertexType> findAllVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public IAttributeType findAttributeTypeById( UUID id ) {
        return this.attributeTypesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Attribute type not found: " + id + "."
            )
        );
    }

    @Override
    public IDirectedEdgeType findDirectedEdgeTypeById( UUID id ) {
        return this.directedEdgeTypesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Directed edge type not found: " + id + "."
            )
        );
    }

    @Override
    public IEdgeType findEdgeTypeById( UUID id ) {
        return this.edgeTypesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Edge type not found: " + id + "."
            )
        );
    }

    @Override
    public Optional<IAttributeType> findOptionalAttributeTypeById( UUID id ) {
        return this.attributeTypesById.get( id );
    }

    @Override
    public Optional<IDirectedEdgeType> findOptionalDirectedEdgeTypeById( UUID id ) {
        return this.directedEdgeTypesById.get( id );
    }

    @Override
    public Optional<IEdgeType> findOptionalEdgeTypeById( UUID id ) {
        return this.edgeTypesById.get( id );
    }

    @Override
    public Optional<IPackage> findOptionalPackageById( UUID id ) {
        return this.packagesById.get( id );
    }

    @Override
    public Optional<IUndirectedEdgeType> findOptionalUndirectedEdgeTypeById( UUID id ) {
        return this.undirectedEdgeTypesById.get( id );
    }

    @Override
    public Optional<IVertexType> findOptionalVertexTypeById( UUID id ) {
        return this.vertexTypesById.get( id );
    }

    @Override
    public IPackage findPackageById( UUID id ) {
        return this.packagesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Package not found: " + id + "."
            )
        );
    }

    @Override
    public IDirectedEdgeType findRootDirectedEdgeType() {
        if ( this.rootDirectedEdgeType == null ) {
            throw new MetamodelException( MetamodelRepository.LOG, "Missing root directed edge type." );
        }
        return this.rootDirectedEdgeType.get();
    }

    @Override
    public IPackage findRootPackage() {
        if ( this.rootPackage == null ) {
            throw new MetamodelException( MetamodelRepository.LOG, "Missing root package." );
        }
        return this.rootPackage.get();
    }

    @Override
    public IUndirectedEdgeType findRootUndirectedEdgeType() {
        if ( this.rootUndirectedEdgeType == null ) {
            throw new MetamodelException( MetamodelRepository.LOG, "Missing root undirected egde type." );
        }
        return this.rootUndirectedEdgeType.get();
    }

    @Override
    public IVertexType findRootVertexType() {
        if ( this.rootVertexType == null ) {
            throw new MetamodelException( MetamodelRepository.LOG, "Missing root vertex type." );
        }
        return this.rootVertexType.get();
    }

    @Override
    public IUndirectedEdgeType findUndirectedEdgeTypeById( UUID id ) {
        return this.undirectedEdgeTypesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Undirected edge type not found: " + id + "."
            )
        );
    }

    @Override
    public IVertexType findVertexTypeById( UUID id ) {
        return this.vertexTypesById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Vertex type not found: " + id + "."
            )
        );
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
    public IDirectedEdgeType loadRootDirectedEdgeType( UUID id, IPackage parentPackage ) {

        IDirectedEdgeType result = new RootDirectedEdgeType( id, parentPackage, this.rootVertexType.get() );

        this.edgeTypes.add( result );
        this.edgeTypesById.put( id, result );
        this.directedEdgeTypes.add( result );
        this.directedEdgeTypesById.put( id, result );
        this.rootDirectedEdgeType = new V<>( result );

        return result;

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
    public IUndirectedEdgeType loadRootUndirectedEdgeType(
        UUID id, IPackage parentPackage
    ) {

        IUndirectedEdgeType result = new RootUndirectedEdgeType( id, parentPackage, this.rootVertexType.get() );

        this.edgeTypes.add( result );
        this.edgeTypesById.put( id, result );
        this.undirectedEdgeTypes.add( result );
        this.undirectedEdgeTypesById.put( id, result );
        this.rootUndirectedEdgeType = new V<>( result );

        return result;

    }

    @Override
    public IVertexType loadRootVertexType( UUID id, IPackage parentPackage ) {

        IVertexType result = new RootVertexType( id, parentPackage );

        this.vertexTypes.add( result );
        this.vertexTypesById.put( id, result );
        this.rootVertexType = new V<>( result );

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

    private V<IDirectedEdgeType> rootDirectedEdgeType = null;

    private V<IPackage> rootPackage = null;

    private V<IUndirectedEdgeType> rootUndirectedEdgeType = null;

    private V<IVertexType> rootVertexType = null;

}
