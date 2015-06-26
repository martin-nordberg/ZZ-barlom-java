//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * An Enum constant.
 */
public interface IJavaEnumConstant
    extends IJavaMember {

    String getParametersCode();

    @Override
    IJavaEnumeration getParent();

    String getRawName();

    String getReferencePrefix();

    Integer getUniqueId();

}
