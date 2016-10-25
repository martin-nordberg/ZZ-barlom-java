//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.metamodel.impl.elements;

import org.barlom.domain.metamodel.api.elements.EAbstractness;
import org.barlom.domain.metamodel.api.elements.ECyclicity;
import org.barlom.domain.metamodel.api.elements.EMultiEdgedness;
import org.barlom.domain.metamodel.api.elements.ESelfLooping;
import org.barlom.domain.metamodel.api.elements.IEdgeAttributeDecl;
import org.barlom.domain.metamodel.api.elements.IEdgeType;
import org.barlom.domain.metamodel.api.elements.IPackage;
import org.barlom.domain.metamodel.api.elements.IUndirectedEdgeType;
import org.barlom.domain.metamodel.api.elements.IVertexType;
import org.barlom.infrastructure.utilities.collections.ISizedIterable;
import org.barlom.infrastructure.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation of the top-level root directed edge type.
 */
public class RootUndirectedEdgeType
    implements IUndirectedEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new root directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param rootVertexType the root vertex type connected by the edge type.
     */
    public RootUndirectedEdgeType(
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
            .write( "vertexTypeId", this.rootVertexType.getId().toString() );
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
    public UUID getId() {
        return this.id;
    }

    @Override
    public OptionalInt getMaxDegree() {
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt getMinDegree() {
        return OptionalInt.empty();
    }

    @Override
    public EMultiEdgedness getMultiEdgedness() {
        return EMultiEdgedness.UNCONSTRAINED;
    }

    @Override
    public String getName() {
        return "Undirected Edge";
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
    public Optional<IUndirectedEdgeType> getSuperType() {
        return Optional.empty();
    }

    @Override
    public IVertexType getVertexType() {
        return this.rootVertexType;
    }

    @Override
    public boolean isSubTypeOf( IUndirectedEdgeType edgeType ) {
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
