//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaComponent;
import org.grestler.domain.javamodel.api.elements.IJavaFunction;
import org.grestler.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.grestler.domain.javamodel.api.elements.IJavaMember;
import org.grestler.domain.javamodel.api.elements.IJavaMethod;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaPackage;
import org.grestler.domain.javamodel.api.elements.IJavaReferenceType;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * An interface, enum, or class.
 */
public abstract class JavaComponent
    extends JavaAnnotatableModelElement
    implements IJavaComponent {

    /**
     * Constructs a new component.
     */
    protected JavaComponent(
        IJavaNamedModelElement parent, String name, String description, boolean isExternal
    ) {
        super( parent, name, description );

        this.isExternal = isExternal;

        this.implementedInterfaces = new ArrayList<>();
        this.members = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    /** Creates a method within this component. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaMethod addMethod(
        String name,
        String description,
        EJavaAccessibility accessibility,
        boolean isAbstract,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType,
        String code
    ) {
        return new JavaMethod(
            this, name, description, accessibility, isAbstract, isStatic, isFinal, returnType, code
        );
    }

    /** @return the fully qualified name of this component. */
    @Override
    public String getFullyQualifiedJavaName() {
        return this.getParent().getFullyQualifiedJavaName() + "." + this.getJavaName();
    }

    /** @return the interfaces implemented by this component. */
    @Override
    public IIndexable<IJavaImplementedInterface> getImplementedInterfaces() {
        return new ReadOnlyListAdapter<>( this.implementedInterfaces );
    }

    /** @return the types needed to be imported by this component. */
    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaMethod method : this.getMethods() ) {
            result.addAll( method.getImports() );
        }

        for ( IJavaImplementedInterface implInterface : this.implementedInterfaces ) {
            result.add( implInterface.getImplementedInterface().makeJavaType() );
        }

        return result;
    }

    /** @return the methods within this component. */
    @Override
    public IIndexable<IJavaMethod> getMethods() {
        List<IJavaMethod> result = new ArrayList<>( this.methods );
        Collections.sort(
            result, ( IJavaFunction m1, IJavaFunction m2 ) -> {
                int cresult = Boolean.compare( m2.isStatic(), m1.isStatic() );
                if ( cresult == 0 ) {
                    cresult = m1.getAccessibility().compareTo( m2.getAccessibility() );
                }
                if ( cresult == 0 ) {
                    cresult = m1.compareTo( m2 );
                }
                if ( cresult == 0 ) {
                    cresult = m1.getParameters().size() - m2.getParameters().size();
                }
                return cresult;
            }
        );
        return new ReadOnlyListAdapter<>( result );
    }

    /** @return the parent of this package. */
    @Override
    public IJavaPackage getParent() {
        return (IJavaPackage) super.getParent();
    }

    /** Returns the isExternal. */
    @Override
    public boolean isExternal() {
        return this.isExternal;
    }

    /** Constructs a generic Java type with one type argument. */
    @Override
    public IJavaType makeGenericType( IJavaComponent typeArg1 ) {
        IJavaReferenceType result = new JavaReferenceType( this );
        result.addTypeArgument( typeArg1 );
        return result;
    }

    /** @return a type referencing this component. */
    @Override
    public IJavaType makeJavaType() {
        return new JavaReferenceType( this );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaImplementedInterface child ) {
        super.onAddChild( child );
        this.implementedInterfaces.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaMember child ) {
        super.onAddChild( child );
        this.members.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaMethod child ) {
        this.onAddChild( (IJavaMember) child );
        this.methods.add( child );
    }

    private final List<IJavaImplementedInterface> implementedInterfaces;

    private final boolean isExternal;

    private final List<IJavaMember> members;

    private final List<IJavaMethod> methods;

}
