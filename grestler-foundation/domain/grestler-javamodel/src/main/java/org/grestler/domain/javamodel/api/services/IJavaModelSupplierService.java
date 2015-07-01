package org.grestler.domain.javamodel.api.services;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to an output supplier extension service.
 */
@FunctionalInterface
public interface IJavaModelSupplierService<T> {

    T supply( IJavaModelElement element );

}
