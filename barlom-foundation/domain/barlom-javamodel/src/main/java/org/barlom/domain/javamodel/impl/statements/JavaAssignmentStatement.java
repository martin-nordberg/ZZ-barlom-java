//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.statements;

import org.barlom.domain.javamodel.api.statements.IJavaAssignmentStatement;

import java.util.Optional;

/**
 * A Java assignment statement.
 */
public class JavaAssignmentStatement
    extends JavaStatement
    implements IJavaAssignmentStatement {

    /**
     * Constructs a new Java model element
     *
     * @param codeBlock     the container of statements.
     * @param description   a description of this model element.
     * @param leftHandSide  the code for the value being assigned to.
     * @param rightHandSide the code for the assigned value.
     * @param extraOperator an extra operator to include in the assignment, e.g. "+" for += assignment.
     */
    protected JavaAssignmentStatement(
        JavaCodeBlockImpl codeBlock,
        Optional<String> description,
        String leftHandSide,
        String rightHandSide,
        Optional<String> extraOperator
    ) {
        super( codeBlock, description );
        this.extraOperator = extraOperator;
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    @Override
    public Optional<String> getExtraOperator() {
        return this.extraOperator;
    }

    @Override
    public String getLeftHandSide() {
        return this.leftHandSide;
    }

    @Override
    public String getRightHandSide() {
        return this.rightHandSide;
    }

    private final Optional<String> extraOperator;

    private final String leftHandSide;

    private final String rightHandSide;
}
