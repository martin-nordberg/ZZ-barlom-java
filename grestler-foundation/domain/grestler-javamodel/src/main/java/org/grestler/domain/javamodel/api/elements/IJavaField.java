//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.Optional;

/**
 * A Java field.
 */
public interface IJavaField
    extends IJavaMember {

    /** Returns the initialValueCode. */
    Optional<String> getInitialValueCode();

    @Override
    IJavaConcreteComponent getParent();

}
