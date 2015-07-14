//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.grestler.domain.javamodel.api.elements.IJavaInterface;

import java.util.Optional;

/**
 * An implements clause.
 */
public final class JavaImplementedInterface
    extends JavaModelElement
    implements IJavaImplementedInterface {

    /**
     * Constructs a new base interface (extends/implements)
     */
    JavaImplementedInterface( JavaComponent parent, IJavaInterface implementedInterface ) {
        super( parent, Optional.empty() );

        this.implementedInterface = implementedInterface;

        parent.onAddChild( this );
    }

    @Override
    public IJavaInterface getImplementedInterface() {
        return this.implementedInterface;
    }

    @Override
    public IJavaComponent getParent() {
        return (IJavaComponent) super.getParent();
    }

    private final IJavaInterface implementedInterface;

}
