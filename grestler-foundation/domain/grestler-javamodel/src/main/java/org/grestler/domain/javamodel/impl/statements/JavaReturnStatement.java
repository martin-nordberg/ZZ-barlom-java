//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.statements;

import org.grestler.domain.javamodel.api.statements.IJavaReturnStatement;

import java.util.Optional;

/**
 * A Java return statement.
 */
public class JavaReturnStatement
    extends JavaStatement
    implements IJavaReturnStatement {

    /**
     * Constructs a new Java model element
     *
     * @param parent      the parent of this model element.
     * @param description a description of this model element.
     * @param returnValue expression for the value returned.
     */
    protected JavaReturnStatement( JavaCodeBlock parent, Optional<String> description, Optional<String> returnValue ) {
        super( parent, description );
        this.returnValue = returnValue;
    }

    @Override
    public Optional<String> getReturnValue() {
        return this.returnValue;
    }

    private final Optional<String> returnValue;
}
