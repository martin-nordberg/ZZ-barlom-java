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
import org.grestler.domain.metamodel.spi.queries.IDirectedEdgeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IMetamodelRepositorySpi;
import org.grestler.domain.metamodel.spi.queries.IPackageDependencyLoader;
import org.grestler.domain.metamodel.spi.queries.IPackageLoader;
import org.grestler.domain.metamodel.spi.queries.IUndirectedEdgeTypeLoader;
import org.grestler.domain.metamodel.spi.queries.IVertexTypeLoader;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.grestler.infrastructure.utilities.instrumentation.OperationTimeLogger;
import org.grestler.infrastructure.utilities.revisions.StmTransactionContext;
import org.grestler.infrastructure.utilities.revisions.V;
import org.grestler.infrastructure.utilities.revisions.VArray;
import org.grestler.infrastructure.utilities.revisions.VHashMap;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
     * @param packageLoader            the loader used to initialize the packages into the metamodel repository.
     * @param attributeTypeLoader      the loader used to initialize the attribute types into the metamodel repository.
     * @param vertexTypeLoader         the loader used to initialize the vertex types into the metamodel repository.
     * @param directedEdgeTypeLoader   the loader used to initialize the directed edge types into the metamodel
     *                                 repository.
     * @param undirectedEdgeTypeLoader the loader used to initialize the undirected edge types into the metamodel
     *                                 repository.
     * @param attributeDeclLoader      the loader used to initialize attribute declarations into the metamodel
     *                                 repository.
     */
    @Inject
    public MetamodelRepository(
        IPackageLoader packageLoader,
        IPackageDependencyLoader packageDependencyLoader,
        IAttributeTypeLoader attributeTypeLoader,
        IVertexTypeLoader vertexTypeLoader,
        IDirectedEdgeTypeLoader directedEdgeTypeLoader,
        IUndirectedEdgeTypeLoader undirectedEdgeTypeLoader,
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

            this.packagedElementsById = new VHashMap<>( 500 );
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
                directedEdgeTypeLoader.loadAllDirectedEdgeTypes( this );
                undirectedEdgeTypeLoader.loadAllUndirectedEdgeTypes( this );
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
    public ISizedIterable<IAttributeType> findAllAttributeTypes() {
        return this.attributeTypes;
    }

    @Override
    public IIndexable<IAttributeType> findAllAttributeTypesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the attribute types list.
        List<IAttributeType> result = new ArrayList<>();
        this.attributeTypes.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                assert false : "Attribute types do not have super types";
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                assert false : "Packages do not have super types";
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

    }

    @Override
    public ISizedIterable<IDirectedEdgeType> findAllDirectedEdgeTypes() {
        return this.directedEdgeTypes;
    }

    @Override
    public IIndexable<IDirectedEdgeType> findAllDirectedEdgeTypesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the directed edge types list.
        List<IDirectedEdgeType> result = new ArrayList<>();
        this.directedEdgeTypes.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                assert false : "Directed edge types do not have super types";
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                Collections.sort( result, Comparators.DIRECTED_EDGE_SUPER_SUBTYPE );
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

    }

    @Override
    public ISizedIterable<IEdgeType> findAllEdgeTypes() {
        return this.edgeTypes;
    }

    @Override
    public IIndexable<IEdgeType> findAllEdgeTypesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the edge types list.
        List<IEdgeType> result = new ArrayList<>();
        this.edgeTypes.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                assert false : "Edge types do not have super types";
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                assert false : "Edge types do not have super types";
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

    }

    @Override
    public ISizedIterable<IPackage> findAllPackages() {
        return this.packages;
    }

    @Override
    public IIndexable<IPackage> findAllPackagesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the packages list.
        List<IPackage> result = new ArrayList<>();
        this.packages.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                Collections.sort( result, Comparators.PACKAGE_PARENT_CHILD );
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                assert false : "Packages do not have super types";
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

    }

    @Override
    public ISizedIterable<IUndirectedEdgeType> findAllUndirectedEdgeTypes() {
        return this.undirectedEdgeTypes;
    }

    @Override
    public IIndexable<IUndirectedEdgeType> findAllUndirectedEdgeTypesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the directed edge types list.
        List<IUndirectedEdgeType> result = new ArrayList<>();
        this.undirectedEdgeTypes.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                assert false : "Undirected edge types do not have super types";
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                Collections.sort( result, Comparators.UNDIRECTED_EDGE_SUPER_SUBTYPE );
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

    }

    @Override
    public ISizedIterable<IVertexType> findAllVertexTypes() {
        return this.vertexTypes;
    }

    @Override
    public IIndexable<IVertexType> findAllVertexTypesSorted( EElementSortOrder sortOrder ) {

        // Make a copy of the vertex types list.
        List<IVertexType> result = new ArrayList<>();
        this.vertexTypes.forEach( result::add );

        // Sort them as needed.
        switch ( sortOrder ) {
            case NAME:
                Collections.sort( result, Comparators.NAME );
                break;
            case PARENT_BEFORE_CHILDREN:
                assert false : "Vertex types do not have super types";
                break;
            case PATH:
                Collections.sort( result, Comparators.PATH );
                break;
            case SUPER_TYPE_BEFORE_SUB_TYPES:
                Collections.sort( result, Comparators.VERTEX_SUPER_SUBTYPE );
                break;
        }

        // Wrap up the result.
        return new ReadOnlyListAdapter<>( result );

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
    public IPackagedElement findPackagedElementById( UUID id ) {
        return this.packagedElementsById.get( id ).orElseThrow(
            () -> new MetamodelException(
                MetamodelRepository.LOG, "Packaged element not found: " + id + "."
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
            throw new MetamodelException( MetamodelRepository.LOG, "Missing root undirected edge type." );
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
        IBooleanAttributeType.Record record, IPackage parentPackage
    ) {

        IBooleanAttributeType result = new BooleanAttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;

    }

    @Override
    public IDateTimeAttributeType loadDateTimeAttributeType(
        IDateTimeAttributeType.Record record, IPackage parentPackage
    ) {

        IDateTimeAttributeType result = new DateTimeAttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;

    }

    @Override
    public IDirectedEdgeType loadDirectedEdgeType(
        IDirectedEdgeType.Record record,
        IPackage parentPackage,
        IDirectedEdgeType superType,
        IVertexType tailVertexType,
        IVertexType headVertexType
    ) {

        IDirectedEdgeType result = new DirectedEdgeType(
            record, parentPackage, superType, tailVertexType, headVertexType
        );

        this.packagedElementsById.put( record.id, result );
        this.edgeTypes.add( result );
        this.edgeTypesById.put( record.id, result );
        this.directedEdgeTypes.add( result );
        this.directedEdgeTypesById.put( record.id, result );

        return result;

    }

    @Override
    public IEdgeAttributeDecl loadEdgeAttributeDecl(
        IEdgeAttributeDecl.Record record, IEdgeType parentEdgeType, IAttributeType type
    ) {
        return new EdgeAttributeDecl( record, parentEdgeType, type );
    }

    @Override
    public IFloat64AttributeType loadFloat64AttributeType(
        IFloat64AttributeType.Record record, IPackage parentPackage
    ) {
        IFloat64AttributeType result = new Float64AttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;
    }

    @Override
    public IInteger32AttributeType loadInteger32AttributeType(
        IInteger32AttributeType.Record record, IPackage parentPackage
    ) {
        IInteger32AttributeType result = new Integer32AttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;
    }

    @Override
    public IPackage loadPackage( IPackage.Record record, IPackage parentPackage ) {

        IPackage result = new Package( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.packages.add( result );
        this.packagesById.put( record.id, result );

        return result;

    }

    @Override
    public IPackageDependency loadPackageDependency(
        IPackageDependency.Record record, IPackage clientPackage, IPackage supplierPackage
    ) {
        return new PackageDependency( record, clientPackage, supplierPackage );
    }

    @Override
    public IDirectedEdgeType loadRootDirectedEdgeType( UUID id, IPackage parentPackage ) {

        IDirectedEdgeType result = new RootDirectedEdgeType( id, parentPackage, this.rootVertexType.get() );

        this.packagedElementsById.put( id, result );
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

        this.packagedElementsById.put( id, result );
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

        this.packagedElementsById.put( id, result );
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

        this.packagedElementsById.put( id, result );
        this.vertexTypes.add( result );
        this.vertexTypesById.put( id, result );
        this.rootVertexType = new V<>( result );

        return result;

    }

    @Override
    public IStringAttributeType loadStringAttributeType(
        IStringAttributeType.Record record, IPackage parentPackage
    ) {
        IStringAttributeType result = new StringAttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;
    }

    @Override
    public IUndirectedEdgeType loadUndirectedEdgeType(
        IUndirectedEdgeType.Record record, IPackage parentPackage, IUndirectedEdgeType superType, IVertexType vertexType
    ) {

        IUndirectedEdgeType result = new UndirectedEdgeType(
            record, parentPackage, superType, vertexType
        );

        this.packagedElementsById.put( record.id, result );
        this.edgeTypes.add( result );
        this.edgeTypesById.put( record.id, result );
        this.undirectedEdgeTypes.add( result );
        this.undirectedEdgeTypesById.put( record.id, result );

        return result;

    }

    @Override
    public IUuidAttributeType loadUuidAttributeType(
        IUuidAttributeType.Record record, IPackage parentPackage
    ) {
        IUuidAttributeType result = new UuidAttributeType( record, parentPackage );

        this.packagedElementsById.put( record.id, result );
        this.attributeTypes.add( result );
        this.attributeTypesById.put( record.id, result );

        return result;
    }

    @Override
    public IVertexAttributeDecl loadVertexAttributeDecl(
        IVertexAttributeDecl.Record record, IVertexType parentVertexType, IAttributeType type
    ) {
        return new VertexAttributeDecl( record, parentVertexType, type );
    }

    @Override
    public IVertexType loadVertexType(
        IVertexType.Record record, IPackage parentPackage, IVertexType superType
    ) {

        IVertexType result = new VertexType( record, parentPackage, superType );

        this.packagedElementsById.put( record.id, result );
        this.vertexTypes.add( result );
        this.vertexTypesById.put( record.id, result );

        return result;

    }

    private static final Logger LOG = LogManager.getLogger();

    private final VArray<IAttributeType> attributeTypes;

    private final VHashMap<UUID, IAttributeType> attributeTypesById;

    private final VArray<IDirectedEdgeType> directedEdgeTypes;

    private final VHashMap<UUID, IDirectedEdgeType> directedEdgeTypesById;

    private final VArray<IEdgeType> edgeTypes;

    private final VHashMap<UUID, IEdgeType> edgeTypesById;

    private final VHashMap<UUID, IPackagedElement> packagedElementsById;

    private final VArray<IPackage> packages;

    private final VHashMap<UUID, IPackage> packagesById;

    private V<IDirectedEdgeType> rootDirectedEdgeType = null;

    private V<IPackage> rootPackage = null;

    private V<IUndirectedEdgeType> rootUndirectedEdgeType = null;

    private V<IVertexType> rootVertexType = null;

    private final VArray<IUndirectedEdgeType> undirectedEdgeTypes;

    private final VHashMap<UUID, IUndirectedEdgeType> undirectedEdgeTypesById;

    private final VArray<IVertexType> vertexTypes;

    private final VHashMap<UUID, IVertexType> vertexTypesById;

}
