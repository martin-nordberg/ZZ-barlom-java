//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.impl.elements;

import org.grestler.domain.metamodel.api.elements.INamedElement;
import org.grestler.infrastructure.utilities.revisions.V;

import javax.json.stream.JsonGenerator;

/**
 * Implementation of abstract named element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class NamedElement
    extends DocumentedElement
    implements INamedElement {

    /**
     * Constructs a new named model element.
     *
     * @param record the attributes of the element.
     */
    protected NamedElement( INamedElement.Record record ) {

        super( record );

        this.name = new V<>( record.name );

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "name", this.getName() ).write( "path", this.getPath() );

    }

    @Override
    public final String getName() {
        return this.name.get();
    }

    /**
     * Changes the name of this element.
     *
     * @param name the new name.
     */
    public void setName( String name ) {
        this.name.set( name );
    }

    /** The name of this element. */
    private final V<String> name;

}
