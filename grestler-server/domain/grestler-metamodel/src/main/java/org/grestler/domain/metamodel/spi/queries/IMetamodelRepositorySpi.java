//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.spi.queries;

import org.grestler.domain.metamodel.api.elements.*;
import org.grestler.domain.metamodel.api.queries.IMetamodelRepository;

import java.util.UUID;

/**
 * Interface to a metamodel repository including SPI methods..
 */
public interface IMetamodelRepositorySpi
    extends IMetamodelRepository {

    /**
     * Loads a queried boolean attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IBooleanAttributeType loadBooleanAttributeType(
        IBooleanAttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried date/time attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IDateTimeAttributeType loadDateTimeAttributeType(
        IDateTimeAttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried directed edge type into the repository.
     *
     * @param record         the attributes of the edge type.
     * @param parentPackage  the parent package for the edge type.
     * @param superType      the super type of the edge type.
     * @param tailVertexType the vertex type at the start of edges of the new edge type.
     * @param headVertexType the vertex type at the end of edges of the new edge type.
     *
     * @return the loaded edge type.
     */
    IDirectedEdgeType loadDirectedEdgeType(
        IDirectedEdgeType.Record record,
        IPackage parentPackage,
        IDirectedEdgeType superType,
        IVertexType tailVertexType,
        IVertexType headVertexType
    );

    /**
     * Loads a queried edge attribute declaration into the repository.
     *
     * @param record         the unique ID of the attribute declaration.
     * @param parentEdgeType the parent edge type.
     * @param type           the type of the attribute.
     *
     * @return the loaded edge attribute.
     */
    IEdgeAttributeDecl loadEdgeAttributeDecl(
        IEdgeAttributeDecl.Record record, IEdgeType parentEdgeType, IAttributeType type
    );

    /**
     * Loads a queried 64-bit floating point attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IFloat64AttributeType loadFloat64AttributeType(
        IFloat64AttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried 32-bit integer attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IInteger32AttributeType loadInteger32AttributeType(
        IInteger32AttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried package into the repository.
     *
     * @param record        the attributes of the package.
     * @param parentPackage the parent package for the package.
     *
     * @return the loaded package.
     */
    IPackage loadPackage( IPackage.Record record, IPackage parentPackage );

    /**
     * Loads a queried package dependency into the repository.
     *
     * @param record          the attributes of the dependency itself.
     * @param clientPackage   the package that uses another package.
     * @param supplierPackage the package used.
     *
     * @return the new dependency.
     */
    IPackageDependency loadPackageDependency(
        IPackageDependency.Record record,
        IPackage clientPackage,
        IPackage supplierPackage
    );

    /**
     * Loads the queried root directed edge type into the repository.
     *
     * @param id            the unique ID of the root edge type.
     * @param parentPackage the parent package for the root edge type.
     *
     * @return the loaded edge type.
     */
    IDirectedEdgeType loadRootDirectedEdgeType(
        UUID id, IPackage parentPackage
    );

    /**
     * Loads the queried root package into the repository.
     *
     * @param id the unique ID of the package.
     *
     * @return the loaded package.
     */
    IPackage loadRootPackage( UUID id );

    /**
     * Loads the queried root undirected edge type into the repository.
     *
     * @param id            the unique ID of the root edge type.
     * @param parentPackage the parent package for the root edge type.
     *
     * @return the loaded edge type.
     */
    IUndirectedEdgeType loadRootUndirectedEdgeType(
        UUID id, IPackage parentPackage
    );

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the parent (root) package for the vertex type.
     *
     * @return the loaded vertex type.
     */
    IVertexType loadRootVertexType( UUID id, IPackage parentPackage );

    /**
     * Loads a queried string attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IStringAttributeType loadStringAttributeType(
        IStringAttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried undirected edge type into the repository.
     *
     * @param record        the unique ID of the edge type.
     * @param parentPackage the parent package for the edge type.
     * @param superType     the super type of the edge type.
     * @param vertexType    the vertex type at the start of edges of the new edge type.
     *
     * @return the loaded edge type.
     */
    IUndirectedEdgeType loadUndirectedEdgeType(
        IUndirectedEdgeType.Record record, IPackage parentPackage, IUndirectedEdgeType superType, IVertexType vertexType
    );

    /**
     * Loads a queried UUID attribute type into the repository.
     *
     * @param record        the attributes of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     *
     * @return the loaded attribute type.
     */
    IUuidAttributeType loadUuidAttributeType(
        IUuidAttributeType.Record record, IPackage parentPackage
    );

    /**
     * Loads a queried vertex attribute declaration into the repository.
     *
     * @param record           the attributes of the attribute declaration.
     * @param parentVertexType the parent vertex type.
     * @param type             the type of the attribute.
     *
     * @return the loaded vertex attribute.
     */
    IVertexAttributeDecl loadVertexAttributeDecl(
        IVertexAttributeDecl.Record record, IVertexType parentVertexType, IAttributeType type
    );

    /**
     * Loads a queried vertex type into the repository.
     *
     * @param record        the unique ID of the vertex type.
     * @param parentPackage the parent package for the vertex type.
     * @param superType     the super type of the vertex type.
     *
     * @return the loaded vertex type.
     */
    IVertexType loadVertexType(
        IVertexType.Record record, IPackage parentPackage, IVertexType superType
    );

}
