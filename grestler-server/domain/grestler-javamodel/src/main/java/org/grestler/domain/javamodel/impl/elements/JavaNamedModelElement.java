package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;

import java.util.ArrayList;
import java.util.List;

/**
 * A Java model element with a name.
 */
public abstract class JavaNamedModelElement
    extends JavaModelElement
    implements IJavaNamedModelElement {

    /**
     * Constructs a new named model element.
     *
     * @param parent      The parent of the element.
     * @param name        The name of the element.
     * @param description A description of the element
     */
    protected JavaNamedModelElement( IJavaNamedModelElement parent, String name, String description ) {
        super( parent, description );

        assert name != null && !name.isEmpty();

        this.name = name;

        this.children = new ArrayList<>();
    }

    @SuppressWarnings( "NullableProblems" )
    @Override
    public int compareTo( IJavaNamedModelElement that ) {
        return this.getJavaName().compareTo( that.getJavaName() );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || this.getClass() != o.getClass() ) {
            return false;
        }

        JavaNamedModelElement that = (JavaNamedModelElement) o;

        return this.name.equals( that.name ) && this.getParent().equals( that.getParent() );
    }

    /** @return the children of this model element. */
    @Override
    public List<IJavaModelElement> getChildren() {
        return this.children;
    }

    /** @return the name of this element for Java code purposes. */
    @Override
    public String getJavaName() {
        return JavaNamedModelElement.makeJavaName( this.name );
    }

    /** @return the name. */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int result = this.getParent().hashCode();
        result = 31 * result + this.name.hashCode();
        return result;
    }

    /** @return A Java name from the given model element name. */
    @SuppressWarnings( "DynamicRegexReplaceableByCompiledPattern" )
    static String makeJavaName( String nonJavaName ) {
        return nonJavaName.replaceAll( " ", "" );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaModelElement child ) {
        this.children.add( child );
    }

    private final List<IJavaModelElement> children;

    private final String name;

}
