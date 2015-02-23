//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;
import org.grestler.utilities.collections.ISizedIterable;
import org.grestler.utilities.revisions.VArray;

import javax.json.stream.JsonGenerator;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the top-level base vertex type.
 */
public final class BaseVertexType
    implements IVertexType, IVertexTypeUnderAssembly {

    /**
     * Constructs a new root vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     */
    public BaseVertexType(
        UUID id, IPackage parentPackage
    ) {

        this.id = id;
        this.parentPackage = parentPackage;
        this.attributes = new VArray<>();

        ( (IPackageUnderAssembly) parentPackage ).addVertexType( this );

    }

    @Override
    public void addAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {
        json.write( "id", this.id.toString() )
            .write( "parentPackageId", this.parentPackage.getId().toString() )
            .write( "name", "Vertex" )
            .write( "path", this.getPath() );
    }

    @Override
    public EAbstractness getAbstractness() {
        return EAbstractness.ABSTRACT;
    }

    @Override
    public ISizedIterable<IVertexAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return "Vertex";
    }

    @Override
    public IPackage getParentPackage() {
        return this.parentPackage;
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.empty();
    }

    @Override
    public boolean isSubTypeOf( IVertexType vertexType ) {
        return this == vertexType;
    }

    @Override
    public void removeAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.remove( attribute );
    }

    /** The attributes of this vertex type. */
    private final VArray<IVertexAttributeDecl> attributes;

    /** The unique ID of this vertex type. */
    private final UUID id;

    /** The parent (root) package for this vertex type. */
    private final IPackage parentPackage;

}
