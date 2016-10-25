//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/impl/elements
 */

import api_elements = require( '../api/elements' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top-level class for Grestler model elements.
 */
export class DocumentedElement implements api_elements.IDocumentedElement {

    /**
     * Constructs a new model element.
     *
     * @param typeName the concrete type name of this element.
     * @param id   the unique ID for the element.
     */
    constructor( typeName : string, id : string ) {
        this._typeName = typeName;
        this._id = id;
    }

    public get id() : string {
        return this._id;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.DOCUMENTED_ELEMENT == typeName;
    }

    public get parent() : api_elements.INamedElement {
        throw new Error( "Abstract method" );
    }

    public get parentsInPath() : api_elements.IDocumentedElement[] {

        var result : api_elements.IDocumentedElement[] = [];

        // Loop through the parents, adding them to the result.
        var element : api_elements.IDocumentedElement = this;
        while ( true ) {
            if ( element.typeName == 'RootPackage' ) {
                break;
            }

            element = element.parent;

            result.push( element );
        }

        return result.reverse();

    }

    public get typeName() : string {
        return this._typeName;
    }

    public set typeName( value : string ) {
        throw new Error( "Attempted to change read only attribute - typeName." );
    }

    private _id : string;

    private _typeName : string;

    // TODO: typeName attribute

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of abstract named element.
 */
export class NamedElement extends DocumentedElement implements api_elements.INamedElement {

    /**
     * Constructs a new named model element.
     *
     * @param typeName the concrete type name of this element.
     * @param id   the unique ID for the element.
     * @param name the name of the element.
     */
    constructor( typeName : string, id : string, name : string ) {
        super( typeName, id );
        this._name = name;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.NAMED_ELEMENT == typeName || super.isA( typeName );
    }

    /**
     * Returns the fully qualified path to this element.
     */
    public get path() : string {
        var result : string;

        result = this.parent.path;

        if ( result.length !== 0 ) {
            return result + "." + this.name;
        }

        return this.name;
    }

    public get name() : string {
        return this._name;
    }

    public set name( value : string ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.name',
                name: 'name',
                oldValue: this._name,
                newValue: value
            }
        );
        this._name = value;
    }

    private _name : string;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface supported by packages.
 */
interface IPackageUnderAssembly extends api_elements.IPackage {

    /**
     * Adds a child element to this package.
     *
     * @param packagedElement the child element to add.
     */
    addChildElement( packagedElement : api_elements.IPackagedElement ) : void;

    /**
     * Registers a package dependency with this package.
     *
     * @param packageDependency the added package dependency.
     */
    addPackageDependency( packageDependency : api_elements.IPackageDependency ) : void;

    /**
     * Removes a child element from this package.
     *
     * @param packagedElement the child element to remove.
     */
    removeChildElement( packagedElement : api_elements.IPackagedElement ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of abstract element.
 */
export class PackagedElement extends NamedElement implements api_elements.IPackagedElement {

    /**
     * Constructs a new element.
     *
     * @param typeName the concrete type name of this element.
     * @param id            the unique ID of the element.
     * @param parentPackage the parent package.
     * @param name          the name of the element.
     */
    constructor( typeName : string, id : string, parentPackage : api_elements.IPackage, name : string ) {

        super( typeName, id, name );

        this._parentPackage = parentPackage;

        ( <IPackageUnderAssembly> parentPackage ).addChildElement( this );

    }

    public isA( typeName : string ) : boolean {
        return api_elements.PACKAGED_ELEMENT == typeName || super.isA( typeName );
    }

    /**
     * Determines whether this package is a direct or indirect child of the given package.
     *
     * @param parentPackage the potential parent.
     *
     * @return true if this package is a child or grandchild of the given parent package.
     */
    public isChildOf( parentPackage : api_elements.IPackage ) : boolean {
        return this._parentPackage === parentPackage || this._parentPackage.isChildOf( parentPackage );
    }

    public get parent() : api_elements.INamedElement {
        return this._parentPackage;
    }

    public get parentPackage() : api_elements.IPackage {
        return this._parentPackage;
    }

    private _parentPackage : api_elements.IPackage;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface for vertex types.
 */
interface IVertexTypeUnderAssembly extends api_elements.IVertexType {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    addAttribute( attribute : api_elements.IVertexAttributeDecl ) : void;

    /**
     * Removes an attribute from this vertex type.
     *
     * @param attribute the attribute declaration to remove.
     */
    removeAttribute( attribute : api_elements.IVertexAttributeDecl ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for vertex types.
 */
export class VertexType extends PackagedElement implements api_elements.IVertexType, IVertexTypeUnderAssembly {

    /**
     * Constructs a new vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type.
     * @param abstractness  whether the vertex type is abstract.
     */
    constructor(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        superType : api_elements.IVertexType,
        abstractness : api_elements.EAbstractness
    ) {

        super( api_elements.VERTEX_TYPE, id, parentPackage, name );

        this._superType = superType;
        this._abstractness = abstractness;
        this._attributes = [];

        // TODO: track subtypes

    }

    public get abstractness() : api_elements.EAbstractness {
        return this._abstractness;
    }

    public set abstractness( value : api_elements.EAbstractness ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.abstractness',
                name: 'abstractness',
                oldValue: this._abstractness,
                newValue: value
            }
        );
        this._abstractness = value;
    }

    public addAttribute( attribute : api_elements.IVertexAttributeDecl ) : void {
        this._attributes.push( attribute );
    }

    public get attributes() : api_elements.IVertexAttributeDecl[] {
        return this._attributes;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.VERTEX_TYPE == typeName || super.isA( typeName );
    }

    public isSubTypeOf( vertexType : api_elements.IVertexType ) : boolean {
        return this == vertexType || this._superType.isSubTypeOf( vertexType );
    }

    public removeAttribute( attribute : api_elements.IVertexAttributeDecl ) : void {
        var index = this._attributes.indexOf( attribute );
        if ( index > -1 ) {
            this._attributes.splice( index, 1 );
        }
    }

    public get superType() : api_elements.IVertexType {
        return this._superType;
    }

    public set superType( value : api_elements.IVertexType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.superType',
                name: 'superType',
                oldValue: this._superType,
                newValue: value
            }
        );
        this._superType = value;
    }

    /** Whether this vertex type is abstract. */
    private _abstractness : api_elements.EAbstractness;

    /** The attributes of this vertex type. */
    private _attributes : api_elements.IVertexAttributeDecl[];

    /** The super type of this vertex type. */
    private _superType : api_elements.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface for edge types.
 */
interface IEdgeTypeUnderAssembly extends api_elements.IEdgeType {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    addAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void;

    /**
     * Removes an attribute from this vertex type while modifying the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    removeAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for edge types.
 */
export class EdgeType extends PackagedElement implements api_elements.IEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new edge type.
     *
     * @param typeName the concrete type name of this element.
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfLooping    whether the edge type disallows edges from a vertex to itself.
     */
    constructor(
        typeName : string,
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        abstractness : api_elements.EAbstractness,
        cyclicity : api_elements.ECyclicity,
        multiEdgedness : api_elements.EMultiEdgedness,
        selfLooping : api_elements.ESelfLooping
    ) {
        super( typeName, id, parentPackage, name );

        this._abstractness = abstractness;
        this._cyclicity = cyclicity;
        this._multiEdgedness = multiEdgedness;
        this._selfLooping = selfLooping;

        this._attributes = [];

        // TODO: track sub-types

    }

    public get abstractness() : api_elements.EAbstractness {
        return this._abstractness;
    }

    public set abstractness( value : api_elements.EAbstractness ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.abstractness',
                name: 'abstractness',
                oldValue: this._abstractness,
                newValue: value
            }
        );
        this._abstractness = value;
    }

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    public addAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        this._attributes.push( attribute );
    }

    public get attributes() : api_elements.IEdgeAttributeDecl[] {
        return this._attributes;
    }

    public get cyclicity() : api_elements.ECyclicity {
        return this._cyclicity;
    }

    public set cyclicity( value : api_elements.ECyclicity ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.cyclicity',
                name: 'cyclicity',
                oldValue: this._cyclicity,
                newValue: value
            }
        );
        this._cyclicity = value;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.EDGE_TYPE == typeName || super.isA( typeName );
    }

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    public get isAbstract() : boolean {
        return this._abstractness === api_elements.EAbstractness.ABSTRACT;
    }

    /**
     * @return whether graphs formed by this edge type are simple, i.e. have neither self-loops not multi-edges.
     */
    public get isSimple() : boolean {
        return this._multiEdgedness == api_elements.EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED &&
            this._selfLooping == api_elements.ESelfLooping.SELF_LOOPS_NOT_ALLOWED;
    }

    public get multiEdgedness() : api_elements.EMultiEdgedness {
        return this._multiEdgedness;
    }

    public set multiEdgedness( value : api_elements.EMultiEdgedness ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.multiEdgedness',
                name: 'multiEdgedness',
                oldValue: this._multiEdgedness,
                newValue: value
            }
        );
        this._multiEdgedness = value;
    }

    /**
     * Removes an attribute from this vertex type while modifying the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    public removeAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        var index = this._attributes.indexOf( attribute );
        if ( index > -1 ) {
            this._attributes.splice( index, 1 );
        }
    }

    public get selfLooping() : api_elements.ESelfLooping {
        return this._selfLooping;
    }

    public set selfLooping( value : api_elements.ESelfLooping ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.selfLooping',
                name: 'selfLooping',
                oldValue: this._selfLooping,
                newValue: value
            }
        );
        this._selfLooping = value;
    }

    /**
     * @return the super type of this edge type.
     */
    public get superEdgeType() : api_elements.IEdgeType {
        throw new Error( "Abstract method." );
    }

    /** Whether this edge type is abstract. */
    private _abstractness : api_elements.EAbstractness;

    /** The attribute declarations within this edge type. */
    private _attributes : api_elements.IEdgeAttributeDecl[];

    /** Whether this edge type is acyclic. */
    private _cyclicity : api_elements.ECyclicity;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    private _multiEdgedness : api_elements.EMultiEdgedness;

    /** Whether this edge type allows an edge from a vertex to itself. */
    private _selfLooping : api_elements.ESelfLooping;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Attribute type implementation.
 */
export class AttributeType extends PackagedElement implements api_elements.IAttributeType {

    /**
     * Constructs a new attribute type.
     *
     * @param typeName the concrete type name of this element.
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     */
    constructor( typeName : string, id : string, parentPackage : api_elements.IPackage, name : string ) {
        super( typeName, id, parentPackage, name );
    }

    public get dataType() : api_elements.EDataType {
        throw new Error( "Abstract method get dataType called on " + this.path + "." );
    }

    public set dataType( ignored : api_elements.EDataType ) {
        throw new Error( "Abstract method set dataType called on " + this.path + "." );
    }

    public isA( typeName : string ) : boolean {
        return api_elements.ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for package dependencies.
 */
export class PackageDependency extends DocumentedElement implements api_elements.IPackageDependency {

    /**
     * Constructs a package dependency.
     *
     * @param id              the unique ID of the package dependency.
     * @param clientPackage   the package making use of the supplier package.
     * @param supplierPackage the package that is depended upon.
     */
    constructor(
        id : string, clientPackage : api_elements.IPackage, supplierPackage : api_elements.IPackage
    ) {

        super( api_elements.PACKAGE_DEPENDENCY, id );

        this._clientPackage = clientPackage;
        this._supplierPackage = supplierPackage;

        // Register both ends.
        ( <IPackageUnderAssembly> clientPackage ).addPackageDependency( this );
        ( <IPackageUnderAssembly> supplierPackage ).addPackageDependency( this );

    }

    public get clientPackage() : api_elements.IPackage {
        return this._clientPackage;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.PACKAGE_DEPENDENCY == typeName || super.isA( typeName );
    }

    public get supplierPackage() : api_elements.IPackage {
        return this._supplierPackage;
    }

    /** The package that makes use of the supplier. */
    private _clientPackage : api_elements.IPackage;

    /** The package depended upon. */
    private _supplierPackage : api_elements.IPackage;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Helper class for maintaining the contents of a package.
 */
class PackageContents {

    /**
     * Constructs a new package elements helper instance.
     *
     * @param ownerPkg the package delegating to this helper class.
     */
    constructor( ownerPkg : api_elements.IPackage ) {

        this._ownerPkg = ownerPkg;

        this._attributeTypes = [];
        this._childPackages = [];
        this._directedEdgeTypes = [];
        this._edgeTypes = [];
        this._undirectedEdgeTypes = [];
        this._vertexTypes = [];

    }

    addChildElement( packagedElement : api_elements.IPackagedElement ) : void {

        if ( packagedElement.typeName.match( /AttributeType$/ ) ) {
            this._attributeTypes.push( <api_elements.IAttributeType> packagedElement );
        }
        else if ( packagedElement.typeName == "Package" ) {
            this._childPackages.push( <api_elements.IPackage> packagedElement );
        }
        else if ( packagedElement.typeName.match( /VertexType$/ ) ) {
            this._vertexTypes.push( <api_elements.IVertexType> packagedElement );
        }
        else if ( packagedElement.typeName.match( /DirectedEdgeType$/ ) ) {
            this._edgeTypes.push( <api_elements.IEdgeType> packagedElement );
            this._directedEdgeTypes.push( <api_elements.IDirectedEdgeType> packagedElement );
        }
        else if ( packagedElement.typeName.match( /UndirectedEdgeType$/ ) ) {
            this._edgeTypes.push( <api_elements.IEdgeType> packagedElement );
            this._undirectedEdgeTypes.push( <api_elements.IUndirectedEdgeType> packagedElement );
        }
        else {
            throw new Error( "Unknown package element: " + packagedElement.typeName );
        }

    }

    get attributeTypes() : api_elements.IAttributeType[] {
        return this._attributeTypes;
    }

    get childPackages() : api_elements.IPackage[] {
        return this._childPackages.sort(
            ( pkg1, pkg2 ) => {
                if ( pkg1.name < pkg2.name ) {
                    return -1;
                }
                if ( pkg1.name > pkg2.name ) {
                    return 1;
                }
                return 0;
            }
        );
    }

    get directedEdgeTypes() : api_elements.IDirectedEdgeType[] {
        return this._directedEdgeTypes;
    }

    get edgeTypes() : api_elements.IEdgeType[] {
        return this._edgeTypes;
    }

    get undirectedEdgeTypes() : api_elements.IUndirectedEdgeType[] {
        return this._undirectedEdgeTypes;
    }

    findOptionalChildPackageByName( name : string ) : api_elements.IPackage {
        for ( var i = 0; i < this._childPackages.length; i += 1 ) {
            if ( this._childPackages[i].name == name ) {
                return this._childPackages[i];
            }
        }
        return null;
    }

    findOptionalDirectedEdgeTypeByName( name : string ) : api_elements.IDirectedEdgeType {
        for ( var i = 0; i < this._directedEdgeTypes.length; i += 1 ) {
            if ( this._directedEdgeTypes[i].name == name ) {
                return this._directedEdgeTypes[i];
            }
        }
        return null;
    }

    findOptionalPackagedElementByName( name : string ) : api_elements.IPackagedElement {
        return this.findOptionalChildPackageByName( name ) ||
            this.findOptionalVertexTypeByName( name ) ||
            this.findOptionalDirectedEdgeTypeByName( name ) ||
            this.findOptionalUndirectedEdgeTypeByName( name );
    }

    findOptionalUndirectedEdgeTypeByName( name : string ) : api_elements.IUndirectedEdgeType {
        for ( var i = 0; i < this._undirectedEdgeTypes.length; i += 1 ) {
            if ( this._undirectedEdgeTypes[i].name == name ) {
                return this._undirectedEdgeTypes[i];
            }
        }
        return null;
    }

    findOptionalVertexTypeByName( name : string ) : api_elements.IVertexType {
        for ( var i = 0; i < this._vertexTypes.length; i += 1 ) {
            if ( this._vertexTypes[i].name == name ) {
                return this._vertexTypes[i];
            }
        }
        return null;
    }

    removeChildElement( packagedElement : api_elements.IPackagedElement ) : void {

        var index : number;

        if ( packagedElement.typeName.match( /AttributeType$/ ) ) {
            index = this._attributeTypes.indexOf( <api_elements.IAttributeType> packagedElement );
            if ( index > -1 ) {
                this._attributeTypes.splice( index, 1 );
            }
        }
        else if ( packagedElement.typeName == "Package" ) {
            index = this._childPackages.indexOf( <api_elements.IPackage> packagedElement );
            if ( index > -1 ) {
                this._childPackages.splice( index, 1 );
            }
        }
        else if ( packagedElement.typeName == "VertexType" ) {
            index = this._vertexTypes.indexOf( <api_elements.IVertexType> packagedElement );
            if ( index > -1 ) {
                this._vertexTypes.splice( index, 1 );
            }
        }
        else if ( packagedElement.typeName.match( /EdgeType$/ ) ) {
            index = this._edgeTypes.indexOf( <api_elements.IEdgeType> packagedElement );
            if ( index > -1 ) {
                this._edgeTypes.splice( index, 1 );
            }
        }
        else {
            throw new Error( "Unknown package element: " + packagedElement.typeName );
        }

    }

    get vertexTypes() : api_elements.IVertexType[] {
        return this._vertexTypes;
    }

    /** The attribute types within this package. */
    private _attributeTypes : api_elements.IAttributeType[];

    /** The sub-packages of this package. */
    private _childPackages : api_elements.IPackage[];

    /** The directed edge types within this package. */
    private _directedEdgeTypes : api_elements.IDirectedEdgeType[];

    /** The edge types within this package. */
    private _edgeTypes : api_elements.IEdgeType[];

    /** The package using this helper object. */
    private _ownerPkg : api_elements.IPackage;

    /** The undirected edge types within this package. */
    private _undirectedEdgeTypes : api_elements.IUndirectedEdgeType[];

    /** The vertex types within this package. */
    private _vertexTypes : api_elements.IVertexType[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for Grestler package dependency graph management.
 */
class PackageDependencies {

    /**
     * Constructs a new package dependencies helper.
     */
    constructor( ownerPkg : api_elements.IPackage ) {

        this._ownerPkg = ownerPkg;

        this._clientPackages = [];
        this._supplierPackages = [];

    }

    /**
     * Adds a package dependency related to the owner package.
     *
     * @param packageDependency the added dependency.
     */
    addPackageDependency( packageDependency : api_elements.IPackageDependency ) : void {
        if ( this._ownerPkg == packageDependency.clientPackage ) {
            this._supplierPackages.push( packageDependency.supplierPackage );
        }
        else {
            this._clientPackages.push( packageDependency.clientPackage );
        }
    }

    // TODO: removePackageDependency

    /**
     * Gets the packages that are clients of this package.
     *
     * @param dependencyDepth whether to include transitive clients.
     *
     * @return the collection of packages from the dependency graph.
     */
    getClientPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {

        // TODO: transitive dependencies
        //if ( dependencyDepth.isTransitive() ) {
        //    Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
        //    this.clientPackages.forEach(
        //            p -> {
        //            if ( result.add( p ) ) {
        //                p.getClientPackages( dependencyDepth ).forEach( result::add );
        //            }
        //        }
        //    );
        //    return new ReadOnlyCollectionAdapter<>( result );
        //}

        return this._clientPackages;

    }

    /**
     * Gets the packages that are suppliers of the owner package.
     *
     * @param dependencyDepth whether to include transitive dependencies.
     *
     * @return the collection of packages found.
     */
    getSupplierPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {

        // TODO: transitive dependencies
        //if ( dependencyDepth.isTransitive() ) {
        //    Collection<IPackage> result = new TreeSet<>( ( p1, p2 ) -> p1.getId().compareTo( p2.getId() ) );
        //    this.supplierPackages.forEach(
        //            p -> {
        //            if ( result.add( p ) ) {
        //                p.getClientPackages( dependencyDepth ).forEach( result::add );
        //            }
        //        }
        //    );
        //    return new ReadOnlyCollectionAdapter<>( result );
        //}

        return this._supplierPackages;

    }

    /**
     * Whether the package that owns this helper instance depends on the given package.
     *
     * @param pkg             the package to look for.
     * @param dependencyDepth whether to include transitive dependencies.
     *
     * @return true if the given package is a supplier.
     */
    hasSupplierPackage( pkg : api_elements.IPackage, dependencyDepth : api_elements.EDependencyDepth ) : boolean {

        var me = this;

        if ( dependencyDepth == api_elements.EDependencyDepth.TRANSITIVE ) {
            this._supplierPackages.forEach(
                function ( pkg2 : api_elements.IPackage ) {
                    if ( pkg == pkg2 ) {
                        return true;
                    }
                    if ( pkg2 != me._ownerPkg && pkg2.hasSupplierPackage( pkg, dependencyDepth ) ) {
                        return true;
                    }
                }
            );
        }
        else {
            this._supplierPackages.forEach(
                function ( pkg2 : api_elements.IPackage ) {
                    if ( pkg == pkg2 ) {
                        return true;
                    }
                }
            );
        }

        return false;

    }

    /** The packages that depend upon this one. */
    private _clientPackages : api_elements.IPackage[];

    /** The package that delegates to this helper class. */
    private  _ownerPkg : api_elements.IPackage;

    /** The packages that this one depends upon. */
    private _supplierPackages : api_elements.IPackage[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for Grestler packages.
 */
export class Package extends PackagedElement implements api_elements.IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new package.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package.
     * @param name          the name of the package.
     */
    constructor( id : string, parentPackage : api_elements.IPackage, name : string ) {

        super( api_elements.PACKAGE, id, parentPackage, name );

        this._packageDependencies = new PackageDependencies( this );
        this._packageContents = new PackageContents( this );

    }

    public addChildElement( packagedElement : api_elements.IPackagedElement ) : void {

        this._packageContents.addChildElement( packagedElement );

        Object['getNotifier']( this ).notify(
            {
                type: 'change.childElements',
                name: 'childElements',
                newValue: this._packageContents
            }
        );

    }

    public addPackageDependency( packageDependency : api_elements.IPackageDependency ) : void {
        this._packageDependencies.addPackageDependency( packageDependency );
    }

    public get attributeTypes() : api_elements.IAttributeType[] {
        return this._packageContents.attributeTypes;
    }

    public get childPackages() : api_elements.IPackage[] {
        return this._packageContents.childPackages;
    }

    public findOptionalChildPackageByName( name : string ) : api_elements.IPackage {
        return this._packageContents.findOptionalChildPackageByName( name );
    }

    public findOptionalDirectedEdgeTypeByName( name : string ) : api_elements.IDirectedEdgeType {
        return this._packageContents.findOptionalDirectedEdgeTypeByName( name );
    }

    public findOptionalPackagedElementByName( name : string ) : api_elements.IPackagedElement {
        return this._packageContents.findOptionalPackagedElementByName( name );
    }

    public findOptionalUndirectedEdgeTypeByName( name : string ) : api_elements.IUndirectedEdgeType {
        return this._packageContents.findOptionalUndirectedEdgeTypeByName( name );
    }

    public findOptionalVertexTypeByName( name : string ) : api_elements.IVertexType {
        return this._packageContents.findOptionalVertexTypeByName( name );
    }

    public getClientPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {
        return this._packageDependencies.getClientPackages( dependencyDepth );
    }

    public get directedEdgeTypes() : api_elements.IDirectedEdgeType[] {
        return this._packageContents.directedEdgeTypes;
    }

    public get edgeTypes() : api_elements.IEdgeType[] {
        return this._packageContents.edgeTypes;
    }

    public getSupplierPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {
        return this._packageDependencies.getSupplierPackages( dependencyDepth );
    }

    public get undirectedEdgeTypes() : api_elements.IUndirectedEdgeType[] {
        return this._packageContents.undirectedEdgeTypes;
    }

    public get vertexTypes() : api_elements.IVertexType[] {
        return this._packageContents.vertexTypes;
    }

    public hasSupplierPackage(
        pkg : api_elements.IPackage,
        dependencyDepth : api_elements.EDependencyDepth
    ) : boolean {
        return this._packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    public isA( typeName : string ) : boolean {
        return api_elements.PACKAGE == typeName || super.isA( typeName );
    }

    public removeChildElement( packagedElement : api_elements.IPackagedElement ) : void {
        this._packageContents.removeChildElement( packagedElement );
    }

    /** Helper object that manages this package's contents. */
    private  _packageContents : PackageContents;

    /** Helper object that manages this package's dependencies. */
    private  _packageDependencies : PackageDependencies;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for edge types.
 */
export class DirectedEdgeType extends EdgeType implements api_elements.IDirectedEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param id               the unique ID of the edge type.
     * @param parentPackage    the package containing the edge type.
     * @param name             the name of the edge type.
     * @param superType        the super type.
     * @param abstractness     whether the edge type is abstract or concrete.
     * @param cyclicity        whether the edge type is constrained to be acyclic.
     * @param multiEdgedness   whether the edge type is constrained to disallow multiple edges between two given
     *                         vertexes.
     * @param selfLooping      whether the edge type disallows edges from a vertex to itself.
     * @param tailVertexType   the vertex type at the start of the edge type.
     * @param headVertexType   the vertex type at the end of the edge type.
     * @param tailRoleName     the role name for vertexes at the tail of this edge type
     * @param headRoleName     the role name for vertexes at the head of this edge type
     * @param minTailOutDegree the minimum out-degree for the tail vertex of an edge of this type.
     * @param maxTailOutDegree the maximum out-degree for the tail vertex of an edge of this type.
     * @param minHeadInDegree  the minimum in-degree for the head vertex of an edge of this type.
     * @param maxHeadInDegree  the maximum in-degree for the head vertex of an edge of this type.
     */
    constructor(
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
    ) {
        super(
            api_elements.DIRECTED_EDGE_TYPE,
            id,
            parentPackage,
            name,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping
        );

        this._superType = superType;
        this._tailVertexType = tailVertexType;
        this._headVertexType = headVertexType;
        this._tailRoleName = tailRoleName;
        this._headRoleName = headRoleName;
        this._minTailOutDegree = minTailOutDegree;
        this._maxTailOutDegree = maxTailOutDegree;
        this._minHeadInDegree = minHeadInDegree;
        this._maxHeadInDegree = maxHeadInDegree;

    }

    public get headRoleName() : string {
        return this._headRoleName;
    }

    public set headRoleName( value : string ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.headRoleName',
                name: 'headRoleName',
                oldValue: this._headRoleName,
                newValue: value
            }
        );
        this._headRoleName = value;
    }

    public  get headVertexType() : api_elements.IVertexType {
        return this._headVertexType;
    }

    public set headVertexType( value : api_elements.IVertexType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.headVertexType',
                name: 'headVertexType',
                oldValue: this._headVertexType,
                newValue: value
            }
        );
        this._headVertexType = value;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.DIRECTED_EDGE_TYPE == typeName || super.isA( typeName );
    }

    public  get maxHeadInDegree() : number {
        return this._maxHeadInDegree;
    }

    public set maxHeadInDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxHeadInDegree',
                name: 'maxHeadInDegree',
                oldValue: this._maxHeadInDegree,
                newValue: value
            }
        );
        this._maxHeadInDegree = value;
    }

    public get maxTailOutDegree() : number {
        return this._maxTailOutDegree;
    }

    public set maxTailOutDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxTailOutDegree',
                name: 'maxTailOutDegree',
                oldValue: this._maxTailOutDegree,
                newValue: value
            }
        );
        this._maxTailOutDegree = value;
    }

    public get minHeadInDegree() : number {
        return this._minHeadInDegree;
    }

    public set minHeadInDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minHeadInDegree',
                name: 'minHeadInDegree',
                oldValue: this._minHeadInDegree,
                newValue: value
            }
        );
        this._minHeadInDegree = value;
    }

    public get minTailOutDegree() : number {
        return this._minTailOutDegree;
    }

    public set minTailOutDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minTailOutDegree',
                name: 'minTailOutDegree',
                oldValue: this._minTailOutDegree,
                newValue: value
            }
        );
        this._minTailOutDegree = value;
    }

    public get superEdgeType() : api_elements.IEdgeType {
        return this._superType;
    }

    public get superType() : api_elements.IDirectedEdgeType {
        return this._superType;
    }

    public set superType( value : api_elements.IDirectedEdgeType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.superType',
                name: 'superType',
                oldValue: this._superType,
                newValue: value
            }
        );
        // TODO: unwire old subtype
        this._superType = value;
        // TODO: wire new subtype
    }

    public get tailRoleName() : string {
        return this._tailRoleName;
    }

    public set tailRoleName( value : string ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.tailRoleName',
                name: 'tailRoleName',
                oldValue: this._tailRoleName,
                newValue: value
            }
        );
        this._tailRoleName = value;
    }

    public get tailVertexType() : api_elements.IVertexType {
        return this._tailVertexType;
    }

    public set tailVertexType( value : api_elements.IVertexType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.tailVertexType',
                name: 'tailVertexType',
                oldValue: this._tailVertexType,
                newValue: value
            }
        );
        this._tailVertexType = value;
    }

    public isSubTypeOf( edgeType : api_elements.IDirectedEdgeType ) : boolean {
        return this == edgeType || this.superType.isSubTypeOf( edgeType );
    }

    /** The name of the role for the vertex at the head of edges of this type. */
    private _headRoleName : string;

    /** The vertex type at the head of edges of this type. */
    private _headVertexType : api_elements.IVertexType;

    /** The maximum in-degree for the head vertex of edges of this type. */
    private _maxHeadInDegree : number;

    /** The maximum out-degree for the tail vertex of edges of this type. */
    private _maxTailOutDegree : number;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private _minHeadInDegree : number;

    /** The minimum out-degree for the tail vertex of edges of this type. */
    private _minTailOutDegree : number;

    /** The super type of this edge type. */
    private _superType : api_elements.IDirectedEdgeType;

    /** The name of the role for the vertex at the tail of edges of this type. */
    private _tailRoleName : string;

    /** The vertex type at the tail of edges of this type. */
    private _tailVertexType : api_elements.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for edge types.
 */
export class UndirectedEdgeType extends EdgeType implements api_elements.IUndirectedEdgeType {

    /**
     * Constructs a new edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param cyclicity      whether the edge type is constrained to be acyclic.
     * @param multiEdgedness whether the edge type is constrained to disallow multiple edges between two given
     *                       vertexes.
     * @param selfLooping    whether the edge type disallows edges from a vertex to itself.
     * @param vertexType     the vertex type for the edge type.
     * @param minDegree      the minimum degree for the any vertex of an edge of this type.
     * @param maxDegree      the maximum degree for the any vertex of an edge of this type.
     */
    constructor(
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
    ) {
        super(
            api_elements.UNDIRECTED_EDGE_TYPE,
            id,
            parentPackage,
            name,
            abstractness,
            cyclicity,
            multiEdgedness,
            selfLooping
        );

        this._superType = superType;
        this._vertexType = vertexType;
        this._minDegree = minDegree;
        this._maxDegree = maxDegree;

    }

    public isA( typeName : string ) : boolean {
        return api_elements.UNDIRECTED_EDGE_TYPE == typeName || super.isA( typeName );
    }

    public  get maxDegree() : number {
        return this._maxDegree;
    }

    public set maxDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxDegree',
                name: 'maxDegree',
                oldValue: this._maxDegree,
                newValue: value
            }
        );
        this._maxDegree = value;
    }

    public get minDegree() : number {
        return this._minDegree;
    }

    public set minDegree( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minDegree',
                name: 'minDegree',
                oldValue: this._minDegree,
                newValue: value
            }
        );
        this._minDegree = value;
    }

    public get superEdgeType() : api_elements.IEdgeType {
        return this._superType;
    }

    public get superType() : api_elements.IUndirectedEdgeType {
        return this._superType;
    }

    public set superType( value : api_elements.IUndirectedEdgeType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.superType',
                name: 'superType',
                oldValue: this._superType,
                newValue: value
            }
        );
        // TODO: unwire old subtype
        this._superType = value;
        // TODO: wire new subtype
    }


    public get vertexType() : api_elements.IVertexType {
        return this._vertexType;
    }

    public set vertexType( value : api_elements.IVertexType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.vertexType',
                name: 'vertexType',
                oldValue: this._vertexType,
                newValue: value
            }
        );
        this._vertexType = value;
    }

    public isSubTypeOf( edgeType : api_elements.IUndirectedEdgeType ) : boolean {
        return this == edgeType || this.superType.isSubTypeOf( edgeType );
    }

    /** The maximum in-degree for the head vertex of edges of this type. */
    private _maxDegree : number;

    /** The minimum in-degree for the head vertex of edges of this type. */
    private _minDegree : number;

    /** The super type of this type. */
    private _superType : api_elements.IUndirectedEdgeType;

    /** The vertex type for edges of this type. */
    private _vertexType : api_elements.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of a boolean attribute type.
 */
export class BooleanAttributeType extends AttributeType implements api_elements.IBooleanAttributeType {

    /**
     * Constructs a new boolean attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param defaultValue  the default value for attributes of this type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage, name : string, defaultValue : boolean
    ) {
        super( api_elements.BOOLEAN_ATTRIBUTE_TYPE, id, parentPackage, name );
        this._defaultValue = defaultValue;
    }

    public get defaultValue() : boolean {
        return this._defaultValue;
    }

    public set defaultValue( value : boolean ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.defaultValue',
                name: 'defaultValue',
                oldValue: this._defaultValue,
                newValue: value
            }
        );
        this._defaultValue = value;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.BOOLEAN_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

    /** The default value for attributes of this type. */
    private _defaultValue : boolean;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Date/time attribute type implementation.
 */
export class DateTimeAttributeType extends AttributeType implements api_elements.IDateTimeAttributeType {

    /**
     * Constructs a new date/time attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage, name : string, minValue : Date, maxValue : Date
    ) {

        super( api_elements.DATE_TIME_ATTRIBUTE_TYPE, id, parentPackage, name );

        this._maxValue = maxValue;
        this._minValue = minValue;

    }

    public isA( typeName : string ) : boolean {
        return api_elements.DATE_TIME_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

    public get maxValue() : Date {
        return this._maxValue;
    }

    public set maxValue( value : Date ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxValue',
                name: 'maxValue',
                oldValue: this._maxValue,
                newValue: value
            }
        );
        this._maxValue = value;
    }

    public get minValue() : Date {
        return this._minValue;
    }

    public set minValue( value : Date ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minValue',
                name: 'minValue',
                oldValue: this._minValue,
                newValue: value
            }
        );
        this._minValue = value;
    }

    /** The maximum allowed value for attributes with this type. */
    private _maxValue : Date;

    /** The minimum allowed value for attributes with this type. */
    private _minValue : Date;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation for 64-bit floating point attribute types.
 */
export class Float64AttributeType extends AttributeType implements api_elements.IFloat64AttributeType {

    /**
     * Constructs a new floating point attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     * @param defaultValue  the default value for attributes of this type.
     */
    constructor(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) {
        super( api_elements.FLOAT64_ATTRIBUTE_TYPE, id, parentPackage, name );

        this._minValue = minValue;
        this._maxValue = maxValue;
        this._defaultValue = defaultValue;
    }

    public get defaultValue() : number {
        return this._defaultValue;
    }

    public set defaultValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.defaultValue',
                name: 'defaultValue',
                oldValue: this._defaultValue,
                newValue: value
            }
        );
        this._defaultValue = value;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.FLOAT64_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

    public get maxValue() : number {
        return this._maxValue;
    }

    public set maxValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxValue',
                name: 'maxValue',
                oldValue: this._maxValue,
                newValue: value
            }
        );
        this._maxValue = value;
    }

    public get minValue() : number {
        return this._minValue;
    }

    public set minValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minValue',
                name: 'minValue',
                oldValue: this._minValue,
                newValue: value
            }
        );
        this._minValue = value;
    }

    /** The default value for attributes of this type. */
    private _defaultValue : number;

    /** The minimum allowed value for attributes with this type. */
    private _maxValue : number;

    /** The maximum allowed value for attributes with this type. */
    private _minValue : number;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation for 32-bit integer attribute types.
 */
export class Integer32AttributeType extends AttributeType implements api_elements.IInteger32AttributeType {

    /**
     * Constructs a new floating point attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes of this type.
     * @param maxValue      the minimum value for attributes of this type.
     * @param defaultValue  the default value for attributes of this type.
     */
    constructor(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) {
        super( api_elements.INTEGER32_ATTRIBUTE_TYPE, id, parentPackage, name );

        this._minValue = minValue;
        this._maxValue = maxValue;
        this._defaultValue = defaultValue;
    }

    public get defaultValue() : number {
        return this._defaultValue;
    }

    public set defaultValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.defaultValue',
                name: 'defaultValue',
                oldValue: this._defaultValue,
                newValue: value
            }
        );
        this._defaultValue = value;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.INTEGER32_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

    public get maxValue() : number {
        return this._maxValue;
    }

    public set maxValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxValue',
                name: 'maxValue',
                oldValue: this._maxValue,
                newValue: value
            }
        );
        this._maxValue = value;
    }

    public get minValue() : number {
        return this._minValue;
    }

    public set minValue( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minValue',
                name: 'minValue',
                oldValue: this._minValue,
                newValue: value
            }
        );
        this._minValue = value;
    }

    /** The default value for attributes of this type. */
    private _defaultValue : number;

    /** The minimum allowed value for attributes with this type. */
    private _maxValue : number;

    /** The maximum allowed value for attributes with this type. */
    private _minValue : number;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation for string attribute types.
 */
export class StringAttributeType extends AttributeType implements api_elements.IStringAttributeType {

    /**
     * Constructs a new integer attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     * @param minLength     the minimum length for values with this attribute type.
     * @param maxLength     the maximum length for values with this attribute type.
     * @param regexPattern  a regular expression that must be matched by values with this attribute type.
     */
    constructor(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minLength : number,
        maxLength : number,
        regexPattern : string
    ) {
        super( api_elements.STRING_ATTRIBUTE_TYPE, id, parentPackage, name );

        this._minLength = minLength;
        this._maxLength = maxLength;

        this._regexPattern = new RegExp( regexPattern );
    }

    public isA( typeName : string ) : boolean {
        return api_elements.STRING_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

    public get maxLength() : number {
        return this._maxLength;
    }

    public get minLength() : number {
        return this._minLength;
    }

    public get regexPattern() : RegExp {
        return this._regexPattern;
    }

    public set maxLength( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.maxLength',
                name: 'maxLength',
                oldValue: this._maxLength,
                newValue: value
            }
        );
        this._maxLength = value;
    }

    public set minLength( value : number ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.minLength',
                name: 'minLength',
                oldValue: this._minLength,
                newValue: value
            }
        );
        this._minLength = value;
    }

    public set regexPattern( value : RegExp ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.regexPattern',
                name: 'regexPattern',
                oldValue: this._regexPattern,
                newValue: value
            }
        );
        this._regexPattern = value;
    }

    /** The maximum length for values with this attribute type. */
    private _maxLength : number;

    /** The minimum length for attributes of this type. */
    private _minLength : number;

    /** The regular expression that must be matched by values with this attribute type. */
    private _regexPattern : RegExp;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of a UUID attribute type.
 */
export class UuidAttributeType extends AttributeType implements api_elements.IUuidAttributeType {

    /**
     * Constructs a new UUID attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage, name : string
    ) {
        super( api_elements.UUID_ATTRIBUTE_TYPE, id, parentPackage, name );
    }

    public isA( typeName : string ) : boolean {
        return api_elements.UUID_ATTRIBUTE_TYPE == typeName || super.isA( typeName );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for edge attribute declarations.
 */
export class EdgeAttributeDecl extends NamedElement implements api_elements.IEdgeAttributeDecl {

    /**
     * Constructs a new edge attribute declaration.
     *
     * @param id             the unique ID of the attribute declaration.
     * @param parentEdgeType the parent edge type.
     * @param name           the name of the attribute.
     * @param type           the type of the attribute.
     * @param optionality    whether this attribute is optionality.
     */
    constructor(
        id : string,
        parentEdgeType : api_elements.IEdgeType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality
    ) {

        super( api_elements.EDGE_ATTRIBUTE_DECL, id, name );

        this._parentEdgeType = parentEdgeType;
        this._type = type;
        this._optionality = optionality;

        ( <IEdgeTypeUnderAssembly> parentEdgeType ).addAttribute( this );

    }

    public isA( typeName : string ) : boolean {
        return api_elements.EDGE_ATTRIBUTE_DECL == typeName || super.isA( typeName );
    }

    public get optionality() : api_elements.EAttributeOptionality {
        return this._optionality;
    }

    public get parentEdgeType() : api_elements.IEdgeType {
        return this._parentEdgeType;
    }

    public get type() : api_elements.IAttributeType {
        return this._type;
    }

    public set optionality( value : api_elements.EAttributeOptionality ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.optionality',
                name: 'optionality',
                oldValue: this._optionality,
                newValue: value
            }
        );
        this._optionality = value;
    }

    public set parentEdgeType( value : api_elements.IEdgeType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.parentEdgeType',
                name: 'parentEdgeType',
                oldValue: this._parentEdgeType,
                newValue: value
            }
        );

        ( <IEdgeTypeUnderAssembly> this._parentEdgeType ).removeAttribute( this );

        this._parentEdgeType = value;

        ( <IEdgeTypeUnderAssembly> value ).addAttribute( this );

    }

    public set type( value : api_elements.IAttributeType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.type',
                name: 'type',
                oldValue: this._type,
                newValue: value
            }
        );
        this._type = value;
    }

    /** Whether this attribute is required for instances of the parent edge type. */
    private _optionality : api_elements.EAttributeOptionality;

    /** The parent edge type with this attribute. */
    private _parentEdgeType : api_elements.IEdgeType;

    /** The type of this attribute declaration. */
    private _type : api_elements.IAttributeType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for vertex attribute declarations.
 */
export class VertexAttributeDecl extends NamedElement implements api_elements.IVertexAttributeDecl {

    /**
     * Constructs a new vertex attribute declaration.
     *
     * @param id               the unique ID of the attribute declaration.
     * @param parentVertexType the parent vertex type.
     * @param name             the name of the attribute.
     * @param type             the type of the attribute.
     * @param optionality      whether this attribute is optionality.
     * @param labelDefaulting  whether this is the default label for vertexes of the parent type.
     */
    constructor(
        id : string,
        parentVertexType : api_elements.IVertexType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality,
        labelDefaulting : api_elements.ELabelDefaulting
    ) {

        super( api_elements.VERTEX_ATTRIBUTE_DECL, id, name );

        this._parentVertexType = parentVertexType;
        this._type = type;
        this._optionality = optionality;
        this._labelDefaulting = labelDefaulting;

        ( <IVertexTypeUnderAssembly> parentVertexType ).addAttribute( this );

    }

    public get labelDefaulting() : api_elements.ELabelDefaulting {
        return this._labelDefaulting;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.VERTEX_ATTRIBUTE_DECL == typeName || super.isA( typeName );
    }

    public get optionality() : api_elements.EAttributeOptionality {
        return this._optionality;
    }

    public get parentVertexType() : api_elements.IVertexType {
        return this._parentVertexType;
    }

    public get type() : api_elements.IAttributeType {
        return this._type;
    }

    public set labelDefaulting( value : api_elements.ELabelDefaulting ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.labelDefaulting',
                name: 'labelDefaulting',
                oldValue: this._labelDefaulting,
                newValue: value
            }
        );
        this._labelDefaulting = value;
    }

    public set optionality( value : api_elements.EAttributeOptionality ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.optionality',
                name: 'optionality',
                oldValue: this._optionality,
                newValue: value
            }
        );
        this._optionality = value;
    }

    public set parentVertexType( value : api_elements.IVertexType ) {

        Object['getNotifier']( this ).notify(
            {
                type: 'change.parentVertexType',
                name: 'parentVertexType',
                oldValue: this._parentVertexType,
                newValue: value
            }
        );

        ( <IVertexTypeUnderAssembly> this._parentVertexType ).removeAttribute( this );

        this._parentVertexType = value;

        ( <IVertexTypeUnderAssembly> value ).addAttribute( this );

    }

    public set type( value : api_elements.IAttributeType ) {
        Object['getNotifier']( this ).notify(
            {
                type: 'change.type',
                name: 'type',
                oldValue: this._type,
                newValue: value
            }
        );
        this._type = value;
    }

    /** Whether this attribute serves as the default label for vertexes of the parent type. */
    private _labelDefaulting : api_elements.ELabelDefaulting;

    /** Whether this attribute is required for instances of the parent vertex type. */
    private _optionality : api_elements.EAttributeOptionality;

    /** The parent vertex type with this attribute. */
    private _parentVertexType : api_elements.IVertexType;

    /** The type of this attribute declaration. */
    private _type : api_elements.IAttributeType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of the top level root package.
 */
export class RootPackage implements api_elements.IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new root package.
     *
     * @param id the unique ID of the root package.
     */
    constructor( id : string ) {

        this._id = id;

        this._packageDependencies = new PackageDependencies( this );
        this._packageContents = new PackageContents( this );

    }

    public addChildElement( packagedElement : api_elements.IPackagedElement ) : void {
        this._packageContents.addChildElement( packagedElement );
    }

    public addPackageDependency( packageDependency : api_elements.IPackageDependency ) : void {
        this._packageDependencies.addPackageDependency( packageDependency );
    }

    public get attributeTypes() : api_elements.IAttributeType[] {
        return this._packageContents.attributeTypes;
    }

    public get childPackages() : api_elements.IPackage[] {
        return this._packageContents.childPackages;
    }

    public get directedEdgeTypes() : api_elements.IDirectedEdgeType[] {
        return this._packageContents.directedEdgeTypes;
    }

    public get edgeTypes() : api_elements.IEdgeType[] {
        return this._packageContents.edgeTypes;
    }

    public findOptionalChildPackageByName( name : string ) : api_elements.IPackage {
        return this._packageContents.findOptionalChildPackageByName( name );
    }

    public findOptionalDirectedEdgeTypeByName( name : string ) : api_elements.IDirectedEdgeType {
        return this._packageContents.findOptionalDirectedEdgeTypeByName( name );
    }

    public findOptionalPackagedElementByName( name : string ) : api_elements.IPackagedElement {
        return this._packageContents.findOptionalPackagedElementByName( name );
    }

    public findOptionalUndirectedEdgeTypeByName( name : string ) : api_elements.IUndirectedEdgeType {
        return this._packageContents.findOptionalUndirectedEdgeTypeByName( name );
    }

    public findOptionalVertexTypeByName( name : string ) : api_elements.IVertexType {
        return this._packageContents.findOptionalVertexTypeByName( name );
    }

    public getClientPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {
        return this._packageDependencies.getClientPackages( dependencyDepth );
    }

    public get id() : string {
        return this._id;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.ROOT_PACKAGE == typeName ||
            api_elements.PACKAGE == typeName ||
            api_elements.PACKAGED_ELEMENT == typeName ||
            api_elements.NAMED_ELEMENT == typeName ||
            api_elements.DOCUMENTED_ELEMENT == typeName;
    }

    public get name() : string {
        return "$";
    }

    public get parent() : api_elements.INamedElement {
        return this;
    }

    public get parentsInPath() : api_elements.IDocumentedElement[] {
        return [];
    }

    public get parentPackage() : api_elements.IPackage {
        return this;
    }

    public get path() : string {
        return "";
    }

    public get typeName() : string {
        return 'RootPackage';
    }

    public getSupplierPackages( dependencyDepth : api_elements.EDependencyDepth ) : api_elements.IPackage[] {
        return this._packageDependencies.getSupplierPackages( dependencyDepth );
    }

    public get undirectedEdgeTypes() : api_elements.IUndirectedEdgeType[] {
        return this._packageContents.undirectedEdgeTypes;
    }

    public get vertexTypes() : api_elements.IVertexType[] {
        return this._packageContents.vertexTypes;
    }

    public hasSupplierPackage(
        pkg : api_elements.IPackage,
        dependencyDepth : api_elements.EDependencyDepth
    ) : boolean {
        return this._packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    public isChildOf( parentPackage : api_elements.IPackage ) : boolean {
        return false;
    }

    public removeChildElement( packagedElement : api_elements.IPackagedElement ) : void {
        this._packageContents.removeChildElement( packagedElement );
    }

    /** The unique ID of this root package. */
    private _id : string;

    /** Helper object manages the contents of this package. */
    private _packageContents : PackageContents;

    /** Helper object that manages this package's dependencies. */
    private _packageDependencies : PackageDependencies;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of the top-level root vertex type.
 */
export class RootVertexType implements api_elements.IVertexType, IVertexTypeUnderAssembly {

    /**
     * Constructs a new root vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage
    ) {

        this._id = id;
        this._parentPackage = parentPackage;
        this._attributes = [];

        ( <IPackageUnderAssembly> parentPackage ).addChildElement( this );

    }

    public addAttribute( attribute : api_elements.IVertexAttributeDecl ) : void {
        this._attributes.push( attribute );
    }

    public get abstractness() : api_elements.EAbstractness {
        return api_elements.EAbstractness.ABSTRACT;
    }

    public get attributes() : api_elements.IVertexAttributeDecl[] {
        return this._attributes;
    }

    public get id() : string {
        return this._id;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.ROOT_VERTEX_TYPE == typeName ||
            api_elements.VERTEX_TYPE == typeName ||
            api_elements.PACKAGED_ELEMENT == typeName ||
            api_elements.NAMED_ELEMENT == typeName ||
            api_elements.DOCUMENTED_ELEMENT == typeName;
    }

    public isChildOf( parentPackage : api_elements.IPackage ) : boolean {
        return this._parentPackage === parentPackage || this._parentPackage.isChildOf( parentPackage );
    }

    public get name() : string {
        return "Vertex";
    }

    public get parent() : api_elements.INamedElement {
        return this._parentPackage;
    }

    public get parentsInPath() : api_elements.IDocumentedElement[] {
        return [this._parentPackage];
    }

    public get parentPackage() : api_elements.IPackage {
        return this._parentPackage;
    }

    public get path() : string {
        return this.name;
    }

    public get superType() : api_elements.IVertexType {
        return null;
    }

    public isSubTypeOf( vertexType : api_elements.IVertexType ) : boolean {
        return this == vertexType;
    }

    public removeAttribute( attribute : api_elements.IVertexAttributeDecl ) : void {
        var index = this._attributes.indexOf( attribute );
        if ( index > -1 ) {
            this._attributes.splice( index, 1 );
        }
    }

    public get typeName() : string {
        return "RootVertexType";
    }

    /** The attributes of this vertex type. */
    private _attributes : api_elements.IVertexAttributeDecl[];

    /** The unique ID of this vertex type. */
    private _id : string;

    /** The parent (root) package for this vertex type. */
    private _parentPackage : api_elements.IPackage;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of the top-level root directed edge type.
 */
export class RootDirectedEdgeType implements api_elements.IDirectedEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new root directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param rootVertexType the root vertex type connected by the edge type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage, rootVertexType : api_elements.IVertexType
    ) {

        this._id = id;
        this._parentPackage = parentPackage;
        this._rootVertexType = rootVertexType;

        this._attributes = [];

        ( <IPackageUnderAssembly> parentPackage ).addChildElement( this );

    }

    public addAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        this._attributes.push( attribute );
    }

    public get abstractness() : api_elements.EAbstractness {
        return api_elements.EAbstractness.ABSTRACT;
    }

    public get attributes() : api_elements.IEdgeAttributeDecl[] {
        return this._attributes;
    }

    public get cyclicity() : api_elements.ECyclicity {
        return api_elements.ECyclicity.UNCONSTRAINED;
    }

    public get headRoleName() : string {
        return null;
    }

    public get headVertexType() : api_elements.IVertexType {
        return this._rootVertexType;
    }

    public get id() : string {
        return this._id;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.ROOT_DIRECTED_EDGE_TYPE == typeName ||
            api_elements.DIRECTED_EDGE_TYPE == typeName ||
            api_elements.EDGE_TYPE == typeName ||
            api_elements.PACKAGED_ELEMENT == typeName ||
            api_elements.NAMED_ELEMENT == typeName ||
            api_elements.DOCUMENTED_ELEMENT == typeName;
    }

    public get isAbstract() : boolean {
        return true;
    }

    public isChildOf( parentPackage : api_elements.IPackage ) : boolean {
        return this._parentPackage === parentPackage || this._parentPackage.isChildOf( parentPackage );
    }

    public get isSimple() : boolean {
        return true;
    }

    public isSubTypeOf( edgeType : api_elements.IDirectedEdgeType ) : boolean {
        return this === edgeType;
    }

    public get maxHeadInDegree() : number {
        return null;
    }

    public get maxTailOutDegree() : number {
        return null;
    }

    public get minHeadInDegree() : number {
        return null;
    }

    public get minTailOutDegree() : number {
        return null;
    }

    public get multiEdgedness() : api_elements.EMultiEdgedness {
        return api_elements.EMultiEdgedness.UNCONSTRAINED;
    }

    public get name() : string {
        return "Directed Edge";
    }

    public get parent() : api_elements.INamedElement {
        return this._parentPackage;
    }

    public get parentPackage() : api_elements.IPackage {
        return this._parentPackage;
    }

    public get parentsInPath() : api_elements.IDocumentedElement[] {
        return [this._parentPackage];
    }

    public get path() : string {
        return this.name;
    }

    public get selfLooping() : api_elements.ESelfLooping {
        return api_elements.ESelfLooping.UNCONSTRAINED;
    }

    public get superEdgeType() : api_elements.IEdgeType {
        return null;
    }

    public get superType() : api_elements.IDirectedEdgeType {
        return null;
    }

    public get tailRoleName() : string {
        return null;
    }

    public get tailVertexType() : api_elements.IVertexType {
        return this._rootVertexType;
    }

    public get typeName() : string {
        return "RootDirectedEdgeType";
    }


    public removeAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        var index = this._attributes.indexOf( attribute );
        if ( index > -1 ) {
            this._attributes.splice( index, 1 );
        }
    }

    private _attributes : api_elements.IEdgeAttributeDecl[];

    private _id : string;

    private _parentPackage : api_elements.IPackage;

    private _rootVertexType : api_elements.IVertexType;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of the top-level root directed edge type.
 */
export class RootUndirectedEdgeType implements api_elements.IUndirectedEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new root directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param rootVertexType the root vertex type connected by the edge type.
     */
    constructor(
        id : string, parentPackage : api_elements.IPackage, rootVertexType : api_elements.IVertexType
    ) {

        this._id = id;
        this._parentPackage = parentPackage;
        this._rootVertexType = rootVertexType;

        this._attributes = [];

        ( <IPackageUnderAssembly> parentPackage ).addChildElement( this );

    }

    public addAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        this._attributes.push( attribute );
    }

    public get abstractness() : api_elements.EAbstractness {
        return api_elements.EAbstractness.ABSTRACT;
    }

    public get attributes() : api_elements.IEdgeAttributeDecl[] {
        return this._attributes;
    }

    public get cyclicity() : api_elements.ECyclicity {
        return api_elements.ECyclicity.UNCONSTRAINED;
    }

    public get id() : string {
        return this._id;
    }

    public isA( typeName : string ) : boolean {
        return api_elements.ROOT_UNDIRECTED_EDGE_TYPE == typeName ||
            api_elements.UNDIRECTED_EDGE_TYPE == typeName ||
            api_elements.EDGE_TYPE == typeName ||
            api_elements.PACKAGED_ELEMENT == typeName ||
            api_elements.NAMED_ELEMENT == typeName ||
            api_elements.DOCUMENTED_ELEMENT == typeName;
    }

    public get isAbstract() : boolean {
        return true;
    }

    public isChildOf( parentPackage : api_elements.IPackage ) : boolean {
        return this._parentPackage === parentPackage || this._parentPackage.isChildOf( parentPackage );
    }

    public get isSimple() : boolean {
        return false;
    }

    public get maxDegree() : number {
        return null;
    }

    public get minDegree() : number {
        return null;
    }

    public get multiEdgedness() : api_elements.EMultiEdgedness {
        return api_elements.EMultiEdgedness.UNCONSTRAINED;
    }

    public get name() : string {
        return "Undirected Edge";
    }

    public get parent() : api_elements.INamedElement {
        return this._parentPackage;
    }

    public get parentPackage() : api_elements.IPackage {
        return this._parentPackage;
    }

    public get parentsInPath() : api_elements.IDocumentedElement[] {
        return [this._parentPackage];
    }

    public get path() : string {
        return this.name;
    }

    public get selfLooping() : api_elements.ESelfLooping {
        return api_elements.ESelfLooping.UNCONSTRAINED;
    }

    public get superEdgeType() : api_elements.IEdgeType {
        return null;
    }

    public get superType() : api_elements.IUndirectedEdgeType {
        return null;
    }

    public get typeName() : string {
        return "RootUndirectedEdgeType";
    }

    public get vertexType() : api_elements.IVertexType {
        return this._rootVertexType;
    }

    public isSubTypeOf( edgeType : api_elements.IUndirectedEdgeType ) : boolean {
        return this === edgeType;
    }

    public removeAttribute( attribute : api_elements.IEdgeAttributeDecl ) : void {
        var index = this._attributes.indexOf( attribute );
        if ( index > -1 ) {
            this._attributes.splice( index, 1 );
        }
    }

    private _attributes : api_elements.IEdgeAttributeDecl[];

    private _id : string;

    private _parentPackage : api_elements.IPackage;

    private _rootVertexType : api_elements.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

