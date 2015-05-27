//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/metamodel/api/elements
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for whether something is abstract.
 */
export enum EAbstractness {

    /** An element is abstract. */
    ABSTRACT = 0,

    /** An element is concrete. */
    CONCRETE = 1

}

/**
 * Converts a string value to an abstractness.
 * @param abstractness the string - should be "ABSTRACT" or "CONCRETE".
 * @returns {*} the corresponding abstractness enum value.
 */
export function abstractnessFromString( abstractness : string ) {

    if ( abstractness == null ) {
        return null;
    }
    if ( "ABSTRACT" === abstractness.toUpperCase().trim() ) {
        return EAbstractness.ABSTRACT;
    }
    if ( "CONCRETE" === abstractness.toUpperCase().trim() ) {
        return EAbstractness.CONCRETE;
    }

    throw new Error( "Undefined abstractness: '" + abstractness + "'." );

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for whether an attribute is required or optional.
 */
export enum EAttributeOptionality {

    /** An attribute is optional. */
    OPTIONAL,

    /** An attribute is required. */
    REQUIRED

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for cyclic/acyclic.
 */
export enum ECyclicity {

    /** Cyclicity of an edge type is unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Edges of a given edge type are constrained to be acyclic. */
    ACYCLIC,

    /** Edges of a given edge type are expected to be cyclic. */
    POTENTIALLY_CYCLIC

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of basic data types.
 */
export enum EDataType {

    BOOLEAN,
    DATETIME,
    FLOAT64,
    INTEGER32,
    STRING,
    UUID

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for whether a dependency is direct or can also be indirect.
 */
export enum EDependencyDepth {

    /** The dependency is direct. */
    DIRECT,

    /** The dependency is direct or indirect. */
    TRANSITIVE

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for whether an attribute is the default label for a vertex type.
 */
export enum ELabelDefaulting {

    /** An attribute is not the default label. */
    NOT_DEFAULT_LABEL,

    /** An element is concrete. */
    DEFAULT_LABEL

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible constraints for multi edges in an edge type.
 */
export enum EMultiEdgedness {

    /** An edge type does not constrain the multi-edgedness of its edges. */
    UNCONSTRAINED,

    /** An edge type allows multiple edges between two given vertexes. */
    MULTI_EDGES_ALLOWED,

    /** An edge type disallows multiple edges between two given vertexes. */
    MULTI_EDGES_NOT_ALLOWED

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible constraints for self loops in an edge type.
 */
export enum ESelfLooping {

    /** Self loops are unconstrained by an edge type. */
    UNCONSTRAINED,

    /** Self loops are allowed by an edge type. */
    SELF_LOOPS_ALLOWED,

    /** Self loops are disallowed by an edge type. */
    SELF_LOOPS_NOT_ALLOWED

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface INamedElement {
    // forward
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared top level base interface for metadata elements.
 */
export interface IDocumentedElement {

    /** The unique ID of this element */
    id : string;

    /** The parent element of this element. */
    parent : INamedElement;

    /** An array of elements from the root package down to but not including this element. */
    parentsInPath : IDocumentedElement[];

    /** The concrete type name of this element. */
    typeName : string;

    /** Whether this element has the given type. */
    isA( typeName : string ) : boolean;

}

/**
 * Type name for documented elements.
 * @type {string}
 */
export const DOCUMENTED_ELEMENT = 'DocumentedElement';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared base interface for metadata elements with names.
 */
export interface INamedElement extends IDocumentedElement {

    /** the name of this element. */
    name: string;

    /**
     * Returns the fully qualified path to this element.
     */
    path : string;

}

/**
 * Type name for named elements.
 * @type {string}
 */
export const NAMED_ELEMENT = 'NamedElement';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface IPackage {
    // Forward
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared general interface for metadata elements that are direct children of packages.
 */
export interface IPackagedElement extends INamedElement {

    /**
     * @return the parent package of this packaged element.
     */
    parentPackage : IPackage;

    /**
     * Determines whether this package is a direct or indirect child of the given package.
     *
     * @param parentPackage the potential parent.
     *
     * @return true if this package is a child or grandchild of the given parent package.
     */
    isChildOf( parentPackage : IPackage ) : boolean;

}

/**
 * Type name for packaged elements.
 * @type {string}
 */
export const PACKAGED_ELEMENT = 'PackagedElement';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface IVertexAttributeDecl {
    // forward
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top level interface to a vertex type.
 */
export interface IVertexType extends IPackagedElement {

    /**
     * @return the abstractness of this vertex type
     */
    abstractness : EAbstractness;

    /**
     * @return the defined attributes of this vertex type.
     */
    attributes : IVertexAttributeDecl[];

    /**
     * @return the super type of this vertex type.
     */
    superType : IVertexType;

    /**
     * Determines whether this vertex type is a direct or indirect subtype of the given vertex type.
     *
     * @param vertexType the potential super type
     *
     * @return true if this vertex type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    isSubTypeOf( vertexType : IVertexType ) : boolean;

}

/**
 * Type name for vertex types.
 * @type {string}
 */
export const VERTEX_TYPE = 'VertexType';

/**
 * Type name for root vertex types.
 * @type {string}
 */
export const ROOT_VERTEX_TYPE = 'RootVertexType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface IEdgeAttributeDecl {
    // forward
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top level interface to an edge type.
 */
export interface IEdgeType extends IPackagedElement {

    /**
     * @return the abstractness of this edge type
     */
    abstractness : EAbstractness;

    /**
     * @return the defined attributes of this edge type.
     */
    attributes : IEdgeAttributeDecl[];

    /**
     * @return whether edges of this type can form a cyclic graph.
     */
    cyclicity : ECyclicity;

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    isAbstract : boolean;

    /**
     * @return whether graphs formed by this edge type are simple, i.e. have neither self-loops not multi-edges.
     */
    isSimple : boolean;

    /**
     * @return whether edges of this type must be unique between any two given vertexes.
     */
    multiEdgedness : EMultiEdgedness ;

    /**
     * @return whether edges of this type can connect a vertex to itself.
     */
    selfLooping : ESelfLooping ;

    /**
     * @return the super type of this edge type.
     */
    superEdgeType : IEdgeType;

}

/**
 * Type name for edge types.
 * @type {string}
 */
export const EDGE_TYPE = 'EdgeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Base interface for an abstract attribute type.
 */
export interface IAttributeType extends IPackagedElement {

    /**
     * @return the fundamental data type for attributes of this type.
     */
    dataType : EDataType;

}

/**
 * Type name for attribute types.
 * @type {string}
 */
export const ATTRIBUTE_TYPE = 'AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a package dependency.
 */
export interface IPackageDependency extends IDocumentedElement {

    /**
     * @return the package that makes use of the supplier package.
     */
    clientPackage : IPackage;


    /**
     * @return the package that is depended upon.
     */
    supplierPackage : IPackage;

}

/**
 * Type name for package dependencies.
 * @type {string}
 */
export const PACKAGE_DEPENDENCY = 'PackageDependency';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface IPackage extends IPackagedElement {

    /**
     * @return the attribute types that are children of this package.
     */
    attributeTypes : IAttributeType[];

    /**
     * @return the packages that are children of this one.
     */
    childPackages : IPackage[];

    /**
     * @return the edge types that are children of this package.
     */
    edgeTypes : IEdgeType[];

    /**
     * @return the vertex types that are children of this package.
     */
    vertexTypes : IVertexType[];

    /**
     * Finds the child package with given name (or null if not found).
     * @param name
     */
    findOptionalChildPackageByName( name : string ) : IPackage;

    /**
     * Finds the vertex type with given name (or null if not found).
     * @param name
     */
    findOptionalVertexTypeByName( name : string ) : IVertexType;

    /**
     * Determines the packages that depend upon this one.
     *
     * @param dependencyDepth whether to include indirect clients.
     *
     * @return the packages that depend upon this package.
     */
    getClientPackages( dependencyDepth : EDependencyDepth ) : IPackage[];

    /**
     * Determines the packages that this package depends upon.
     *
     * @param dependencyDepth whether to include indirect dependencies.
     *
     * @return the packages that this package depends upon.
     */
    getSupplierPackages( dependencyDepth : EDependencyDepth ) : IPackage[];

    /**
     * Whether this package depends upon the given one (directly or indirectly).
     *
     * @param pkg             the package to check.
     * @param dependencyDepth whether to consider indirect suppliers.
     *
     * @return true if this package depends upon the given one.
     */
    hasSupplierPackage( pkg : IPackage, dependencyDepth : EDependencyDepth ) : boolean;

}

/**
 * Type name for packages.
 * @type {string}
 */
export const PACKAGE = 'Package';

/**
 * Type name for root packages.
 * @type {string}
 */
export const ROOT_PACKAGE = 'RootPackage';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top level interface to a directed edge type.
 */
export interface IDirectedEdgeType extends IEdgeType {

    /**
     * @return the name of the role for the vertex at the head of edges of this type.
     */
    headRoleName : string;

    /**
     * @return the destination vertex type for edges of this type.
     */
    headVertexType : IVertexType;

    /**
     * The maximum number of edges into the head vertex for an edge of this type.
     *
     * @return the maximum head in degree (optional).
     */
    maxHeadInDegree : number;

    /**
     * The maximum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the maximum tail out degree (optional).
     */
    maxTailOutDegree : number;

    /**
     * The minimum number of edges into the head vertex for an edge of this type.
     *
     * @return the minimum head in degree (optional).
     */
    minHeadInDegree : number;

    /**
     * The minimum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the minimum tail out degree (optional).
     */
    minTailOutDegree : number;

    /**
     * @return the super type of this edge type.
     */
    superType : IDirectedEdgeType;

    /**
     * @return the name of the role for the vertex at the tail of edges of this type.
     */
    tailRoleName : string;

    /**
     * @return the origin vertex type for edges of this type.
     */
    tailVertexType : IVertexType;

    /**
     * Determines whether this edge type is a direct or indirect subtype of the given edge type.
     *
     * @param edgeType the potential super type
     *
     * @return true if this edge type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    isSubTypeOf( edgeType : IDirectedEdgeType ) : boolean;

}

/**
 * Type name for directed edge types.
 * @type {string}
 */
export const DIRECTED_EDGE_TYPE = 'DirectedEdgeType';

/**
 * Type name for a root directed edge type.
 * @type {string}
 */
export const ROOT_DIRECTED_EDGE_TYPE = 'RootDirectedEdgeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top level interface to an undirected edge type.
 */
export interface IUndirectedEdgeType extends IEdgeType {

    /**
     * The maximum number of edges connected to any vertex for an edge of this type.
     *
     * @return the maximum degree (optional).
     */
    maxDegree : number;

    /**
     * The minimum number of edges connected to any vertex for an edge of this type.
     *
     * @return the minimum degree (optional).
     */
    minDegree : number;

    /**
     * @return the super type of this edge type.
     */
    superType : IUndirectedEdgeType;

    /**
     * @return the vertex type for edges of this type.
     */
    vertexType : IVertexType;

    /**
     * Determines whether this edge type is a direct or indirect subtype of the given edge type.
     *
     * @param edgeType the potential super type
     *
     * @return true if this edge type is the given type or, recursively, if its super type is a subtype of the given
     * type.
     */
    isSubTypeOf( edgeType : IUndirectedEdgeType ) : boolean;

}

/**
 * Type name for undirected edge types.
 * @type {string}
 */
export const UNDIRECTED_EDGE_TYPE = 'UndirectedEdgeType';

/**
 * Type name for a root undirected edge type.
 * @type {string}
 */
export const ROOT_UNDIRECTED_EDGE_TYPE = 'RootUndirectedEdgeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a boolean attribute type.
 */
export interface IBooleanAttributeType extends IAttributeType {


    /**
     * The default value for attributes of this type.
     *
     * @return the default value.
     */
    defaultValue : boolean;

}

/**
 * Type name for boolean attributes.
 * @type {string}
 */
export const BOOLEAN_ATTRIBUTE_TYPE = 'BooleanAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a date/time attribute type.
 */
export interface IDateTimeAttributeType extends IAttributeType {

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    maxValue : Date;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    minValue : Date;

}

/**
 * Type name for date/time attribute types.
 * @type {string}
 */
export const DATE_TIME_ATTRIBUTE_TYPE = 'DateTimeAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to 64-bit floating point attribute types.
 */
export interface IFloat64AttributeType extends IAttributeType {

    /**
     * @return the default value for attributes of this type.
     */
    defaultValue : number;

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    maxValue : number;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    minValue : number;

}

/**
 * Type name for 64-bit floating point attribute types.
 * @type {string}
 */
export const FLOAT64_ATTRIBUTE_TYPE = 'Float64AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to 32-bit integer attribute types.
 */
export interface IInteger32AttributeType extends IAttributeType {

    /**
     * @return the default value for attributes of this type.
     */
    defaultValue : number;

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    maxValue : number;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    minValue : number;

}

/**
 * Type name for 32-bit integer attribute types.
 * @type {string}
 */
export const INTEGER32_ATTRIBUTE_TYPE = 'Integer32AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to string attribute types.
 */
export interface IStringAttributeType extends IAttributeType {

    /**
     * @return the maximum allowed length for attributes of this type.
     */
    maxLength : number;

    /**
     * @return the minimum allowed length for attributes of this type.
     */
    minLength : number;

    /**
     * @return a regular expression that must be matched by values of this attribute type.
     */
    regexPattern : RegExp;

}

/**
 * Type name for string attribute types.
 * @type {string}
 */
export const STRING_ATTRIBUTE_TYPE = 'StringAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a UUID attribute type.
 */
export interface IUuidAttributeType extends IAttributeType {

}

/**
 * Type name for UUID attribute types.
 * @type {string}
 */
export const UUID_ATTRIBUTE_TYPE = 'UuidAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to an edge attribute declaration.
 */
export interface IEdgeAttributeDecl extends INamedElement {

    /**
     * @return whether this is a required attribute.
     */
    optionality : EAttributeOptionality;

    /**
     * @return the parent of this attribute.
     */
    parentEdgeType : IEdgeType;

    /**
     * @return the type of this attribute.
     */
    type : IAttributeType;

}

/**
 * Type name for edge attribute declarations.
 * @type {string}
 */
export const EDGE_ATTRIBUTE_DECL = 'EdgeAttributeDecl';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a vertex attribute declaration.
 */
export interface IVertexAttributeDecl extends INamedElement {

    /**
     * @return whether this is the default label for the vertex.
     */
    labelDefaulting : ELabelDefaulting;

    /**
     * @return whether this is a required attribute.
     */
    optionality : EAttributeOptionality;

    /**
     * @return the parent of this attribute.
     */
    parentVertexType : IVertexType;

    /**
     * @return the typeof this attribute.
     */
    type : IAttributeType;

}

/**
 * Type name for vertex type attribute declarations.
 * @type {string}
 */
export const VERTEX_ATTRIBUTE_DECL = 'VertexAttributeDecl';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
