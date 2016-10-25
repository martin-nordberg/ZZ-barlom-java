//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.impl.statements;

import org.barlom.domain.javamodel.api.elements.IJavaModelElement;
import org.barlom.domain.javamodel.api.elements.IJavaType;
import org.barlom.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.barlom.domain.javamodel.api.statements.IJavaCodeBlock;
import org.barlom.domain.javamodel.api.statements.IJavaReturnStatement;
import org.barlom.domain.javamodel.api.statements.IJavaStatement;
import org.barlom.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.barlom.domain.javamodel.api.statements.IJavaWhileLoop;
import org.barlom.domain.javamodel.impl.elements.JavaModelElement;
import org.barlom.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * Implementation of a Java code block (non-mixin).
 */
public class JavaCodeBlock
    extends JavaModelElement
    implements IJavaCodeBlock {

    /**
     * Constructs a new Java model element
     *
     * @param parent      the parent of this model element.
     * @param description a description of this model element.
     */
    protected JavaCodeBlock(
        IJavaModelElement parent,
        Optional<String> description
    ) {
        super( parent, description );

        this.impl = new JavaCodeBlockImpl( this );
    }

    @Override
    public IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description, String leftHandSide, String rightHandSide, Optional<String> extraOperator
    ) {
        return this.impl.addAssignmentStatement( description, leftHandSide, rightHandSide, extraOperator );
    }

    @Override
    public IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue ) {
        return this.impl.addReturnStatement( description, returnValue );
    }

    @Override
    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return this.impl.addVariableDeclaration( name, description, type, initialValue );
    }

    @Override
    public IJavaWhileLoop addWhileLoop( Optional<String> description, String loopCondition ) {
        return this.impl.addWhileLoop( description, loopCondition );
    }

    @Override
    public IIndexable<IJavaStatement> getStatements() {
        return this.impl.getStatements();
    }

    private final JavaCodeBlockImpl impl;

}
