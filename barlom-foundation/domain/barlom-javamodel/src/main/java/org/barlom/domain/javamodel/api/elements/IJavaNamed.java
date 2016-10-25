//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * Mixin interface for a Java model element with a name.
 */
public interface IJavaNamed {

    /**
     * @return The name of this element for Java code purposes.
     */
    String getJavaName();

    /**
     * @return The name of this element.
     */
    String getName();

}
