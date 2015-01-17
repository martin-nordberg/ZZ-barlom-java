//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.spi;

import org.grestler.metamodel.api.IMetamodelRepository;
import org.grestler.metamodel.api.attributes.IBooleanAttributeType;
import org.grestler.metamodel.api.attributes.IDateTimeAttributeType;
import org.grestler.metamodel.api.attributes.IFloat64AttributeType;
import org.grestler.metamodel.api.attributes.IInteger32AttributeType;
import org.grestler.metamodel.api.attributes.IStringAttributeType;
import org.grestler.metamodel.api.attributes.IUuidAttributeType;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import java.time.LocalDateTime;
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
     * Loads a queried boolean attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     *
     * @return the loaded attribute type.
     */
    IBooleanAttributeType loadBooleanAttributeType(
        UUID id, IPackage parentPackage, String name
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
        UUID id, IPackage parentPackage, String name, Optional<LocalDateTime> minValue, Optional<LocalDateTime> maxValue
    );

    /**
     * Loads a queried edge type into the repository.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the parent package for the edge type.
     * @param name           the name of the edge type.
     * @param superType      the super type of the edge type.
     * @param fromVertexType the vertex type at the start of edges of the new edge type.
     * @param toVertexType   the vertex type at the end of edges of the new edge type.
     *
     * @return the loaded edge type.
     */
    IEdgeType loadEdgeType(
        UUID id,
        IPackage parentPackage,
        String name,
        IEdgeType superType,
        IVertexType fromVertexType,
        IVertexType toVertexType
    );

    /**
     * Loads a queried 64-bit floating point attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes with this type.
     * @param maxValue      the minimum value for attributes with this type.
     *
     * @return the loaded attribute type.
     */
    IFloat64AttributeType loadFloat64AttributeType(
        UUID id, IPackage parentPackage, String name, OptionalDouble minValue, OptionalDouble maxValue
    );

    /**
     * Loads a queried 32-bit integer attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param minValue      the minimum value for attributes with this type.
     * @param maxValue      the minimum value for attributes with this type.
     *
     * @return the loaded attribute type.
     */
    IInteger32AttributeType loadInteger32AttributeType(
        UUID id, IPackage parentPackage, String name, OptionalInt minValue, OptionalInt maxValue
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
     * Loads a queried string attribute type into the repository.
     *
     * @param id            the unique ID of the attribute type.
     * @param parentPackage the parent package for the attribute type.
     * @param name          the name of the attribute type.
     * @param maxLength     the maximum length for attributes with this type.
     * @param regexPattern  a regular expression that must be matched by attributes with this type.
     *
     * @return the loaded attribute type.
     */
    IStringAttributeType loadStringAttributeType(
        UUID id, IPackage parentPackage, String name, int maxLength, Optional<String> regexPattern
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
     * Loads a queried vertex type into the repository.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the parent package for the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type of the vertex type.
     *
     * @return the loaded vertex type.
     */
    IVertexType loadVertexType( UUID id, IPackage parentPackage, String name, IVertexType superType );

}
