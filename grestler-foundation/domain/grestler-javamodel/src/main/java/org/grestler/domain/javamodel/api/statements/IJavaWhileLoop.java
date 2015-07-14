//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.statements;

/**
 * Interface to a while loop.
 */
public interface IJavaWhileLoop
    extends IJavaStatement, IJavaCodeBlock {

    /**
     * @return the code for the expression controlling the loop.
     */
    String getLoopCondition();

}
