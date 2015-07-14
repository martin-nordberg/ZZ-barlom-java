//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaFunction;
import org.grestler.domain.javamodel.api.elements.IJavaParameter;
import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * A Java parameter.
 */
public class JavaParameter
    extends JavaTypedModelElement
    implements IJavaParameter {

    /**
     * Constructs a new parameter
     */
    JavaParameter( JavaFunction parent, String name, Optional<String> description, IJavaType type ) {
        super( parent, name, description, type );

        parent.onAddChild( this );
    }

    @Override
    public IJavaFunction getParent() {
        return (IJavaFunction) super.getParent();
    }

}
