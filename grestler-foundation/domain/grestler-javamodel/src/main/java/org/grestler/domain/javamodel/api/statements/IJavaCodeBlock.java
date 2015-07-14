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
public interface IJavaCodeBlock
    extends IJavaModelElement {

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
     * Returns the statements within this code block.
     */
    IIndexable<IJavaStatement> getStatements();

}
