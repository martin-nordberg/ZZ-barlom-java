//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaRootPackage;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelSupplierFactory;

/**
 * Top level Java element.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class JavaModelElement
    implements IJavaModelElement {

    /**
     * Constructs a new Java model element
     *
     * @param parent the parent of this model element.
     * @param description a description of this model element.
     */
    protected JavaModelElement( IJavaNamedModelElement parent, String description ) {
        super();
        this.parent = parent;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public IJavaNamedModelElement getParent() {
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

    @Override
    public <T> void consume( final IJavaModelConsumerFactory<T> factory, T value ) {
        factory.build( this.getClass() ).consume( this, value );
    }

    private final String description;

    private final IJavaNamedModelElement parent;

}
