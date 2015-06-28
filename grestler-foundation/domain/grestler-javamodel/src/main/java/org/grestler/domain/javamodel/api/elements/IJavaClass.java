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

    /** @return whether this is a test class. */
    boolean isTestCode();

    /**
     * Sets the base class.
     * @param baseClass the base class for this class.
     */
    void setBaseClass( IJavaClass baseClass );

}
