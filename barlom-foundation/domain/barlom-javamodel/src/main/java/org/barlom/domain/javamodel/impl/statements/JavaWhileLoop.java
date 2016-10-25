//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.statements;

import org.barlom.domain.javamodel.api.elements.IJavaType;
import org.barlom.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.barlom.domain.javamodel.api.statements.IJavaReturnStatement;
import org.barlom.domain.javamodel.api.statements.IJavaStatement;
import org.barlom.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.barlom.domain.javamodel.api.statements.IJavaWhileLoop;
import org.barlom.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * A Java return statement.
 */
public class JavaWhileLoop
    extends JavaStatement
    implements IJavaWhileLoop {

    /**
     * Constructs a new Java model element
     *
     * @param codeBlock     the container of statements.
     * @param description   a description of this model element.
     * @param loopCondition expression for the value returned.
     */
    protected JavaWhileLoop(
        JavaCodeBlockImpl codeBlock,
        Optional<String> description, String loopCondition
    ) {
        super( codeBlock, description );
        this.loopCondition = loopCondition;
        this.codeBlock = new JavaCodeBlockImpl( this );
    }

    @Override
    public IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description, String leftHandSide, String rightHandSide, Optional<String> extraOperator
    ) {
        return this.codeBlock.addAssignmentStatement( description, leftHandSide, rightHandSide, extraOperator );
    }

    @Override
    public IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue ) {
        return this.codeBlock.addReturnStatement( description, returnValue );
    }

    @Override
    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return this.codeBlock.addVariableDeclaration( name, description, type, initialValue );
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public IJavaWhileLoop addWhileLoop( Optional<String> description, String nestedLoopCondition ) {
        return this.codeBlock.addWhileLoop( description, nestedLoopCondition );
    }

    @Override
    public String getLoopCondition() {
        return this.loopCondition;
    }

    @Override
    public IIndexable<IJavaStatement> getStatements() {
        return this.codeBlock.getStatements();
    }

    private final JavaCodeBlockImpl codeBlock;

    private final String loopCondition;
}
