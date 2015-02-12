//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.EAbstractness;
import org.grestler.metamodel.api.elements.IPackage;
import org.grestler.metamodel.api.elements.IVertexAttributeDecl;
import org.grestler.metamodel.api.elements.IVertexType;

import javax.json.stream.JsonGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for vertex types.
 */
public final class VertexType
    extends PackagedElement
    implements IVertexType, IVertexTypeUnderAssembly {

    /**
     * Constructs a new vertex type.
     *
     * @param id            the unique ID of the vertex type.
     * @param parentPackage the package containing the vertex type.
     * @param name          the name of the vertex type.
     * @param superType     the super type.
     * @param abstractness  whether the vertex type is abstract.
     */
    public VertexType(
        UUID id, IPackage parentPackage, String name, IVertexType superType, EAbstractness abstractness
    ) {

        super( id, parentPackage, name );

        this.superType = superType;
        this.abstractness = abstractness;
        this.attributes = new ArrayList<>();

        ( (IPackageUnderAssembly) parentPackage ).addVertexType( this );

    }

    @Override
    public void addAttribute( IVertexAttributeDecl attribute ) {
        this.attributes.add( attribute );
    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "superTypeId", this.superType.getId().toString() )
            .write( "abstractness", this.abstractness.name() );

        // TODO: attribute declarations
    }

    @Override
    public EAbstractness getAbstractness() {
        return this.abstractness;
    }

    @Override
    public List<IVertexAttributeDecl> getAttributes() {
        return this.attributes;
    }

    @Override
    public Optional<IVertexType> getSuperType() {
        return Optional.of( this.superType );
    }

    @Override
    public boolean isSubTypeOf( IVertexType vertexType ) {
        return this == vertexType || this.getSuperType().get().isSubTypeOf( vertexType );
    }

    /** Whether this vertex type is abstract. */
    private final EAbstractness abstractness;

    /** The attributes of this vertex type. */
    private final List<IVertexAttributeDecl> attributes;

    /** The super type of this vertex type. */
    private final IVertexType superType;

}
