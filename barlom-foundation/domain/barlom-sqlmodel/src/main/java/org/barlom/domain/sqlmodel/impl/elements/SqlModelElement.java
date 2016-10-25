//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.sqlmodel.impl.elements;

import org.barlom.domain.sqlmodel.api.elements.ISqlModelElement;
import org.barlom.domain.sqlmodel.api.elements.ISqlNamedModelElement;
import org.barlom.domain.sqlmodel.api.elements.ISqlSchema;

/**
 * An abstract SQL model element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class SqlModelElement
    implements ISqlModelElement {

    /**
     * Constructs a new database model element
     *
     * @param parent The parent of this model element.
     */
    protected SqlModelElement( ISqlNamedModelElement parent, String description ) {
        super();
        this.parent = parent;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public ISqlNamedModelElement getParent() {
        return this.parent;
    }

    @Override
    public ISqlSchema getSchema() {
        return this.parent.getSchema();
    }

    /** The description of this model element. */
    private final String description;

    /** The parent of this model element. */
    private final ISqlNamedModelElement parent;

}
