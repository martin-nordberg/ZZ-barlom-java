//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaConcreteComponent;
import org.grestler.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.grestler.domain.javamodel.impl.statements.JavaCodeBlock;

import java.util.Optional;

/**
 * A static initialization block.
 */
public final class JavaStaticInitialization
    extends JavaCodeBlock
    implements IJavaStaticInitialization {

    /**
     * Constructs a new static initialization block.
     */
    JavaStaticInitialization( JavaConcreteComponent parent, Optional<String> description ) {
        super( parent, description );

        parent.onAddChild( this );
    }

    @Override
    public IJavaConcreteComponent getParent() {
        return (IJavaConcreteComponent) super.getParent();
    }

}
