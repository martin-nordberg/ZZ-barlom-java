//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.elements;

import org.barlom.domain.javamodel.api.statements.IJavaCodeBlock;

/**
 * A static initialization block.
 */
public interface IJavaStaticInitialization
    extends IJavaCodeBlock {

    @Override
    IJavaConcreteComponent getParent();

}
