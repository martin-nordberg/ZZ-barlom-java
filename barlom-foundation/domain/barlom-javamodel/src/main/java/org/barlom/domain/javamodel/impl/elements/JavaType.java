//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * A Java type.
 */
@SuppressWarnings( { "NullableProblems", "ComparableImplementedButEqualsNotOverridden" } )
public abstract class JavaType
    extends JavaModelElement
    implements IJavaType {

    /**
     * Constructs a new type.
     */
    protected JavaType() {
        super( null, Optional.empty() );
    }

    @Override
    public int compareTo( IJavaType that ) {
        return this.getFullyQualifiedJavaName().compareTo( that.getFullyQualifiedJavaName() );
    }

}
