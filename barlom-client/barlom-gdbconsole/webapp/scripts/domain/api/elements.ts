"use strict";

//
// (C) Copyright 2015-2017 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: Barlom domain/api/elements.ts
 */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import {IElement} from "../../infrastructure/modeling/elements";

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

    switch ( abstractness.toUpperCase().trim() ) {
        case "ABSTRACT":
            return EAbstractness.ABSTRACT;
        case "CONCRETE":
            return EAbstractness.CONCRETE;
        default:
            throw new Error( "Undefined abstractness: '" + abstractness + "'." );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for whether an attribute is required or optional.
 */
export enum EOptionality {

    /** An attribute is optional. */
    OPTIONAL,

    /** An attribute is required. */
    REQUIRED

}

/**
 * Converts a string value to an optionality.
 * @param optionality the string - should be "OPTIONAL" or "REQUIRED".
 * @returns {*} the corresponding optionality enum value.
 */
export function optionalityFromString( optionality : string ) {

    if ( optionality == null ) {
        return null;
    }

    switch ( optionality.toUpperCase().trim() ) {
        case "OPTIONAL":
            return EOptionality.OPTIONAL;
        case "REQUIRED":
            return EOptionality.REQUIRED;
        default:
            throw new Error( "Undefined optionality: '" + optionality + "'." );
    }


}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible values for cyclic/acyclic.
 */
export enum ECyclicity {

    /** Cyclicity of an edge type is unconstrained by an abstract edge type. */
    UNCONSTRAINED,

    /** Edges of a given edge type are constrained to be acyclic. */
    ACYCLIC,

    /** Edges of a given edge type are expected to be cyclic. */
    POTENTIALLY_CYCLIC

}

/**
 * Converts a string value to a cyclicity.
 * @param cyclicity the string - should be "UNCONSTRAINED", "ACYCLIC", or "POTENTIALLY_CYCLIC".
 * @returns {*} the corresponding cyclicity enum value.
 */
export function cyclicityFromString( cyclicity : string ) {

    if ( cyclicity == null ) {
        return null;
    }

    switch ( cyclicity.toUpperCase().trim() ) {
        case "UNCONSTRAINED":
            return ECyclicity.UNCONSTRAINED;
        case "ACYCLIC":
            return ECyclicity.ACYCLIC;
        case "POTENTIALLY_CYCLIC":
            return ECyclicity.POTENTIALLY_CYCLIC;
        default:
            throw new Error( "Undefined cyclicity: '" + cyclicity + "'." );
    }

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

/**
 * Converts a string value to a data type.
 * @param dataType the string - should be "BOOLEAN", "DATETIME", "FLOAT64", "INTEGER32", "STRING", or "UUID".
 * @returns {*} the corresponding cyclicity enum value.
 */
export function dataTypeFromString( dataType : string ) {

    if ( dataType == null ) {
        return null;
    }

    switch ( dataType.toUpperCase().trim() ) {
        case "BOOLEAN":
            return EDataType.BOOLEAN;
        case "DATETIME":
            return EDataType.DATETIME;
        case "FLOAT64":
            return EDataType.FLOAT64;
        case "INTEGER32":
            return EDataType.INTEGER32;
        case "STRING":
            return EDataType.STRING;
        case "UUID":
            return EDataType.UUID;
        default:
            throw new Error( "Undefined data type: '" + dataType + "'." );
    }


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

/**
 * Converts a string value to a dependency path.
 * @param dependencyPath the string - should be "DIRECT", or "TRANSITIVE".
 * @returns {*} the corresponding dependency path enum value.
 */
export function dependencyPathFromString( dependencyPath : string ) {

    if ( dependencyPath == null ) {
        return null;
    }

    switch ( dependencyPath.toUpperCase().trim() ) {
        case "DIRECT":
            return EDependencyDepth.DIRECT;
        case "TRANSITIVE":
            return EDependencyDepth.TRANSITIVE;
        default:
            throw new Error( "Undefined dependency path: '" + dependencyPath + "'." );
    }

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

/**
 * Converts a string value to a label defaulting.
 * @param labelDefaulting the string - should be "NOT_DEFAULT_LABEL", or "DEFAULT_LABEL".
 * @returns {*} the corresponding label defaulting enum value.
 */
export function labelDefaultingFromString( labelDefaulting : string ) {

    if ( labelDefaulting == null ) {
        return null;
    }

    switch ( labelDefaulting.toUpperCase().trim() ) {
        case "NOT_DEFAULT_LABEL":
            return ELabelDefaulting.NOT_DEFAULT_LABEL;
        case "DEFAULT_LABEL":
            return ELabelDefaulting.DEFAULT_LABEL;
        default:
            throw new Error( "Undefined label defaulting: '" + labelDefaulting + "'." );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Enumeration of possible constraints for multi edges in an edge type.
 */
export enum EMultiEdgedness {

    /** An abstract edge type does not constrain the multi-edgedness of its edges. */
    UNCONSTRAINED,

    /** An edge type allows multiple edges between two given vertexes. */
    MULTI_EDGES_ALLOWED,

    /** An edge type disallows multiple edges between two given vertexes. */
    MULTI_EDGES_NOT_ALLOWED

}

/**
 * Converts a string value to a multi-edgedness.
 * @param multiEdgedness the string - should be "UNCONSTRAINED", "MULTI_EDGES_ALLOWED", or "MULTI_EDGES_NOT_ALLOWED".
 * @returns {*} the corresponding cyclicity enum value.
 */
export function multiEdgednessFromString( multiEdgedness : string ) {

    if ( multiEdgedness == null ) {
        return null;
    }

    switch ( multiEdgedness.toUpperCase().trim() ) {
        case "UNCONSTRAINED":
            return EMultiEdgedness.UNCONSTRAINED;
        case "MULTI_EDGES_ALLOWED":
           return EMultiEdgedness.MULTI_EDGES_ALLOWED;
        case "MULTI_EDGES_NOT_ALLOWED":
            return EMultiEdgedness.MULTI_EDGES_NOT_ALLOWED;
        default:
            throw new Error( "Undefined multiEdgedness: '" + multiEdgedness + "'." );
    }

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

/**
 * Converts a string value to a self-looping value.
 * @param selfLooping the string - should be "UNCONSTRAINED", "SELF_LOOPS_ALLOWED", or "SELF_LOOPS_NOT_ALLOWED".
 * @returns {*} the corresponding cyclicity enum value.
 */
export function selfLoopingFromString( selfLooping : string ) {

    if ( selfLooping == null ) {
        return null;
    }

    switch ( selfLooping.toUpperCase().trim() ) {
        case "UNCONSTRAINED":
            return ESelfLooping.UNCONSTRAINED;
        case "SELF_LOOPS_ALLOWED":
            return ESelfLooping.SELF_LOOPS_ALLOWED;
        case "SELF_LOOPS_NOT_ALLOWED":
            return ESelfLooping.SELF_LOOPS_NOT_ALLOWED;
        default:
            throw new Error( "Undefined selfLooping: '" + selfLooping + "'." );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface INamedElement {
    // forward
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared top level base interface for metadata elements.
 */
export interface IDocumentedElement extends IElement {

    /** The parent element of this element. */
    readonly parent : INamedElement;

    /** An array of elements from the root package down to but not including this element. */
    readonly parentsInPath : IDocumentedElement[];

}

/**
 * Type name for documented elements.
 * @type {string}
 */
export const DOCUMENTED_ELEMENT = 'Element/Barlom/DocumentedElement';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Shared base interface for metadata elements with names.
 */
export interface INamedElement extends IDocumentedElement {

    /** the name of this element. */
    readonly name: string;

    /**
     * Returns the fully qualified path to this element.
     */
    readonly path : string;

}

/**
 * Type name for named elements.
 * @type {string}
 */
export const NAMED_ELEMENT = 'Element/Barlom/NamedElement';

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
    readonly parentPackage : IPackage;

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
export const PACKAGED_ELEMENT = 'Element/Barlom/PackagedElement';

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
    readonly abstractness : EAbstractness;

    /**
     * @return the defined attributes of this vertex type.
     */
    readonly attributes : IVertexAttributeDecl[];

    /**
     * @return the super type of this vertex type.
     */
    readonly superType : IVertexType;

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
export const VERTEX_TYPE = 'Element/Barlom/VertexType';

/**
 * Type name for root vertex types.
 * @type {string}
 */
export const ROOT_VERTEX_TYPE = 'Element/Barlom/RootVertexType';

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
    readonly abstractness : EAbstractness;

    /**
     * @return the defined attributes of this edge type.
     */
    readonly attributes : IEdgeAttributeDecl[];

    /**
     * @return whether edges of this type can form a cyclic graph.
     */
    readonly cyclicity : ECyclicity;

    /**
     * @return whether this edge type is abstract, i.e has no concrete instances. Note that a super type must be
     * abstract.
     */
    readonly isAbstract : boolean;

    /**
     * @return whether graphs formed by this edge type are simple, i.e. have neither self-loops not multi-edges.
     */
    readonly isSimple : boolean;

    /**
     * @return whether edges of this type must be unique between any two given vertexes.
     */
    readonly multiEdgedness : EMultiEdgedness ;

    /**
     * @return whether edges of this type can connect a vertex to itself.
     */
    readonly selfLooping : ESelfLooping ;

    /**
     * @return the super type of this edge type.
     */
    readonly superEdgeType : IEdgeType;

}

/**
 * Type name for edge types.
 * @type {string}
 */
export const EDGE_TYPE = 'Element/Barlom/EdgeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Base interface for an abstract attribute type.
 */
export interface IAttributeType extends IPackagedElement {

    /**
     * @return the fundamental data type for attributes of this type.
     */
    readonly dataType : EDataType;

}

/**
 * Type name for attribute types.
 * @type {string}
 */
export const ATTRIBUTE_TYPE = 'Element/Barlom/AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Top level interface to a directed edge type.
 */
export interface IDirectedEdgeType extends IEdgeType {

    /**
     * @return the name of the role for the vertex at the head of edges of this type.
     */
    readonly headRoleName : string;

    /**
     * @return the destination vertex type for edges of this type.
     */
    readonly headVertexType : IVertexType;

    /**
     * The maximum number of edges into the head vertex for an edge of this type.
     *
     * @return the maximum head in degree (optional).
     */
    readonly maxHeadInDegree : number;

    /**
     * The maximum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the maximum tail out degree (optional).
     */
    readonly maxTailOutDegree : number;

    /**
     * The minimum number of edges into the head vertex for an edge of this type.
     *
     * @return the minimum head in degree (optional).
     */
    readonly minHeadInDegree : number;

    /**
     * The minimum number of edges out of the tail vertex for an edge of this type.
     *
     * @return the minimum tail out degree (optional).
     */
    readonly minTailOutDegree : number;

    /**
     * @return the super type of this edge type.
     */
    readonly superType : IDirectedEdgeType;

    /**
     * @return the name of the role for the vertex at the tail of edges of this type.
     */
    readonly tailRoleName : string;

    /**
     * @return the origin vertex type for edges of this type.
     */
    readonly tailVertexType : IVertexType;

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
export const DIRECTED_EDGE_TYPE = 'Element/Barlom/DirectedEdgeType';

/**
 * Type name for a root directed edge type.
 * @type {string}
 */
export const ROOT_DIRECTED_EDGE_TYPE = 'Element/Barlom/RootDirectedEdgeType';

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
    readonly maxDegree : number;

    /**
     * The minimum number of edges connected to any vertex for an edge of this type.
     *
     * @return the minimum degree (optional).
     */
    readonly minDegree : number;

    /**
     * @return the super type of this edge type.
     */
    readonly superType : IUndirectedEdgeType;

    /**
     * @return the vertex type for edges of this type.
     */
    readonly vertexType : IVertexType;

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
export const UNDIRECTED_EDGE_TYPE = 'Element/Barlom/UndirectedEdgeType';

/**
 * Type name for a root undirected edge type.
 * @type {string}
 */
export const ROOT_UNDIRECTED_EDGE_TYPE = 'Element/Barlom/RootUndirectedEdgeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a package dependency.
 */
export interface IPackageDependency extends IDocumentedElement {

    /**
     * @return the package that makes use of the supplier package.
     */
    readonly clientPackage : IPackage;


    /**
     * @return the package that is depended upon.
     */
    readonly supplierPackage : IPackage;

}

/**
 * Type name for package dependencies.
 * @type {string}
 */
export const PACKAGE_DEPENDENCY = 'Element/Barlom/PackageDependency';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

export interface IPackage extends IPackagedElement {

    /**
     * @return the attribute types that are children of this package.
     */
    readonly attributeTypes : IAttributeType[];

    /**
     * @return the packages that are children of this one.
     */
    readonly childPackages : IPackage[];

    /**
     * @return the directed edge types that are children of this package.
     */
    readonly directedEdgeTypes: IDirectedEdgeType[];

    /**
     * @return the edge types that are children of this package.
     */
    readonly edgeTypes : IEdgeType[];

    /**
     * @return the undirected edge types that are children of this package.
     */
    readonly undirectedEdgeTypes: IUndirectedEdgeType[];

    /**
     * @return the vertex types that are children of this package.
     */
    readonly vertexTypes : IVertexType[];

    /**
     * Finds the child package with given name (or null if not found).
     * @param name
     */
    findOptionalChildPackageByName( name : string ) : IPackage;

    /**
     * Finds the directed edge type with given name (or null if not found).
     * @param name
     */
    findOptionalDirectedEdgeTypeByName( name : string ) : IDirectedEdgeType;

    /**
     * Finds the packaged element with given name (or null if not found).
     * @param name
     */
    findOptionalPackagedElementByName( name : string ) : IPackagedElement;

    /**
     * Finds the undirected edge type with given name (or null if not found).
     * @param name
     */
    findOptionalUndirectedEdgeTypeByName( name : string ) : IUndirectedEdgeType;

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
export const PACKAGE = 'Element/Barlom/Package';

/**
 * Type name for root packages.
 * @type {string}
 */
export const ROOT_PACKAGE = 'Element/Barlom/RootPackage';

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
    readonly defaultValue : boolean;

}

/**
 * Type name for boolean attributes.
 * @type {string}
 */
export const BOOLEAN_ATTRIBUTE_TYPE = 'Element/Barlom/BooleanAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a date/time attribute type.
 */
export interface IDateTimeAttributeType extends IAttributeType {

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    readonly maxValue : Date;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    readonly minValue : Date;

}

/**
 * Type name for date/time attribute types.
 * @type {string}
 */
export const DATE_TIME_ATTRIBUTE_TYPE = 'Element/Barlom/DateTimeAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to 64-bit floating point attribute types.
 */
export interface IFloat64AttributeType extends IAttributeType {

    /**
     * @return the default value for attributes of this type.
     */
    readonly defaultValue : number;

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    readonly maxValue : number;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    readonly minValue : number;

}

/**
 * Type name for 64-bit floating point attribute types.
 * @type {string}
 */
export const FLOAT64_ATTRIBUTE_TYPE = 'Element/Barlom/Float64AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to 32-bit integer attribute types.
 */
export interface IInteger32AttributeType extends IAttributeType {

    /**
     * @return the default value for attributes of this type.
     */
    readonly defaultValue : number;

    /**
     * @return the minimum possible value (inclusive) for attributes of this type.
     */
    readonly maxValue : number;

    /**
     * @return the maximum possible value (inclusive) for attributes of this type.
     */
    readonly minValue : number;

}

/**
 * Type name for 32-bit integer attribute types.
 * @type {string}
 */
export const INTEGER32_ATTRIBUTE_TYPE = 'Element/Barlom/Integer32AttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to string attribute types.
 */
export interface IStringAttributeType extends IAttributeType {

    /**
     * @return the maximum allowed length for attributes of this type.
     */
    readonly maxLength : number;

    /**
     * @return the minimum allowed length for attributes of this type.
     */
    readonly minLength : number;

    /**
     * @return a regular expression that must be matched by values of this attribute type.
     */
    readonly regexPattern : RegExp;

}

/**
 * Type name for string attribute types.
 * @type {string}
 */
export const STRING_ATTRIBUTE_TYPE = 'Element/Barlom/StringAttributeType';

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
export const UUID_ATTRIBUTE_TYPE = 'Element/Barlom/UuidAttributeType';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to an edge attribute declaration.
 */
export interface IEdgeAttributeDecl extends INamedElement {

    /**
     * @return whether this is a required attribute.
     */
    readonly optionality : EOptionality;

    /**
     * @return the parent of this attribute.
     */
    readonly parentEdgeType : IEdgeType;

    /**
     * @return the type of this attribute.
     */
    readonly type : IAttributeType;

}

/**
 * Type name for edge attribute declarations.
 * @type {string}
 */
export const EDGE_ATTRIBUTE_DECL = 'Element/Barlom/EdgeAttributeDecl';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a vertex attribute declaration.
 */
export interface IVertexAttributeDecl extends INamedElement {

    /**
     * @return whether this is the default label for the vertex.
     */
    readonly labelDefaulting : ELabelDefaulting;

    /**
     * @return whether this is a required attribute.
     */
    readonly optionality : EOptionality;

    /**
     * @return the parent of this attribute.
     */
    readonly parentVertexType : IVertexType;

    /**
     * @return the type of this attribute.
     */
    readonly type : IAttributeType;

}

/**
 * Type name for vertex type attribute declarations.
 * @type {string}
 */
export const VERTEX_ATTRIBUTE_DECL = 'Element/Barlom/VertexAttributeDecl';

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
