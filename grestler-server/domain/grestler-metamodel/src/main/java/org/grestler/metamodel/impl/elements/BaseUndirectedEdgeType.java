//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.ECyclicity;
import org.grestler.metamodel.api.elements.EMultiEdgedness;
import org.grestler.metamodel.api.elements.ESelfLooping;
import org.grestler.metamodel.api.elements.IEdgeAttributeDecl;
import org.grestler.metamodel.api.elements.IEdgeType;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IUndirectedEdgeType;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.collections.ISizedIterable;
import org.grestler.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Implementation of the top-level base directed edge type.
 */
public class BaseUndirectedEdgeType
    implements IUndirectedEdgeType, IEdgeTypeUnderAssembly {

    /**
     * Constructs a new base directed edge type.
     *
     * @param id             the unique ID of the edge type.
     * @param parentPackage  the package containing the edge type.
     * @param baseVertexType the base vertex type connected by the edge type.
     */
    public BaseUndirectedEdgeType(
        UUID id, IPackage parentPackage, IVertexType baseVertexType
    ) {

        this.id = id;
        this.parentPackage = parentPackage;
        this.baseVertexType = baseVertexType;

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
            .write( "name", "Vertex" )
            .write( "path", this.getPath() )
            .write( "vertexTypeId", this.baseVertexType.getId().toString() );
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
    public Optional<IEdgeType> getSuperEdgeType() {
        return Optional.empty();
    }

    @Override
    public Optional<IUndirectedEdgeType> getSuperType() {
        return Optional.empty();
    }

    @Override
    public IVertexType getVertexType() {
        return this.baseVertexType;
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

    private final IVertexType baseVertexType;

    private final UUID id;

    private final IPackage parentPackage;
}
