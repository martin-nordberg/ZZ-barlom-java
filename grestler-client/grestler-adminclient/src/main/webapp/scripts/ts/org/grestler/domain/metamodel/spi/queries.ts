//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

/**
 * Module: org/grestler/domain/metamodel/spi/queries
 */

import api_elements = require( '../api/elements' );
import api_queries = require( '../api/queries' );
import values = require( '../../../infrastructure/utilities/values' );

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface to a metamodel repository including SPI methods..
 */
export interface IMetamodelRepositorySpi extends api_queries.IMetamodelRepository {

    /**
     * Loads a queried boolean attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param defaultValue  the default value for attributes of this type.
     *
     * @return the loaded attribute type.
     */
    loadBooleanAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string, defaultValue : boolean
    ) : api_elements.IBooleanAttributeType;

    /**
     * Loads a queried date/time attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes with this type.
     * @param maxValue      the minimum value for attributes with this type.
     *
     * @return the loaded attribute type.
     */
    loadDateTimeAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string, minValue : Date, maxValue : Date
    ) : api_elements.IDateTimeAttributeType;

    /**
     * Loads a queried directed edge type into the repository.
     *
     * @param id               the unique ID of the edge type.
     * @param parentPackage    the parent package for the edge type.
     * @param name             the name of the edge type.
     * @param superType        the super type of the edge type.
     * @param abstractness     whether the edge type is abstract or concrete.
     * @param cyclicity        whether edges of the new type are allowed to be cyclic.
     * @param multiEdgedness   whether the new edge type allows multiple edges between two given vertexes.
     * @param selfLooping      whether the new edge type allows edges from a vertex to itself.
     * @param tailVertexType   the vertex type at the start of edges of the new edge type.
     * @param headVertexType   the vertex type at the end of edges of the new edge type.
     * @param tailRoleName     the role name for the tail vertex of edges of the new type.
     * @param headRoleName     the role name for the head vertex of edges of the new type.
     * @param minTailOutDegree the minimum out-degree for the tail vertex of an edge of this type.
     * @param maxTailOutDegree the maximum out-degree for the tail vertex of an edge of this type.
     * @param minHeadInDegree  the minimum in-degree for the head vertex of an edge of this type.
     * @param maxHeadInDegree  the maximum in-degree for the head vertex of an edge of this type.
     *
     * @return the loaded edge type.
     */
    loadDirectedEdgeType(
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
    ) : api_elements.IDirectedEdgeType;

    /**
     * Loads a queried edge attribute declaration into the repository.
     *
     * @param id             the unique ID of the attribute declaration.
     * @param parentEdgeType the parent edge type.
     * @param name           the name of the attribute.
     * @param type           the type of the attribute.
     * @param optionality    whether this attribute is required.
     *
     * @return the loaded edge attribute.
     */
    loadEdgeAttributeDecl(
        id : string,
        parentEdgeType : api_elements.IEdgeType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality
    ) : api_elements.IEdgeAttributeDecl;

    /**
     * Loads a queried 64-bit floating point attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes with this type.
     * @param maxValue      the minimum value for attributes with this type.
     * @param defaultValue  the default value for attribuets of this type.
     *
     * @return the loaded attribute type.
     */
    loadFloat64AttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) : api_elements.IFloat64AttributeType;

    /**
     * Loads a queried 32-bit integer attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes with this type.
     * @param maxValue      the minimum value for attributes with this type.
     * @param defaultValue  the default value for attributes of this type.
     *
     * @return the loaded attribute type.
     */
    loadInteger32AttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minValue : number,
        maxValue : number,
        defaultValue : number
    ) : api_elements.IInteger32AttributeType;

    /**
     * Loads a queried package into the repository.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package for the package.
     * @param name          the name of the package.
     *
     * @return the loaded package.
     */
    loadPackage( id : string, parentPackage : api_elements.IPackage, name : string ) : api_elements.IPackage;

    /**
     * Loads a queried package dependency into the repository.
     *
     * @param id              the unique ID of the dependency itself.
     * @param clientPackage   the package that uses another package.
     * @param supplierPackage the package used.
     *
     * @return the new dependency.
     */
    loadPackageDependency(
        id : string,
        clientPackage : api_elements.IPackage,
        supplierPackage : api_elements.IPackage
    ) : api_elements.IPackageDependency;

    /**
     * Loads the queried root directed edge type into the repository.
     *
     * @param id            the unique ID of the root edge type.
     * @param parentPackage the parent package for the root edge type.
     *
     * @return the loaded edge type.
     */
    loadRootDirectedEdgeType(
        id : string, parentPackage : api_elements.IPackage
    ) : api_elements.IDirectedEdgeType;

    /**
     * Loads the queried root package into the repository.
     *
     * @param id the unique ID of the package.
     *
     * @return the loaded package.
     */
    loadRootPackage( id : string ) : api_elements.IPackage;

    /**
     * Loads the queried root undirected edge type into the repository.
     *
     * @param id            the unique ID of the root edge type.
     * @param parentPackage the parent package for the root edge type.
     *
     * @return the loaded edge type.
     */
    loadRootUndirectedEdgeType(
        id : string, parentPackage : api_elements.IPackage
    ) : api_elements.IUndirectedEdgeType;

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the parent (root) package for the vertex type.
     *
     * @return the loaded vertex type.
     */
    loadRootVertexType( id : string, parentPackage : api_elements.IPackage ) : api_elements.IVertexType;

    /**
     * Loads a queried string attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minLength     the minimum length for attributes with this type.
     * @param maxLength     the maximum length for attributes with this type.
     * @param regexPattern  a regular expression that must be matched by attributes with this type.
     *
     * @return the loaded attribute type.
     */
    loadStringAttributeType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        minLength : number,
        maxLength : number,
        regexPattern : string
    ) : api_elements.IStringAttributeType;

    /**
     * Loads a queried undirected edge type into the repository.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the parent package for the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type of the edge type.
     * @param abstractness   whether the edge type is abstract or concrete.
     * @param cyclicity      whether edges of the new type are allowed to be cyclic.
     * @param multiEdgedness whether the new edge type allows multiple edges between two given vertexes.
     * @param selfLooping    whether the new edge type allows edges from a vertex to itself.
     * @param vertexType     the vertex type at the start of edges of the new edge type.
     * @param minDegree      the minimum degree for any vertex of an edge of this type.
     * @param maxDegree      the maximum degree for any vertex of an edge of this type.
     *
     * @return the loaded edge type.
     */
    loadUndirectedEdgeType(
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
    ) : api_elements.IUndirectedEdgeType;

    /**
     * Loads a queried UUID attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     *
     * @return the loaded attribute type.
     */
    loadUuidAttributeType(
        id : string, parentPackage : api_elements.IPackage, name : string
    ) : api_elements.IUuidAttributeType;

    /**
     * Loads a queried vertex attribute declaration into the repository.
     *
     * @param id               the unique ID of the attribute declaration.
     * @param parentVertexType the parent vertex type.
     * @param name             the name of the attribute.
     * @param type             the type of the attribute.
     * @param optionality      whether this attribute is required.
     * @param labelDefaulting  whether this is the default label for vertexes of the parent type.
     *
     * @return the loaded vertex attribute.
     */
    loadVertexAttributeDecl(
        id : string,
        parentVertexType : api_elements.IVertexType,
        name : string,
        type : api_elements.IAttributeType,
        optionality : api_elements.EAttributeOptionality,
        labelDefaulting : api_elements.ELabelDefaulting
    ) : api_elements.IVertexAttributeDecl;

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the parent package for the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type of the vertex type.
     * @param abstractness  whether the vertex type is abstract.
     *
     * @return the loaded vertex type.
     */
    loadVertexType(
        id : string,
        parentPackage : api_elements.IPackage,
        name : string,
        superType : api_elements.IVertexType,
        abstractness : api_elements.EAbstractness
    ) : api_elements.IVertexType;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface for a back end attribute declaration loader.
 */
export interface IAttributeDeclLoader {

    /**
     * Loads all attribute declarations into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllAttributeDecls( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining attribute type queries.
 */
export interface IAttributeTypeLoader {

    /**
     * Loads all attribute types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllAttributeTypes( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining directed edge type queries.
 */
export interface IDirectedEdgeTypeLoader {

    /**
     * Loads all directed edge types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllDirectedEdgeTypes( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining package dependency queries.
 */
export interface IPackageDependencyLoader {

    /**
     * Loads all package dependencies into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllPackageDependencies( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining package queries.
 */
export interface IPackageLoader {

    /**
     * Loads all packages into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllPackages( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining undirected edge type queries.
 */
export interface IUndirectedEdgeTypeLoader {

    /**
     * Loads all undirected edge types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllUndirectedEdgeTypes( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Interface defining vertex type queries.
 */
export interface IVertexTypeLoader {

    /**
     * Loads all vertex types into the metamodel repository.
     *
     * @param repository the repository to be loaded.
     */
    loadAllVertexTypes( repository : IMetamodelRepositorySpi ) : Promise<values.ENothing>;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
