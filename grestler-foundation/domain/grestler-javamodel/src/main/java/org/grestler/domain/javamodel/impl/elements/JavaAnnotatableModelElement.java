//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaAnnotatableModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotation;
import org.grestler.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaType;
import org.grestler.domain.javamodel.api.elements.IJavaTyped;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An anotatable model element.
 */
public abstract class JavaAnnotatableModelElement
    extends JavaNamedModelElement
    implements IJavaAnnotatableModelElement {

    /**
     * Constructs a new named model element.
     *
     * @param parent      The parent of the element.
     * @param name        The name of the element.
     * @param description A description of the element
     */
    protected JavaAnnotatableModelElement(
        IJavaNamedModelElement parent, String name, String description
    ) {
        super( parent, name, description );

        this.annotations = new ArrayList<>();
        this.importedElements = new HashSet<>();
    }

    /** Creates an annotation for this named model element. */
    @Override
    public IJavaAnnotation addAnnotation(
        String description, IJavaAnnotationInterface annotationInterface, String parametersCode
    ) {
        return new JavaAnnotation( this, description, annotationInterface, parametersCode );
    }

    /** @return the annotations of this named model element. */
    @Override
    public List<IJavaAnnotation> getAnnotations() {
        return this.annotations;
    }

    /** @return the types needed to be imported by this component. */
    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = new ImportedJavaTypesSet();

        for ( IJavaTyped importedElement : this.importedElements ) {
            result.add( importedElement.makeJavaType() );
        }

        for ( IJavaAnnotation method : this.annotations ) {
            result.add( method.getAnnotationInterface().makeJavaType() );
        }

        return result;
    }

    /** Adds a type that must be imported for textual code to work. */
    @Override
    public void importForCode( IJavaTyped importedElement ) {
        assert importedElement != null;
        this.importedElements.add( importedElement );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaAnnotation child ) {
        super.onAddChild( child );
        this.annotations.add( child );
    }

    private final List<IJavaAnnotation> annotations;

    private final Set<IJavaTyped> importedElements;

}
