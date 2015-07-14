//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaClass;
import org.grestler.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;
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
        Optional<String> description,
        boolean isExternal,
        boolean isAbstract,
        boolean isFinal,
        Optional<IJavaClass> baseClass,
        boolean isTestCode
    ) {
        super( parent, name, description, isExternal );

        this.isAbstract = isAbstract;
        this.isFinal = isFinal;
        this.baseClass = baseClass;
        this.isTestCode = isTestCode;

        parent.onAddChild( this );
    }

    @Override
    public Optional<IJavaClass> getBaseClass() {
        return this.baseClass;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        if ( this.baseClass.isPresent() ) {
            result.add( this.baseClass.get().getType() );
        }

        return result;
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public boolean isTestCode() {
        return this.isTestCode;
    }

    @Override
    public void setBaseClass( IJavaClass baseClass ) {
        assert !this.baseClass.isPresent() : "Cannot change base class once set.";
        this.baseClass = Optional.of( baseClass );
    }

    private Optional<IJavaClass> baseClass;

    private final boolean isAbstract;

    private final boolean isFinal;

    private final boolean isTestCode;
}
