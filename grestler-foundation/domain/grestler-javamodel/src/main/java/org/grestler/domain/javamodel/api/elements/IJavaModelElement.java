//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import org.grestler.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelSupplierFactory;

/**
 * Top level Java element.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaModelElement {

    /**
     * Consumes a value for this model element using the external service built by the given factory.
     *
     * @param factory a factory that builds (or just provides) a consumer extension to Java model elements.
     * @param value   the value to consume.
     * @param <T>     the type of the consumed value.
     */
    <T> void consume( IJavaModelConsumerFactory<T> factory, T value );

    /**
     * @return The description of this model element.
     */
    String getDescription();

    /**
     * @return The parent of this model element.
     */
    IJavaNamedModelElement getParent();

    /**
     * @return The highest root package containing this model element.
     */
    IJavaRootPackage getRootPackage();

    /**
     * Supplies a value from this model element using the external service built by the given factory.
     *
     * @param factory a factory that builds (or just provides) a supplier extension to Java model elements.
     * @param <T>     the type of value supplied.
     *
     * @return the supplied value.
     */
    <T> T supply( IJavaModelSupplierFactory<T> factory );

}
