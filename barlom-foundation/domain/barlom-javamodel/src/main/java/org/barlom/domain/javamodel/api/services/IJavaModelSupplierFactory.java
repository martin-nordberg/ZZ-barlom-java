package org.barlom.domain.javamodel.api.services;

import org.barlom.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to a factory of Java model extensions.
 */
@FunctionalInterface
public interface IJavaModelSupplierFactory<T> {

    /**
     * Builds a Java model external supplier service for the given concrete type of Java model element.
     *
     * @param elementType the type of Java model element.
     *
     * @return the supplier service.
     */
    IJavaModelSupplierService<T> build( Class<? extends IJavaModelElement> elementType );

}
