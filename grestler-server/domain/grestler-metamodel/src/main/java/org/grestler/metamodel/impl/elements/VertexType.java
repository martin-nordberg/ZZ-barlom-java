//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for vertex types.
 */
public final class VertexType
    extends Element
    implements IVertexType, IVertexTypeSpi {

    /**
     * Constructs a new vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type.
     */
    public VertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType
    ) {

        super( id, parentPackage, name );

        this.superType = superType;
        this.attributes = new ArrayList<>();

        ( (IPackageSpi) parentPackage ).addVertexType( this );

    }

    @Override
    public boolean isSubTypeOf( IVertexType vertexType ) {
        return this == vertexType || this.getSuperType().get().isSubTypeOf( vertexType );
    }

    @Override
    public Iterable<IVertexAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.of( this.superType );
    }

    @Override
    public void addAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    /** The attributes of this vertex type. */
    private final List<IVertexAttributeDecl> attributes;

    /** The super type of this vertex type. */
    private final IVertexType superType;

}
