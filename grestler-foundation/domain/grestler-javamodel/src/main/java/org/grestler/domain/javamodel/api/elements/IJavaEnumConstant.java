//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.Optional;

/**
 * An Enum constant.
 */
public interface IJavaEnumConstant
    extends IJavaMember {

    /**
     * @return the raw code for the parameters of the constant's construction.
     */
    Optional<String> getParametersCode();

    @Override
    IJavaEnumeration getParent();

}
