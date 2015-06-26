//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaType;

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
        super( null, "" );
    }

    @Override
    public int compareTo( IJavaType that ) {
        return this.getFullyQualifiedJavaName().compareTo( that.getFullyQualifiedJavaName() );
    }

}
