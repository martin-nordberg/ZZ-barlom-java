//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.javamodel.impl.elements;

import org.grestler.domain.javamodel.api.elements.EJavaAccessibility;
import org.grestler.domain.javamodel.api.elements.IJavaEnumConstant;
import org.grestler.domain.javamodel.api.elements.IJavaEnumeration;

import java.util.Optional;

/**
 * An Enum constant.
 */
public final class JavaEnumConstant
    extends JavaMember
    implements IJavaEnumConstant {

    /**
     * Constructs a new field.
     */
    JavaEnumConstant(
        JavaEnumeration parent,
        String name,
        Optional<String> description,
        Integer uniqueId,
        String parametersCode,
        String referencePrefix
    ) {
        super(
            parent, name, description, EJavaAccessibility.PUBLIC, true, true, new JavaReferenceType( parent )
        );

        this.parametersCode = parametersCode;
        this.uniqueId = uniqueId;
        this.referencePrefix = referencePrefix;

        parent.onAddChild( this );
    }

    @Override
    public String getParametersCode() {
        return this.parametersCode;
    }

    @Override
    public IJavaEnumeration getParent() {
        return (IJavaEnumeration) super.getParent();
    }

    @Override
    public String getRawName() {
        return super.getName();
    }

    @Override
    public String getReferencePrefix() {
        return this.referencePrefix;
    }

    @Override
    public Integer getUniqueId() {
        return this.uniqueId;
    }

    private final String parametersCode;

    private final String referencePrefix;

    private final Integer uniqueId;
}
