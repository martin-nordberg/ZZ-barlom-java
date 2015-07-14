//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaFunction;
import org.grestler.domain.javamodel.api.elements.IJavaParameter;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.grestler.domain.javamodel.api.statements.IJavaReturnStatement;
import org.grestler.domain.javamodel.api.statements.IJavaStatement;
import org.grestler.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.grestler.domain.javamodel.api.statements.IJavaWhileLoop;
import org.grestler.domain.javamodel.impl.statements.JavaCodeBlockImpl;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A Java function (constructor or member).
 */
public abstract class JavaFunction
    extends JavaMember
    implements IJavaFunction {

    /**
     * Constructs a new method.
     */
    protected JavaFunction(
        JavaComponent parent,
        String name,
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType );

        this.parameters = new ArrayList<>();
        this.codeBlock = new JavaCodeBlockImpl( this );

        parent.onAddChild( this );
    }

    @Override
    public IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description, String leftHandSide, String rightHandSide, Optional<String> extraOperator
    ) {
        return this.codeBlock.addAssignmentStatement( description, leftHandSide, rightHandSide, extraOperator );
    }

    @Override
    public IJavaParameter addParameter( String name, Optional<String> description, IJavaType type ) {
        return new JavaParameter( this, name, description, type );
    }

    @Override
    public IJavaReturnStatement addReturnStatement(
        Optional<String> description, Optional<String> returnValue
    ) {
        return this.codeBlock.addReturnStatement( description, returnValue );
    }

    @Override
    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return this.codeBlock.addVariableDeclaration( name, description, type, initialValue );
    }

    @Override
    public IJavaWhileLoop addWhileLoop( Optional<String> description, String loopCondition ) {
        return this.codeBlock.addWhileLoop( description, loopCondition );
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaParameter parameter : this.parameters ) {
            result.add( parameter.getType() );
        }

        return result;
    }

    @Override
    public IIndexable<IJavaParameter> getParameters() {
        return new ReadOnlyListAdapter<>( this.parameters );
    }

    @Override
    public IJavaType getReturnType() {
        return this.getType();
    }

    @Override
    public IIndexable<IJavaStatement> getStatements() {
        return this.codeBlock.getStatements();
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaParameter child ) {
        super.onAddChild( child );
        this.parameters.add( child );
    }

    private final JavaCodeBlockImpl codeBlock;

    private final List<IJavaParameter> parameters;

}
