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
     * Constructs a new Java statement.
     *
     * @param codeBlock   the helper object containing the statements of the parent code block.
     * @param description a description of this model element.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaStatement(
        JavaCodeBlockImpl codeBlock,
        Optional<String> description
    ) {
        super( codeBlock.getParent(), description );

        codeBlock.onAddChild( this );
    }

    @Override
    public IJavaCodeBlock getParent() {
        return (IJavaCodeBlock) super.getParent();
    }

}
