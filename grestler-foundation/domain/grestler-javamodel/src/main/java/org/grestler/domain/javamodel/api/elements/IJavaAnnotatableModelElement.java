//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.api.elements;

import java.util.List;
import java.util.Set;

/**
 * An annotatable model element.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface IJavaAnnotatableModelElement
    extends IJavaNamedModelElement {

    /**
     * Creates an annotation for this named model element.
     * @param description a description of the annotation.
     * @param annotationInterface the annotation type.
     * @param parametersCode textual code for the parameters of the annotation.
     * @return the newly added annotation.
     */
    IJavaAnnotation addAnnotation(
        String description, IJavaAnnotationInterface annotationInterface, String parametersCode
    );

    /** @return the annotations of this named model element. */
    List<IJavaAnnotation> getAnnotations();

    /** @return the types needed to be imported by this component. */
    Set<IJavaType> getImports();

    /**
     * Adds a type that must be imported for textual code to work.
     * @param importedElement the element needing an import.
     */
    void importForCode( IJavaTyped importedElement );

}
