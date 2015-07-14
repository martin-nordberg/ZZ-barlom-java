package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.elements.IJavaNamedModelElement;
import org.grestler.infrastructure.utilities.collections.IIndexable;
import org.grestler.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaNamedModelElement( IJavaNamedModelElement parent, String name, Optional<String> description ) {
        super( parent, description );

        assert name != null && !name.isEmpty();

        this.name = name;

        this.children = new ArrayList<>();
    }

    /** @return A Java name from the given model element name. */
    public static String makeJavaName( CharSequence nonJavaName ) {
        return JavaNamedModelElement.SPACES_PATTERN.matcher( nonJavaName ).replaceAll( "" );
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

    @Override
    public IIndexable<IJavaModelElement> getChildren() {
        return new ReadOnlyListAdapter<>( this.children );
    }

    @Override
    public String getJavaName() {
        return JavaNamedModelElement.makeJavaName( this.name );
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IJavaNamedModelElement getParent() {
        return (IJavaNamedModelElement) super.getParent();
    }

    @Override
    public int hashCode() {
        int result = this.getParent().hashCode();
        result = 31 * result + this.name.hashCode();
        return result;
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaModelElement child ) {
        this.children.add( child );
    }

    private static final Pattern SPACES_PATTERN = Pattern.compile( " " );

    private final List<IJavaModelElement> children;

    private final String name;

}
