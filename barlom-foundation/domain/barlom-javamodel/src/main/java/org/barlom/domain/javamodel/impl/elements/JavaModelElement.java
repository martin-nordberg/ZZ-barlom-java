//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.elements;

import org.barlom.domain.javamodel.api.elements.IJavaModelElement;
import org.barlom.domain.javamodel.api.elements.IJavaRootPackage;
import org.barlom.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.barlom.domain.javamodel.api.services.IJavaModelSupplierFactory;

import java.util.Optional;

/**
 * Top level Java element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class JavaModelElement
    implements IJavaModelElement {

    /**
     * Constructs a new Java model element
     *
     * @param parent      the parent of this model element.
     * @param description a description of this model element.
     */
    protected JavaModelElement( IJavaModelElement parent, Optional<String> description ) {
        super();
        this.parent = parent;
        this.description = description;
    }

    @Override
    public <T> void consume( final IJavaModelConsumerFactory<T> factory, T value ) {
        factory.build( this.getClass() ).consume( this, value );
    }

    @Override
    public Optional<String> getDescription() {
        return this.description;
    }

    @Override
    public IJavaModelElement getParent() {
        return this.parent;
    }

    @Override
    public IJavaRootPackage getRootPackage() {
        return this.getParent().getRootPackage();
    }

    @Override
    public <T> T supply( IJavaModelSupplierFactory<T> factory ) {
        return factory.build( this.getClass() ).supply( this );
    }

    private final Optional<String> description;

    private final IJavaModelElement parent;

}
