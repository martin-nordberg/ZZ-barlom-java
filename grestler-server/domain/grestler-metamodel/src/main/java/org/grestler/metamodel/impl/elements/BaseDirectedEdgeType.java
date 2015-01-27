//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexType;

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation of the top-level base directed edge type.
 */
public class BaseDirectedEdgeType
    implements IDirectedEdgeType, IEdgeTypeSpi {

    /**
     * Constructs a new base directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param baseVertexType the base vertex type connected by the edge type.
     */
    public BaseDirectedEdgeType(
        UUID id, IPackage parentPackage, IVertexType baseVertexType
    ) {

        this.id = id;
        this.parentPackage = parentPackage;
        this.baseVertexType = baseVertexType;

        this.attributes = new ArrayList<>();

        ( (IPackageSpi) parentPackage ).addEdgeType( this );

    }

    @Override
    public void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() )
            .write( "parentPackageId", this.parentPackage.getId().toString() )
            .write( "name", "Vertex" )
            .write( "path", this.getPath() )
            .write( "tailVertexTypeId", this.baseVertexType.getId().toString() )
            .write( "headVertexTypeId", this.baseVertexType.getId().toString() );
    }

    @Override
    public EAbstractness getAbstractness() {
        return EAbstractness.ABSTRACT;
    }

    @Override
    public List<IEdgeAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public ECyclicity getCyclicity() {
        return ECyclicity.UNCONSTRAINED;
    }

    @Override
    public Optional<String> getHeadRoleName() {
        return Optional.empty();
    }

    @Override
    public IVertexType getHeadVertexType() {
        return this.baseVertexType;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public OptionalInt getMaxHeadInDegree() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt getMaxTailOutDegree() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt getMinHeadInDegree() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt getMinTailOutDegree() {
        return OptionalInt.empty();
    }

    @Override
    public EMultiEdgedness getMultiEdgedness() {
        return EMultiEdgedness.UNCONSTRAINED;
    }

    @Override
    public String getName() {
        return "Edge";
    }

    @Override
    public IPackage getParentPackage() {
        return this.parentPackage;
    }

    @Override
    public ESelfLooping getSelfLooping() {
        return ESelfLooping.UNCONSTRAINED;
    }

    @Override
    public Optional<IEdgeType> getSuperType() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getTailRoleName() {
        return Optional.empty();
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.baseVertexType;
    }

    @Override
    public boolean isSubTypeOf( IEdgeType edgeType ) {
        return this == edgeType;
    }

    private final List<IEdgeAttributeDecl> attributes;

    private final IVertexType baseVertexType;

    private final UUID id;

    private final IPackage parentPackage;
}
