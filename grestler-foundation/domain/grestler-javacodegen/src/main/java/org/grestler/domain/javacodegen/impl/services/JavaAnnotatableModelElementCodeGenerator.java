package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotatableModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotation;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
abstract class JavaAnnotatableModelElementCodeGenerator {

    protected JavaAnnotatableModelElementCodeGenerator() {
    }

    /**
     * Writes out the accessibility and static/final qualifiers for a member.
     *
     * @param annotatableModelElement the model element to be written.
     * @param writer                  the output writer.
     * @param forcedWrap              whether to always wrap after each annotation
     */
    protected void writeAnnotations(
        IJavaAnnotatableModelElement annotatableModelElement, CodeWriter writer, boolean forcedWrap
    ) {

        IIndexable<IJavaAnnotation> annotations = annotatableModelElement.getAnnotations();

        for ( IJavaAnnotation annotation : annotations ) {
            writer.append( "@" )
                  .append( annotation.getAnnotationInterface().getJavaName() );

            if ( annotation.getParametersCode().isPresent() ) {
                writer.append( "( " )
                      .append( annotation.getParametersCode().get() )
                      .append( " )" );
            }

            if ( forcedWrap ) {
                writer.newLine();
            }
            else {
                writer.spaceOrWrap();
            }
        }

    }

}
