package org.grestler.domain.javamodel.api.services;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to a factory of Java model extensions.
 */
@FunctionalInterface
public interface IJavaModelConsumerFactory<T> {

    /**
     * Builds a Java model external consumer service for the given concrete type of Java model element.
     * @param elementType the type of Java model element.
     * @return the consumer service.
     */
    IJavaModelConsumerService<T> build( Class<? extends IJavaModelElement> elementType );

}
