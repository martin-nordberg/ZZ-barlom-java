//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

/**
 * The root package for Java code.
 */
public interface IJavaRootPackage
    extends IJavaAbstractPackage {

    IJavaBuiltinType getBuiltinBoolean();

    IJavaBuiltinType getBuiltinDouble();

    IJavaBuiltinType getBuiltinFloat();

    IJavaBuiltinType getBuiltinInt();

    IJavaBuiltinType getBuiltinLong();

    IJavaBuiltinType getBuiltinVoid();

}
