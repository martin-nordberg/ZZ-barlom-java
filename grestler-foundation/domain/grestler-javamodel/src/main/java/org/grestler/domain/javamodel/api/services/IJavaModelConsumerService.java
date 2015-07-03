package org.grestler.domain.javamodel.api.services;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to an output supplier extension service.
 */
@FunctionalInterface
public interface IJavaModelConsumerService<E extends IJavaModelElement, T> {

    void consume( E element, T value );

}
