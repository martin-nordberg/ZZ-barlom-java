//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaBuiltinType;

/**
 * A built-in type.
 */
@SuppressWarnings( "javadoc" )
public final class JavaBuiltinType
    extends JavaType
    implements IJavaBuiltinType {

    /**
     * Constructs a new built in type.
     */
    JavaBuiltinType( String javaName ) {
        super();

        this.javaName = javaName;
    }

    @Override
    public String getFullyQualifiedJavaName() {
        return this.javaName;
    }

    @Override
    public String getJavaName() {
        return this.javaName;
    }

    @Override
    public boolean isImplicitlyImported() {
        return true;
    }

    private final String javaName;

}
