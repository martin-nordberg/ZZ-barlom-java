//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaReferenceType;

import java.util.ArrayList;
import java.util.List;

/**
 * A reference type.
 */
public final class JavaReferenceType
    extends JavaType
    implements IJavaReferenceType {

    /**
     * Constructs a new type reference.
     *
     * @param component The referenced component defining this type
     */
    JavaReferenceType( JavaComponent component ) {
        super();

        this.component = component;
        this.typeArgs = new ArrayList<>();
    }

    /** Adds a type argument to this type. */
    @Override
    public void addTypeArgument( IJavaComponent typeArg ) {
        this.typeArgs.add( typeArg );
    }

    @Override
    public String getFullyQualifiedJavaName() {
        return this.component.getFullyQualifiedJavaName();
    }

    @Override
    public String getJavaName() {
        return this.component.getJavaName() + this.getTypeArgsForName();
    }

    @Override
    public boolean isImplicitlyImported() {
        return this.component.getParent().isImplicitlyImported();
    }

    private String getTypeArgsForName() {

        if ( this.typeArgs.isEmpty() ) {
            return "";
        }

        StringBuilder result = new StringBuilder( "<" );
        String delimiter = "";
        for ( IJavaComponent typeArg : this.typeArgs ) {
            result.append( delimiter );
            result.append( typeArg.getJavaName() );
            delimiter = ",";
        }
        result.append( ">" );

        return result.toString();
    }

    private final JavaComponent component;

    private final List<IJavaComponent> typeArgs;

}
