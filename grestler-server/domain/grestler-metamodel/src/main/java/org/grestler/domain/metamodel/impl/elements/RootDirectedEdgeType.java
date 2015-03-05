//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.EAbstractness;
import org.grestler.domain.metamodel.api.elements.ECyclicity;
import org.grestler.domain.metamodel.api.elements.EMultiEdgedness;
import org.grestler.domain.metamodel.api.elements.ESelfLooping;
import org.grestler.domain.metamodel.api.elements.IDirectedEdgeType;
import org.grestler.domain.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.domain.metamodel.api.elements.IEdgeType;
import org.grestler.domain.metamodel.api.elements.IPackage;
import org.grestler.domain.metamodel.api.elements.IVertexType;
import org.grestler.infrastructure.utilities.collections.ISizedIterable;
import org.grestler.infrastructure.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation of the top-level root directed edge type.
 */
public class RootDirectedEdgeType
    implements IDirectedEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new root directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param rootVertexType the root vertex type connected by the edge type.
     */
    public RootDirectedEdgeType(
        UUID id, IPackage parentPackage, IVertexType rootVertexType
    ) {

        this.id = id;
        this.parentPackage = parentPackage;
        this.rootVertexType = rootVertexType;

        this.attributes = new VArray<>();

        ( (IPackageUnderAssembly) parentPackage ).addChildElement( this );

    }

    @Override
    public void addAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() )
            .write( "parentPackageId", this.parentPackage.getId().toString() )
            .write( "name", this.getName() )
            .write( "path", this.getPath() )
            .write( "tailVertexTypeId", this.rootVertexType.getId().toString() )
            .write( "headVertexTypeId", this.rootVertexType.getId().toString() );
    }

    @Override
    public EAbstractness getAbstractness() {
        return EAbstractness.ABSTRACT;
    }

    @Override
    public ISizedIterable<IEdgeAttributeDecl> getAttributes() {
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
        return this.rootVertexType;
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
        return "Directed Edge";
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
    public Optional<IEdgeType> getSuperEdgeType() {
        return Optional.empty();
    }

    @Override
    public Optional<IDirectedEdgeType> getSuperType() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getTailRoleName() {
        return Optional.empty();
    }

    @Override
    public IVertexType getTailVertexType() {
        return this.rootVertexType;
    }

    @Override
    public boolean isSubTypeOf( IDirectedEdgeType edgeType ) {
        return this == edgeType;
    }

    @Override
    public void removeAttribute( IEdgeAttributeDecl attribute ) {
        this.attributes.remove( attribute );
    }

    private final VArray<IEdgeAttributeDecl> attributes;

    private final UUID id;

    private final IPackage parentPackage;

    private final IVertexType rootVertexType;
}
