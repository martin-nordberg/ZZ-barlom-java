//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * A Java function (constructor or member).
 */
public interface IJavaFunction
    extends IJavaMember {

    /** Creates a parameter for this method. */
    IJavaParameter addParameter( String name, Optional<String> description, IJavaType type );

    /** @return the code of this method. */
    String getCode();

    /** @return the parameters within this method. */
    IIndexable<IJavaParameter> getParameters();

    /** @return the return type of this method. */
    IJavaType getReturnType();

}
