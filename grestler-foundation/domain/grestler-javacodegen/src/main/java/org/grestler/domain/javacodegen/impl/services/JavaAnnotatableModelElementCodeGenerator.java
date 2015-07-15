package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotatableModelElement;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
public abstract class JavaAnnotatableModelElementCodeGenerator {

    protected JavaAnnotatableModelElementCodeGenerator() {
    }

    /**
     * Writes out the accessibility and static/final qualifiers for a member.
     *
     * @param annotatableModelElement the model element to be written.
     * @param writer                  the output writer.
     */
    protected void writeAnnotations(
        IJavaAnnotatableModelElement annotatableModelElement, CodeWriter writer
    ) {

        writer.append( "/*TODO: annotations*/" )
              .spaceOrWrap();

    }

}
