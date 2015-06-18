//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;

/**
 * A Java model element with a name.
 */
public interface IJavaNamedModelElement
    extends IJavaModelElement, Comparable<IJavaNamedModelElement> {

    /** @return the children of this model element. */
    List<IJavaModelElement> getChildren();

    /** @return the name of this element for Java code purposes. */
    String getJavaName();

    /** @return the name. */
    String getName();

}
