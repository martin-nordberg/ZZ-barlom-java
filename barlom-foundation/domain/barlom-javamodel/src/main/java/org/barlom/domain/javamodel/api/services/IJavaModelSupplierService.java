package org.barlom.domain.javamodel.api.services;

import org.barlom.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to an output supplier extension service.
 */
@FunctionalInterface
public interface IJavaModelSupplierService<T> {

    T supply( IJavaModelElement element );

}
