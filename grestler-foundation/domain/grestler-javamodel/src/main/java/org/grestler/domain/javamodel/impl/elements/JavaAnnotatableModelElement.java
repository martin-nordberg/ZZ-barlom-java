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
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        IJavaNamedModelElement parent, String name, Optional<String> description
    ) {
        super( parent, name, description );

        this.annotations = new ArrayList<>();
        this.importedElements = new HashSet<>();
    }

    @Override
    public IJavaAnnotation addAnnotation(
        Optional<String> description, IJavaAnnotationInterface annotationInterface, Optional<String> parametersCode
    ) {
        return new JavaAnnotation( this, description, annotationInterface, parametersCode );
    }

    @Override
    public IIndexable<IJavaAnnotation> getAnnotations() {
        return new ReadOnlyListAdapter<>( this.annotations );
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = new ImportedJavaTypesSet();

        for ( IJavaTyped importedElement : this.importedElements ) {
            result.add( importedElement.getType() );
        }

        for ( IJavaAnnotation method : this.annotations ) {
            result.add( method.getAnnotationInterface().getType() );
        }

        return result;
    }

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
