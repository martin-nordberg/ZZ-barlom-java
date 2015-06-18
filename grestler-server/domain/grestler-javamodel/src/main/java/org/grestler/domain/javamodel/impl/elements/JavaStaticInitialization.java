//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.grestler.domain.javamodel.api.elements.IJavaStaticInitialization;

/**
 * A static initialization block.
 */
public final class JavaStaticInitialization
    extends JavaModelElement
    implements IJavaStaticInitialization {

    /**
     * Constructs a new static initialization block.
     */
    JavaStaticInitialization( JavaConcreteComponent parent, String description, String code ) {
        super( parent, description );

        this.code = code;

        parent.onAddChild( this );
    }

    /** Returns the code. */
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public IJavaConcreteComponent getParent() {
        return (IJavaConcreteComponent) super.getParent();
    }

    private final String code;

}
