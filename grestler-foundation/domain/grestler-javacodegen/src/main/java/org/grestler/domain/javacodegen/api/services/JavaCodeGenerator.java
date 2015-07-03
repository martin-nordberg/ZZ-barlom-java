package org.grestler.domain.javacodegen.api.services;

import org.grestler.domain.javacodegen.impl.services.JavaClassCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaFieldCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Java model extension service for generating Java source code.
 */
public final class JavaCodeGenerator implements IJavaModelConsumerFactory<CodeWriter> {

    private JavaCodeGenerator() {
        this.consumers = new HashMap<>();

        this.consumers.put( "JavaClass", JavaClassCodeGenerator.INSTANCE );
        this.consumers.put( "JavaField", JavaFieldCodeGenerator.INSTANCE );
    }

    @Override
    public <E extends IJavaModelElement> IJavaModelConsumerService<E,CodeWriter> build( Class<? extends IJavaModelElement> elementType ) {

        String elementTypeName = elementType.getSimpleName();

        if ( !this.consumers.containsKey( elementTypeName ) ) {
            throw new IllegalArgumentException( "Unhandled Java model element type: " + elementTypeName );
        }

        return (IJavaModelConsumerService<E, CodeWriter>) this.consumers.get( elementTypeName );

    }

    public static final JavaCodeGenerator INSTANCE = new JavaCodeGenerator();

    private final Map<String,IJavaModelConsumerService<? extends IJavaModelElement,CodeWriter>> consumers;

}
