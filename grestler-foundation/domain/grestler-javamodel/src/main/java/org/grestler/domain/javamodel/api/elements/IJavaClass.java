//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

/**
 * A Java class.
 */
public interface IJavaClass
    extends IJavaConcreteComponent {

    /** @return the baseClass. */
    IJavaClass getBaseClass();

    /** @return whether this is an abstract class. */
    boolean isAbstract();

    /** @return whether this is a final class. */
    boolean isFinal();

    /** Returns the isTestCode. */
    boolean isTestCode();

    /** Sets the base class. */
    void setBaseClass( IJavaClass baseClass );

}
