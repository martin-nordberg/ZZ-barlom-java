//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.statements;

import java.util.Optional;

/**
 * Interface to an assignment statement.
 */
public interface IJavaAssignmentStatement
    extends IJavaStatement {

    /**
     * @return an extra operator to include in the assignment, e.g. "+" for a += assignment.
     */
    Optional<String> getExtraOperator();

    /**
     * @return the code for the value being assigned to.
     */
    String getLeftHandSide();

    /**
     * @return the code for the expression of the assigned value.
     */
    String getRightHandSide();

}
