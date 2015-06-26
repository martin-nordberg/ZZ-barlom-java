//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;

/**
 * An enumeration.
 */
public interface IJavaEnumeration
    extends IJavaConcreteComponent {

    /** Creates an new enum constant within this enumeration. */
    IJavaEnumConstant addEnumConstant(
        String name, String description, Integer uniqueId, String parametersCode, String referencePrefix
    );

    /** @return the enum constants within this enumeration. */
    List<IJavaEnumConstant> getEnumConstants();

}
