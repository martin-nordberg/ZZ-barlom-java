//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/elements
 */

import api_elements = require( '../api/elements' );
import api_queries = require( '../api/queries' );
import impl_elements = require( '../impl/elements' );
import spi_queries = require( '../spi/queries' );
import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * The main metamodel repository.
 */
export class MetamodelRepository implements spi_queries.IMetamodelRepositorySpi {

    /**
     * Constructs a new metamodel repository. Loads it with packages, attribute types, vertex types, and edge types from
     * the given sources.
     *
     * @param packageLoader       the loader used to initialize the packages into the metamodel repository.
     * @param packageDependencyLoader the loader for package dependencies.
     * @param attributeTypeLoader the loader used to initialize the attribute types into the metamodel repository.
     * @param vertexTypeLoader    the loader used to initialize the vertex types into the metamodel repository.
     * @param edgeTypeLoader      the loader used to initialize the edge types into the metamodel repository.
     * @param attributeDeclLoader the loader used to initialize attribute declarations into the metamodel repository.
     */
    constructor(
        packageLoader : spi_queries.IPackageLoader,
        packageDependencyLoader : spi_queries.IPackageDependencyLoader,
        attributeTypeLoader : spi_queries.IAttributeTypeLoader,
        vertexTypeLoader : spi_queries.IVertexTypeLoader,
        edgeTypeLoader : spi_queries.IEdgeTypeLoader,
        attributeDeclLoader : spi_queries.IAttributeDeclLoader
    ) {

        // Define arrays of elements.
        this._packages = [];
        this._vertexTypes = [];
        this._edgeTypes = [];
        this._directedEdgeTypes = [];
        this._undirectedEdgeTypes = [];
        this._attributeTypes = [];

        // Define keyed maps of elements.
        this._packagesById = {};
        this._vertexTypesById = {};
        this._edgeTypesById = {};
        this._directedEdgeTypesById = {};
        this._undirectedEdgeTypesById = {};
        this._attributeTypesById = {};

        // Define root elements.
        this._rootDirectedEdgeType = null;
        this._rootPackage = null;
        this._rootUndirectedEdgeType = null;
        this._rootVertexType = null;

        // Load all the metamodel elements in appropriate order
        // TODO: initiate the AJAX in parallel, join, then do the loading in sequence
        this._loaded = packageLoader.loadAllPackages( this );
        this._loaded = this._loaded.then(
            ( _ : values.ENothing ) => packageDependencyLoader.loadAllPackageDependencies( this )
        );
        this._loaded = this._loaded.then(
            ( _ : values.ENothing ) => attributeTypeLoader.loadAllAttributeTypes( this )
        );
        this._loaded = this._loaded.then(
            ( _ : values.ENothing ) => vertexTypeLoader.loadAllVertexTypes( this )
        );
        this._loaded = this._loaded.then(
            ( _ : values.ENothing ) => edgeTypeLoader.loadAllEdgeTypes( this )
        );
        this._loaded = this._loaded.then(
            ( _ : values.ENothing ) => attributeDeclLoader.loadAllAttributeDecls( this )
        );

    }

    public findAllAttributeTypes() : api_elements.IAttributeType[] {
        return this._attributeTypes;
    }

    public findAllDirectedEdgeTypes() : api_elements.IDirectedEdgeType[] {
        return this._directedEdgeTypes;
    }

    public findAllEdgeTypes() : api_elements.IEdgeType[] {
        return this._edgeTypes;
    }

    public findAllPackages() : api_elements.IPackage[] {
        return this._packages;
    }

    public findAllUndirectedEdgeTypes() : api_elements.IUndirectedEdgeType[] {
        return this._undirectedEdgeTypes;
    }

    public findAllVertexTypes() : api_elements.IVertexType[] {
        return this._vertexTypes;
    }

    public findAttributeTypeById( id : string ) : api_elements.IAttributeType {
        var result = this._attributeTypesById[id];
        if ( !result ) {
            throw new Error( "Attribute type not found: " + id + "." );
        }
        return result;
    }

    public findDirectedEdgeTypeById( id : string ) : api_elements.IDirectedEdgeType {
        var result = this._directedEdgeTypesById[id];
        if ( !result ) {
            throw new Error( "Directed edge type not found: " + id + "." );
        }
        return result;
    }

    public findEdgeTypeById( id : string ) : api_elements.IEdgeType {
        var result = this._edgeTypesById[id];
        if ( !result ) {
            throw new Error( "Edge type not found: " + id + "." );
        }
        return result;
    }

    public findOptionalAttributeTypeById( id : string ) : api_elements.IAttributeType {
        return this._attributeTypesById[id];
    }

    public findOptionalDirectedEdgeTypeById( id : string ) : api_elements.IDirectedEdgeType {
        return this._directedEdgeTypesById[id];
    }

    public findOptionalEdgeTypeById( id : string ) : api_elements.IEdgeType {
        return this._edgeTypesById[id];
    }

    public findOptionalPackageById( id : string ) : api_elements.IPackage {
        return this._packagesById[id];
    }

    public findOptionalUndirectedEdgeTypeById( id : string ) : api_elements.IUndirectedEdgeType {
        return this._undirectedEdgeTypesById[id];
    }

    public findOptionalVertexTypeById( id : string ) : api_elements.IVertexType {
        return this._vertexTypesById[id];
    }

    public findPackageById( id : string ) : api_elements.IPackage {
        var result = this._packagesById[id];
        if ( !result ) {
            throw new Error( "Package not found: " + id + "." );
        }
        return result;
    }

    public findRootDirectedEdgeType() : api_elements.IDirectedEdgeType {
        if ( this._rootDirectedEdgeType == null ) {
            throw new Error( "Missing root directed edge type." );
        }
        return this._rootDirectedEdgeType;
    }

    public findRootPackage() : api_elements.IPackage {
        if ( this._rootPackage == null ) {
            throw new Error( "Missing root package." );
        }
        return this._rootPackage;
    }

    public findRootUndirectedEdgeType() : api_elements.IUndirectedEdgeType {
        if ( this._rootUndirectedEdgeType == null ) {
            throw new Error( "Missing root undirected edge type." );
        }
        return this._rootUndirectedEdgeType;
    }

    public findRootVertexType() : api_elements.IVertexType {
        if ( this._rootVertexType == null ) {
            throw new Error( "Missing root vertex type." );
        }
        return this._rootVertexType;
    }

    public findUndirectedEdgeTypeById( id : string ) : api_elements.IUndirectedEdgeType {
        var result = this._undirectedEdgeTypesById[id];
        if ( !result ) {
            throw new Error( "Undirected edge type not found: " + id + "." );
        }
        return result;
    }

    public findVertexTypeById( id : string ) : api_elements.IVertexType {
        var result = this._vertexTypesById[id];
        if ( !result ) {
            throw new Error( "Vertex type not found: " + id + "." );
        }
        return result;
    }

    public loadBooleanAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string, defaultValue : boolean
    ) : api_elements.IBooleanAttributeType {

        var result : api_elements.IBooleanAttributeType = new impl_elements.BooleanAttributeType(
            id,
            parentPackage,
            name,
            defaultValue
        );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;

    }

    public loadDateTimeAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string, minValue : Date, maxValue : Date
    ) : api_elements.IDateTimeAttributeType {

        var result : api_elements.IDateTimeAttributeType = new impl_elements.DateTimeAttributeType(
            id,
            parentPackage,
            name,
            minValue,
            maxValue
        );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;

    }

    public loadDirectedEdgeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        superType : api_elements.IDirectedEdgeType,
        abstractness : api_elements.EAbstractness,
        cyclicity : api_elements.ECyclicity,
        multiEdgedness : api_elements.EMultiEdgedness,
        selfLooping : api_elements.ESelfLooping,
        tailVertexType : api_elements.IVertexType,
        headVertexType : api_elements.IVertexType,
        tailRoleName : string,
        headRoleName : string,
        minTailOutDegree : number,
        maxTailOutDegree : number,
        minHeadInDegree : number,
        maxHeadInDegree : number
    ) : api_elements.IDirectedEdgeType {

        var result : api_elements.IDirectedEdgeType = new impl_elements.DirectedEdgeType(
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

        this._edgeTypes.push( result );
        this._edgeTypesById[id] = result;
        this._directedEdgeTypes.push( result );
        this._directedEdgeTypesById[id] = result;

        return result;

    }

    public loadEdgeAttributeDecl(
        id : string,
        parentEdgeType : api_elements.IEdgeType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality
    ) : api_elements.IEdgeAttributeDecl {
        return new impl_elements.EdgeAttributeDecl( id, parentEdgeType, name, type, optionality );
    }

    public loadFloat64AttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) : api_elements.IFloat64AttributeType {
        var result : api_elements.IFloat64AttributeType = new impl_elements.Float64AttributeType(
            id, parentPackage, name, minValue, maxValue, defaultValue
        );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;
    }

    public loadInteger32AttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) : api_elements.IInteger32AttributeType {
        var result : api_elements.IInteger32AttributeType = new impl_elements.Integer32AttributeType(
            id, parentPackage, name, minValue, maxValue, defaultValue
        );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;
    }

    public loadPackage( id : string, parentPackage : api_elements.IPackage, name : string ) : api_elements.IPackage {

        var result : api_elements.IPackage = new impl_elements.Package( id, parentPackage, name );

        this._packages.push( result );
        this._packagesById[id] = result;

        return result;

    }

    public loadPackageDependency(
        id : string, clientPackage : api_elements.IPackage, supplierPackage : api_elements.IPackage
    ) : api_elements.IPackageDependency {
        return new impl_elements.PackageDependency( id, clientPackage, supplierPackage );
    }

    public loadRootDirectedEdgeType(
        id : string,
        parentPackage : api_elements.IPackage
    ) : api_elements.IDirectedEdgeType {

        var result : api_elements.IDirectedEdgeType = new impl_elements.RootDirectedEdgeType(
            id,
            parentPackage,
            this._rootVertexType
        );

        this._edgeTypes.push( result );
        this._edgeTypesById[id] = result;
        this._directedEdgeTypes.push( result );
        this._directedEdgeTypesById[id] = result;
        this._rootDirectedEdgeType = result;

        return result;

    }

    public loadRootPackage( id : string ) : api_elements.IPackage {

        var result : api_elements.IPackage = new impl_elements.RootPackage( id );

        this._packages.push( result );
        this._packagesById[id] = result;
        this._rootPackage = result;

        return result;

    }

    public loadRootUndirectedEdgeType(
        id : string, parentPackage : api_elements.IPackage
    ) : api_elements.IUndirectedEdgeType {

        var result : api_elements.IUndirectedEdgeType = new impl_elements.RootUndirectedEdgeType(
            id,
            parentPackage,
            this._rootVertexType
        );

        this._edgeTypes.push( result );
        this._edgeTypesById[id] = result;
        this._undirectedEdgeTypes.push( result );
        this._undirectedEdgeTypesById[id] = result;
        this._rootUndirectedEdgeType = result;

        return result;

    }

    public loadRootVertexType( id : string, parentPackage : api_elements.IPackage ) : api_elements.IVertexType {

        var result : api_elements.IVertexType = new impl_elements.RootVertexType( id, parentPackage );

        this._vertexTypes.push( result );
        this._vertexTypesById[id] = result;
        this._rootVertexType = result;

        return result;

    }

    public loadStringAttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minLength : number,
        maxLength : number,
        regexPattern : string
    ) : api_elements.IStringAttributeType {
        var result : api_elements.IStringAttributeType = new impl_elements.StringAttributeType(
            id, parentPackage, name, minLength, maxLength, regexPattern
        );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;
    }

    public loadUndirectedEdgeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        superType : api_elements.IUndirectedEdgeType,
        abstractness : api_elements.EAbstractness,
        cyclicity : api_elements.ECyclicity,
        multiEdgedness : api_elements.EMultiEdgedness,
        selfLooping : api_elements.ESelfLooping,
        vertexType : api_elements.IVertexType,
        minDegree : number,
        maxDegree : number
    ) : api_elements.IUndirectedEdgeType {

        var result : api_elements.IUndirectedEdgeType = new impl_elements.UndirectedEdgeType(
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

        this._edgeTypes.push( result );
        this._edgeTypesById[id] = result;
        this._undirectedEdgeTypes.push( result );
        this._undirectedEdgeTypesById[id] = result;

        return result;

    }

    public loadUuidAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string
    ) : api_elements.IUuidAttributeType {
        var result : api_elements.IUuidAttributeType = new impl_elements.UuidAttributeType( id, parentPackage, name );

        this._attributeTypes.push( result );
        this._attributeTypesById[id] = result;

        return result;
    }

    public loadVertexAttributeDecl(
        id : string,
        parentVertexType : api_elements.IVertexType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality,
        labelDefaulting : api_elements.ELabelDefaulting
    ) : api_elements.IVertexAttributeDecl {
        return new impl_elements.VertexAttributeDecl( id, parentVertexType, name, type, optionality, labelDefaulting );
    }

    public loadVertexType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        superType : api_elements.IVertexType,
        abstractness : api_elements.EAbstractness
    ) : api_elements.IVertexType {

        var result : api_elements.IVertexType = new impl_elements.VertexType(
            id,
            parentPackage,
            name,
            superType,
            abstractness
        );

        this._vertexTypes.push( result );
        this._vertexTypesById[id] = result;

        return result;

    }

    /**
     * @returns Promise fulfilled when the repository has been loaded.
     */
    public get loaded() : Promise<values.ENothing> {
        return this._loaded;
    }

    private _attributeTypes : api_elements.IAttributeType[];

    private _attributeTypesById : {};

    private _directedEdgeTypes : api_elements.IDirectedEdgeType[];

    private _directedEdgeTypesById : {};

    private _edgeTypes : api_elements.IEdgeType[];

    private _edgeTypesById : {};

    private _loaded : Promise<values.ENothing>;

    private _packages : api_elements.IPackage[];

    private _packagesById : {};

    private _undirectedEdgeTypes : api_elements.IUndirectedEdgeType[];

    private _undirectedEdgeTypesById : {};

    private _vertexTypes : api_elements.IVertexType[];

    private _vertexTypesById : {};

    private _rootDirectedEdgeType : api_elements.IDirectedEdgeType;

    private _rootPackage : api_elements.IPackage;

    private _rootUndirectedEdgeType : api_elements.IUndirectedEdgeType;

    private _rootVertexType : api_elements.IVertexType;

}
