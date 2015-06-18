//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaRootPackage;

/**
 * Top level Java element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class JavaModelElement
    implements IJavaModelElement {

    /**
     * Constructs a new Java model element
     *
     * @param parent The parent of this model element.
     */
    protected JavaModelElement( IJavaNamedModelElement parent, String description ) {
        super();
        this.parent = parent;
        this.description = description;
    }

    /** Returns the description. */
    @Override
    public String getDescription() {
        return this.description;
    }

    /** Returns the parent. */
    @Override
    public IJavaNamedModelElement getParent() {
        return this.parent;
    }

    /**
     * @return The highest root package containing this model element.
     */
    @Override
    public IJavaRootPackage getRootPackage() {
        return this.getParent().getRootPackage();
    }

    private final String description;

    private final IJavaNamedModelElement parent;

}
