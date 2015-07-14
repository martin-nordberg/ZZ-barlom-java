//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.statements;

import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.grestler.domain.javamodel.api.statements.IJavaCodeBlock;
import org.grestler.domain.javamodel.api.statements.IJavaReturnStatement;
import org.grestler.domain.javamodel.api.statements.IJavaStatement;
import org.grestler.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.grestler.domain.javamodel.api.statements.IJavaWhileLoop;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Helper class for implementing IJavaCodeBlock.
 */
public class JavaCodeBlockImpl {

    /**
     * Constructs a new Java code block helper.
     */
    public JavaCodeBlockImpl( IJavaCodeBlock parent ) {
        this.parent = parent;
        this.statements = new ArrayList<>();
    }

    public IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description,
        String leftHandSide,
        String rightHandSide,
        Optional<String> extraOperator
    ) {
        return new JavaAssignmentStatement( this, description, leftHandSide, rightHandSide, extraOperator );
    }

    public IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue ) {
        return new JavaReturnStatement( this, description, returnValue );
    }

    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return new JavaVariableDeclaration( this, name, description, type, initialValue );
    }

    public IJavaWhileLoop addWhileLoop( Optional<String> description, String loopCondition ) {
        return new JavaWhileLoop( this, description, loopCondition );
    }

    public IJavaCodeBlock getParent() {
        return this.parent;
    }

    public IIndexable<IJavaStatement> getStatements() {
        return new ReadOnlyListAdapter<>( this.statements );
    }

    void onAddChild( IJavaStatement statement ) {
        this.statements.add( statement );
    }

    private final IJavaCodeBlock parent;

    private final List<IJavaStatement> statements;
}
