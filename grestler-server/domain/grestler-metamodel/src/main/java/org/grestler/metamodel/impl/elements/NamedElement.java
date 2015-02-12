//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.impl.elements;

import org.grestler.metamodel.api.elements.INamedElement;

import javax.json.stream.JsonGenerator;
import java.util.UUID;

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
     * @param id   the unique ID for the element.
     * @param name the name of the element.
     */
    protected NamedElement( UUID id, String name ) {

        super( id );

        this.name = name;

    }

    @Override
    public void generateJsonAttributes( JsonGenerator json ) {

        super.generateJsonAttributes( json );

        json.write( "name", this.name ).write( "path", this.getPath() );

    }

    @Override
    public final String getName() {
        return this.name;
    }

    /** The name of this element. */
    private final String name;

}
