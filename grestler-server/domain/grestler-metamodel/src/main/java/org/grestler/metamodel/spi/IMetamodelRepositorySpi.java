//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi;

import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.api.attributes.EAttributeOptionality;
import org.grestler.metamodel.api.attributes.IAttributeType;
import org.grestler.metamodel.api.attributes.IBooleanAttributeType;
import org.grestler.metamodel.api.attributes.IDateTimeAttributeType;
import org.grestler.metamodel.api.attributes.IFloat64AttributeType;
import org.grestler.metamodel.api.attributes.IInteger32AttributeType;
import org.grestler.metamodel.api.attributes.IStringAttributeType;
import org.grestler.metamodel.api.attributes.IUuidAttributeType;
import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.ELabelDefaulting;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IPackageDependency;
import org.grestler.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Interface to a metamodel repository including SPI methods..
 */
public interface IMetamodelRepositorySpi
    extends IMetamodelRepository {

    /**
     * Loads the queried base directed edge type into the repository.
     *
     * @param id            the unique ID of the base edge type.
     * @param parentPackage the parent package for the base edge type.
     *
     * @return the loaded edge type.
     */
    IDirectedEdgeType loadBaseDirectedEdgeType(
        UUID id, IPackage parentPackage
    );

    /**
     * Loads the queried base undirected edge type into the repository.
     *
     * @param id            the unique ID of the base edge type.
     * @param parentPackage the parent package for the base edge type.
     *
     * @return the loaded edge type.
     */
    IUndirectedEdgeType loadBaseUndirectedEdgeType(
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
    IVertexType loadBaseVertexType( UUID id, IPackage parentPackage );

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
    IBooleanAttributeType loadBooleanAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Boolean> defaultValue
    );

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
    IDateTimeAttributeType loadDateTimeAttributeType(
        UUID id, IPackage parentPackage, String name, Optional<Instant> minValue, Optional<Instant> maxValue
    );

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
    IDirectedEdgeType loadDirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
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
    );

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
    IEdgeAttributeDecl loadEdgeAttributeDecl(
        UUID id, IEdgeType parentEdgeType, String name, IAttributeType type, EAttributeOptionality optionality
    );

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
    IFloat64AttributeType loadFloat64AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalDouble minValue,
        OptionalDouble maxValue,
        OptionalDouble defaultValue
    );

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
    IInteger32AttributeType loadInteger32AttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minValue,
        OptionalInt maxValue,
        OptionalInt defaultValue
    );

    /**
     * Loads a queried package into the repository.
     *
     * @param id            the unique ID of the package.
     * @param parentPackage the parent package for the package.
     * @param name          the name of the package.
     *
     * @return the loaded package.
     */
    IPackage loadPackage( UUID id, IPackage parentPackage, String name );

    /**
     * Loads a queried package dependency into the repository.
     *
     * @param id              the unique ID of the dependency itself.
     * @param clientPackage   the package that uses another package.
     * @param supplierPackage the package used.
     *
     * @return the new dependency.
     */
    IPackageDependency loadPackageDependency( UUID id, IPackage clientPackage, IPackage supplierPackage );

    /**
     * Loads the queried root package into the repository.
     *
     * @param id the unique ID of the package.
     *
     * @return the loaded package.
     */
    IPackage loadRootPackage( UUID id );

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
    IStringAttributeType loadStringAttributeType(
        UUID id,
        IPackage parentPackage,
        String name,
        OptionalInt minLength,
        int maxLength,
        Optional<String> regexPattern
    );

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
    IUndirectedEdgeType loadUndirectedEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        EAbstractness abstractness,
        ECyclicity cyclicity,
        EMultiEdgedness multiEdgedness,
        ESelfLooping selfLooping,
        IVertexType vertexType,
        OptionalInt minDegree,
        OptionalInt maxDegree
    );

    /**
     * Loads a queried UUID attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     *
     * @return the loaded attribute type.
     */
    IUuidAttributeType loadUuidAttributeType(
        UUID id, IPackage parentPackage, String name
    );

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
    IVertexAttributeDecl loadVertexAttributeDecl(
        UUID id,
        IVertexType parentVertexType,
        String name,
        IAttributeType type,
        EAttributeOptionality optionality,
        ELabelDefaulting labelDefaulting
    );

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
    IVertexType loadVertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType, EAbstractness abstractness
    );

}
