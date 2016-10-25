//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

import org.barlom.domain.javamodel.api.statements.IJavaCodeBlock;
import org.barlom.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * A Java function (constructor or member).
 */
public interface IJavaFunction
    extends IJavaMember, IJavaCodeBlock {

    /**
     * Creates a parameter for this method.
     *
     * @param name        the name of the parameter.
     * @param description a description of the parameter.
     * @param type        the type of the parameter.
     *
     * @return the newly created parameter.
     */
    default IJavaParameter addParameter( String name, String description, IJavaType type ) {
        return this.addParameter( name, Optional.of( description ), type );
    }

    /**
     * Creates a parameter for this method.
     *
     * @param name        the name of the parameter.
     * @param description an optional description of the parameter.
     * @param type        the type of the parameter.
     *
     * @return the newly created parameter.
     */
    IJavaParameter addParameter( String name, Optional<String> description, IJavaType type );

    /** @return the parameters within this method. */
    IIndexable<IJavaParameter> getParameters();

    /** @return the return type of this method. */
    IJavaType getReturnType();

}
