//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.Set;

/**
 * A Java class.
 */
public final class JavaClass
    extends JavaConcreteComponent
    implements IJavaClass {

    /**
     * Constructs a new class.
     */
    JavaClass(
        JavaPackage parent,
        String name,
        String description,
        boolean isExternal,
        boolean isAbstract,
        boolean isFinal,
        IJavaClass baseClass,
        boolean isTestCode
    ) {
        super( parent, name, description, isExternal );

        this.isAbstract = isAbstract;
        this.isFinal = isFinal;
        this.baseClass = baseClass;
        this.isTestCode = isTestCode;

        parent.onAddChild( this );
    }

    /** @return the baseClass. */
    @Override
    public IJavaClass getBaseClass() {
        return this.baseClass;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        if ( this.baseClass != null ) {
            result.add( this.baseClass.makeJavaType() );
        }

        return result;
    }

    /** @return whether this is an abstract class. */
    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    /** @return whether this is a final class. */
    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    /** Returns the isTestCode. */
    @Override
    public boolean isTestCode() {
        return this.isTestCode;
    }

    /** Sets the base class. */
    @Override
    public void setBaseClass( IJavaClass baseClass ) {
        assert this.baseClass == null;
        this.baseClass = baseClass;
    }

    private IJavaClass baseClass;

    private final boolean isAbstract;

    private final boolean isFinal;

    private final boolean isTestCode;
}
