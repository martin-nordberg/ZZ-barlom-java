//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.statements;

import org.grestler.domain.javamodel.api.statements.IJavaCodeBlock;
import org.grestler.domain.javamodel.api.statements.IJavaStatement;
import org.grestler.domain.javamodel.impl.elements.JavaModelElement;

import java.util.Optional;

/**
 * Implementation for a Java statement.
 */
public class JavaStatement
    extends JavaModelElement
    implements IJavaStatement {

    /**
     * Constructs a new Java model element
     *
     * @param parent      the parent of this model element.
     * @param description a description of this model element.
     */
    protected JavaStatement(
        JavaCodeBlock parent,
        Optional<String> description
    ) {
        super( parent, description );

        parent.onAddChild( this );
    }

    @Override
    public IJavaCodeBlock getParent() {
        return (IJavaCodeBlock) super.getParent();
    }

}
