//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.statements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * Mixin interface defining the behavior of a block of code (function, static initialization, loop, etc.).
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaCodeBlock
    extends IJavaModelElement {

    /**
     * Adds an assignment statement to this code block.
     *
     * @param leftHandSide  the code for the value being assigned to.
     * @param rightHandSide the code for the assigned value.
     */
    default IJavaAssignmentStatement addAssignmentStatement(
        String leftHandSide,
        String rightHandSide
    ) {
        return this.addAssignmentStatement( Optional.empty(), leftHandSide, rightHandSide, Optional.empty() );
    }

    /**
     * Adds an assignment statement to this code block.
     *
     * @param description   description for the statement
     * @param leftHandSide  the code for the value being assigned to.
     * @param rightHandSide the code for the assigned value.
     * @param extraOperator an extra operator to include in the assignment, e.g. "+" for += assignment.
     */
    IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description,
        String leftHandSide,
        String rightHandSide,
        Optional<String> extraOperator
    );

    /**
     * Adds a return statement to this code block.
     *
     * @param description description for the statement
     * @param returnValue the code for the expression of the value to return.
     */
    IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue );

    /**
     * Adds a variable declaration statement to the code block.
     *
     * @param name         the name of the variable.
     * @param description  a description of the variable.
     * @param type         the type of the variable.
     * @param initialValue an expression for the variables initial value.
     *
     * @return the new statement
     */
    IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    );

    /**
     * Adds a while loop statement to this code block.
     *
     * @param description   description for the statement
     * @param loopCondition the code for the expression controlling the loop.
     */
    IJavaWhileLoop addWhileLoop( Optional<String> description, String loopCondition );

    /**
     * Returns the statements within this code block.
     */
    IIndexable<IJavaStatement> getStatements();

}
