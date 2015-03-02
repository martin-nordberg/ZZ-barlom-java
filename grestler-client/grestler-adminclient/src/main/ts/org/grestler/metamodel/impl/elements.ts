//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/metamodel/impl/elements
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import api = require( '../api/elements' );

/**
 * Top-level class for Grestler model elements.
 */
export class DocumentedElement implements api.IDocumentedElement {

    constructor( id : string ) {
        this.id = id;
    }

    /**
     * Returns the parent element of this element.
     */
    getParent() : api.INamedElement {
        throw new Error( "Abstract method" );
    }

    public id : string;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of abstract named element.
 */
export class NamedElement extends DocumentedElement implements api.INamedElement {

    /**
     * Constructs a new named model element.
     *
     * @param id   the unique ID for the element.
     * @param name the name of the element.
     */
    constructor( id : string, name : string ) {
        super( id );
        this.name = name;
    }

    /**
     * Returns the fully qualified path to this element.
     */
    getPath() : string {
        var result : string;

        result = this.getParent().getPath();

        if ( result.length !== 0 ) {
            return result + "." + this.name;
        }

        return this.name;
    }

    public name : string;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface supported by packages.
 */
interface IPackageUnderAssembly extends api.IPackage {

    /**
     * Adds a child element to this package.
     *
     * @param packagedElement the child element to add.
     */
    addChildElement( packagedElement : api.IPackagedElement ) : void;

    /**
     * Registers a package dependency with this package.
     *
     * @param packageDependency the added package dependency.
     */
    addPackageDependency( packageDependency : api.IPackageDependency ) : void;

    /**
     * Removes a child element from this package.
     *
     * @param packagedElement the child element to remove.
     */
    removeChildElement( packagedElement : api.IPackagedElement ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation of abstract element.
 */
export class PackagedElement extends NamedElement implements api.IPackagedElement {

    /**
     * Constructs a new element.
     *
     * @param id            the unique ID of the element.
     * @param parentPackage the parent package.
     * @param name          the name of the element.
     */
    constructor( id : string, parentPackage : api.IPackage, name : string ) {

        super( id, name );

        this.parentPackage = parentPackage;

        ( <IPackageUnderAssembly> parentPackage ).addChildElement( this );

    }

    /**
     * Determines whether this package is a direct or indirect child of the given package.
     *
     * @param parentPackage the potential parent.
     *
     * @return true if this package is a child or grandchild of the given parent package.
     */
    isChildOf( parentPackage : api.IPackage ) : boolean {
        var parentPkg : api.IPackage;

        parentPkg = this.parentPackage;

        return parentPkg == parentPackage || parentPkg.isChildOf( parentPackage );
    }

    public parentPackage : api.IPackage;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface for vertex types.
 */
interface IVertexTypeUnderAssembly {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    addAttribute( attribute : api.IVertexAttributeDecl ) : void;

    /**
     * Removes an attribute from this vertex type.
     *
     * @param attribute the attribute declaration to remove.
     */
    removeAttribute( attribute : api.IVertexAttributeDecl ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for vertex types.
 */
export class VertexType extends PackagedElement implements api.IVertexType, IVertexTypeUnderAssembly {

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
        parentPackage : api.IPackage,
        name : string,
        superType : api.IVertexType,
        abstractness : api.EAbstractness
    ) {

        super( id, parentPackage, name );

        this.superType = superType;
        this.abstractness = abstractness;
        this.attributes = [];

        // TODO: track subtypes

    }

    public addAttribute( attribute : api.IVertexAttributeDecl ) : void {
        this.attributes.push( attribute );
    }

    public isSubTypeOf( vertexType : api.IVertexType ) : boolean {
        return this == vertexType || this.superType.isSubTypeOf( vertexType );
    }

    public removeAttribute( attribute : api.IVertexAttributeDecl ) : void {
        var index = this.attributes.indexOf( attribute );
        if ( index > -1 ) {
            this.attributes.splice( index, 1 );
        }
    }

    /** Whether this vertex type is abstract. */
    public abstractness : api.EAbstractness;

    /** The attributes of this vertex type. */
    public attributes : api.IVertexAttributeDecl[];

    /** The super type of this vertex type. */
    public superType : api.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Internal interface for edge types.
 */
interface IEdgeTypeUnderAssembly {

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    addAttribute( attribute : api.IEdgeAttributeDecl ) : void;

    /**
     * Removes an attribute from this vertex type while modifying the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    removeAttribute( attribute : api.IEdgeAttributeDecl ) : void;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for edge types.
 */
export class EdgeType extends PackagedElement implements api.IEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new edge type.
     *
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
        id : string,
        parentPackage : api.IPackage,
        name : string,
        abstractness : api.EAbstractness,
        cyclicity : api.ECyclicity,
        multiEdgedness : api.EMultiEdgedness,
        selfLooping : api.ESelfLooping
    ) {
        super( id, parentPackage, name );

        this.abstractness = abstractness;
        this.cyclicity = cyclicity;
        this.multiEdgedness = multiEdgedness;
        this.selfLooping = selfLooping;

        this.attributes = [];

        // TODO: track sub-types

    }

    /**
     * Adds an attribute to this vertex type while constructing the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    public addAttribute( attribute : api.IEdgeAttributeDecl ) : void {
        this.attributes.push( attribute );
    }

    /**
     * @return the super type of this edge type.
     */
    public getSuperEdgeType() : api.IEdgeType {
        throw new Error( "Abstract method." );
    }

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    public isAbstract() : boolean {
        return this.abstractness === api.EAbstractness.ABSTRACT;
    }

    /**
     * @return whether graphs formed by this edge type are simple, i.e. have neither self-loops not multi-edges.
     */
    public isSimple() : boolean {
        return this.multiEdgedness == api.EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED &&
            this.selfLooping == api.ESelfLooping.SELF_LOOPS_NOT_ALLOWED;
    }

    /**
     * Removes an attribute from this vertex type while modifying the metamodel.
     *
     * @param attribute the child attribute to add.
     */
    public removeAttribute( attribute : api.IEdgeAttributeDecl ) : void {
        var index = this.attributes.indexOf( attribute );
        if ( index > -1 ) {
            this.attributes.splice( index, 1 );
        }
    }

    /** Whether this edge type is abstract. */
    public abstractness : api.EAbstractness;

    /** The attribute declarations within this edge type. */
    public attributes : api.IEdgeAttributeDecl[];

    /** Whether this edge type is acyclic. */
    public cyclicity : api.ECyclicity;

    /** Whether this edge type allows multiple edges between two given vertexes. */
    public multiEdgedness : api.EMultiEdgedness;

    /** Whether this edge type allows an edge from a vertex to itself. */
    public selfLooping : api.ESelfLooping;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Attribute type implementation.
 */
export class AttributeType extends PackagedElement implements api.IAttributeType {

    /**
     * Constructs a new attribute type.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent attribute type.
     * @param name          the name of the attribute type.
     */
    constructor( id : string, parentPackage : api.IPackage, name : string ) {
        super( id, parentPackage, name );
    }

    dataType : api.EDataType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for package dependencies.
 */
export class PackageDependency extends DocumentedElement implements api.IPackageDependency {

    /**
     * Constructs a package dependency.
     *
     * @param id              the unique ID of the package dependency.
     * @param clientPackage   the package making use of the supplier package.
     * @param supplierPackage the package that is depended upon.
     */
    constructor(
        id : string, clientPackage : api.IPackage, supplierPackage : api.IPackage
    ) {

        super( id );

        this.clientPackage = clientPackage;
        this.supplierPackage = supplierPackage;

        // Register both ends.
        ( <IPackageUnderAssembly> clientPackage ).addPackageDependency( this );
        ( <IPackageUnderAssembly> supplierPackage ).addPackageDependency( this );

    }

    /** The package that makes use of the supplier. */
    public clientPackage : api.IPackage;

    /** The package depended upon. */
    public supplierPackage : api.IPackage;

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
    constructor( ownerPkg : api.IPackage ) {

        this.ownerPkg = ownerPkg;

        this.attributeTypes = [];
        this.childPackages = [];
        this.edgeTypes = [];
        this.vertexTypes = [];

    }

    addChildElement( packagedElement : api.IPackagedElement ) : void {

        if ( (typeof packagedElement).match( /AttributeType$/ ) ) {
            this.attributeTypes.push( <api.IAttributeType> packagedElement );
        }
        else if ( typeof packagedElement == "Package" ) {
            this.childPackages.push( <api.IPackage> packagedElement );
        }
        else if ( typeof packagedElement == "VertexType" ) {
            this.vertexTypes.push( <api.IVertexType> packagedElement );
        }
        else if ( (typeof packagedElement).match( /EdgeType$/ ) ) {
            this.edgeTypes.push( <api.IEdgeType> packagedElement );
        }
        else {
            throw new Error( "Unknown package element: " + typeof packagedElement );
        }

    }

    getAttributeTypes() : api.IAttributeType[] {
        return this.attributeTypes;
    }

    getChildPackages() : api.IPackage[] {
        return this.childPackages;
    }

    getEdgeTypes() : api.IEdgeType[] {
        return this.edgeTypes;
    }

    getVertexTypes() : api.IVertexType[] {
        return this.vertexTypes;
    }

    removeChildElement( packagedElement : api.IPackagedElement ) : void {

        var index : number;

        if ( (typeof packagedElement).match( /AttributeType$/ ) ) {
            index = this.attributeTypes.indexOf( <api.IAttributeType> packagedElement );
            if ( index > -1 ) {
                this.attributeTypes.splice( index, 1 );
            }
        }
        else if ( typeof packagedElement == "Package" ) {
            index = this.childPackages.indexOf( <api.IPackage> packagedElement );
            if ( index > -1 ) {
                this.childPackages.splice( index, 1 );
            }
        }
        else if ( typeof packagedElement == "VertexType" ) {
            index = this.vertexTypes.indexOf( <api.IVertexType> packagedElement );
            if ( index > -1 ) {
                this.vertexTypes.splice( index, 1 );
            }
        }
        else if ( (typeof packagedElement).match( /EdgeType$/ ) ) {
            index = this.edgeTypes.indexOf( <api.IEdgeType> packagedElement );
            if ( index > -1 ) {
                this.edgeTypes.splice( index, 1 );
            }
        }
        else {
            throw new Error( "Unknown package element: " + typeof packagedElement );
        }

    }

    /** The attribute types within this package. */
    private attributeTypes : api.IAttributeType[];

    /** The sub-packages of this package. */
    private childPackages : api.IPackage[];

    /** The edge types within this package. */
    private edgeTypes : api.IEdgeType[];

    /** The package using this helper object. */
    private ownerPkg : api.IPackage;

    /** The vertex types within this package. */
    private vertexTypes : api.IVertexType[];
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for Grestler package dependency graph management.
 */
class PackageDependencies {

    /**
     * Constructs a new package dependencies helper.
     */
    constructor(  ownerPkg : api.IPackage ) {

    this.ownerPkg = ownerPkg;

    this.clientPackages = [];
    this.supplierPackages = [];

}

/**
 * Adds a package dependency related to the owner package.
 *
 * @param packageDependency the added dependency.
 */
 addPackageDependency( packageDependency : api.IPackageDependency ) : void {
    if ( this.ownerPkg == packageDependency.clientPackage ) {
        this.supplierPackages.push( packageDependency.supplierPackage );
    }
    else {
        this.clientPackages.push( packageDependency.clientPackage );
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
getClientPackages(  dependencyDepth : api.EDependencyDepth ) : api.IPackage[] {

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

    return this.clientPackages;

}

/**
 * Gets the packages that are suppliers of the owner package.
 *
 * @param dependencyDepth whether to include transitive dependencies.
 *
 * @return the collection of packages found.
 */
getSupplierPackages(  dependencyDepth : api.EDependencyDepth ) : api.IPackage[] {

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

    return this.supplierPackages;

}

/**
 * Whether the package that owns this helper instance depends on the given package.
 *
 * @param pkg             the package to look for.
 * @param dependencyDepth whether to include transitive dependencies.
 *
 * @return true if the given package is a supplier.
 */
 hasSupplierPackage(  pkg : api.IPackage,  dependencyDepth : api.EDependencyDepth ) : boolean {

    var me = this;

    if ( dependencyDepth == api.EDependencyDepth.TRANSITIVE ) {
        this.supplierPackages.forEach( function( pkg2 : api.IPackage ) {
            if ( pkg == pkg2 ) {
                return true;
            }
            if ( pkg2 != me.ownerPkg && pkg2.hasSupplierPackage( pkg, dependencyDepth ) ) {
                return true;
            }
        });
    }
    else {
        this.supplierPackages.forEach( function( pkg2 : api.IPackage ) {
            if ( pkg == pkg2 ) {
                return true;
            }
        });
    }

    return false;

}

/** The packages that depend upon this one. */
private clientPackages : api.IPackage[];

/** The package that delegates to this helper class. */
private  ownerPkg : api.IPackage;

/** The packages that this one depends upon. */
private supplierPackages : api.IPackage[];

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Implementation class for Grestler packages.
 */
export class Package extends PackagedElement implements api.IPackage, IPackageUnderAssembly {

    /**
     * Constructs a new package.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package.
     * @param name          the name of the package.
     */
    constructor( id : string, parentPackage : api.IPackage, name : string ) {

        super( id, parentPackage, name );

        this.packageDependencies = new PackageDependencies( this );
        this.packageContents = new PackageContents( this );

    }

    public  addChildElement( packagedElement : api.IPackagedElement ) : void {
        this.packageContents.addChildElement( packagedElement );
    }

    public  addPackageDependency( packageDependency : api.IPackageDependency ) : void {
        this.packageDependencies.addPackageDependency( packageDependency );
    }

    public getAttributeTypes() : api.IAttributeType[] {
        return this.packageContents.getAttributeTypes();
    }

    public getChildPackages() : api.IPackage[] {
        return this.packageContents.getChildPackages();
    }

    public getClientPackages( dependencyDepth : api.EDependencyDepth ) : api.IPackage[] {
        return this.packageDependencies.getClientPackages( dependencyDepth );
    }

    public getEdgeTypes() : api.IEdgeType[] {
        return this.packageContents.getEdgeTypes();
    }

    public getSupplierPackages( dependencyDepth : api.EDependencyDepth ) : api.IPackage[] {
        return this.packageDependencies.getSupplierPackages( dependencyDepth );
    }

    public getVertexTypes() : api.IVertexType[] {
        return this.packageContents.getVertexTypes();
    }

    public hasSupplierPackage( pkg : api.IPackage, dependencyDepth : api.EDependencyDepth ) : boolean {
        return this.packageDependencies.hasSupplierPackage( pkg, dependencyDepth );
    }

    public removeChildElement( packagedElement : api.IPackagedElement ) : void {
        this.packageContents.removeChildElement( packagedElement );
    }

    /** Helper object that manages this package's contents. */
    private  packageContents : PackageContents;

    /** Helper object that manages this package's dependencies. */
    private  packageDependencies : PackageDependencies;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

