//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.statements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.domain.javamodel.api.statements.IJavaCodeBlock;
import org.grestler.domain.javamodel.api.statements.IJavaReturnStatement;
import org.grestler.domain.javamodel.api.statements.IJavaStatement;
import org.grestler.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.grestler.domain.javamodel.impl.elements.JavaModelElement;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
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

        this.statements = new ArrayList<>();
    }

    @Override
    public IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue ) {
        return new JavaReturnStatement( this, description, returnValue );
    }

    @Override
    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return new JavaVariableDeclaration( this, name, description, type, initialValue );
    }

    @Override
    public IIndexable<IJavaStatement> getStatements() {
        return new ReadOnlyListAdapter<>( this.statements );
    }

    void onAddChild( IJavaStatement statement ) {
        this.statements.add( statement );
    }

    private final List<IJavaStatement> statements;

}
