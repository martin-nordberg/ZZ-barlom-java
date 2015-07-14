//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.statements;

import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.grestler.domain.javamodel.impl.elements.JavaNamedModelElement;

import java.util.Optional;

/**
 * Concrete implementation of a variable declaration statement.
 */
public class JavaVariableDeclaration
    extends JavaStatement
    implements IJavaVariableDeclaration {

    /**
     * Constructs a new Java model element
     *
     * @param codeBlock    the parent code block of this statement.
     * @param description  a description of this model element.
     * @param initialValue code for the initial value expression.
     * @param name         the name of the variable
     * @param type         the type of the variable
     */
    protected JavaVariableDeclaration(
        JavaCodeBlockImpl codeBlock,
        String name,
        Optional<String> description,
        IJavaType type,
        Optional<String> initialValue
    ) {
        super( codeBlock, description );

        this.name = name;
        this.type = type;
        this.initialValue = initialValue;
    }

    @Override
    public Optional<String> getInitialValue() {
        return this.initialValue;
    }

    @Override
    public String getJavaName() {
        return JavaNamedModelElement.makeJavaName( this.name );
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IJavaType getType() {
        return this.type;
    }

    private final Optional<String> initialValue;

    private final String name;

    private final IJavaType type;

}
