//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaFunction;
import org.grestler.domain.javamodel.api.elements.IJavaParameter;
import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A Java function (constructor or member).
 */
public abstract class JavaFunction
    extends JavaMember
    implements IJavaFunction {

    /**
     * Constructs a new method.
     */
    protected JavaFunction(
        JavaComponent parent,
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType,
        String code
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType );

        this.code = code;

        this.parameters = new ArrayList<>();

        parent.onAddChild( this );
    }

    /** Creates a parameter for this method. */
    @Override
    public IJavaParameter addParameter( String name, String description, IJavaType type ) {
        return new JavaParameter( this, name, description, type );
    }

    /** @return the code of this method. */
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaParameter parameter : this.parameters ) {
            result.add( parameter.getType() );
        }

        return result;
    }

    /** @return the parameters within this method. */
    @Override
    public List<IJavaParameter> getParameters() {
        return this.parameters;
    }

    /** @return the return type of this method. */
    @Override
    public IJavaType getReturnType() {
        return this.getType();
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaParameter child ) {
        super.onAddChild( child );
        this.parameters.add( child );
    }

    private final String code;

    private final List<IJavaParameter> parameters;

}
