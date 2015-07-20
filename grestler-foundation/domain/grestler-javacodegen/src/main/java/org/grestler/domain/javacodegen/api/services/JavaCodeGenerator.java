package org.grestler.domain.javacodegen.api.services;

import org.grestler.domain.javacodegen.impl.services.JavaBuiltinTypeCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaClassCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaConstructorCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaFieldCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaMethodCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaParameterCodeGenerator;
import org.grestler.domain.javacodegen.impl.services.JavaStaticInitializationCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaModelElement;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerFactory;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Java model extension service for generating Java source code.
 */
public final class JavaCodeGenerator
    implements IJavaModelConsumerFactory<CodeWriter> {

    /**
     * Constructs a new code generator.
     */
    private JavaCodeGenerator() {

        this.consumers = new HashMap<>();

        // Map all the concrete classes to individual code generators.
        this.consumers.put( "JavaBuiltinType", JavaBuiltinTypeCodeGenerator.INSTANCE );
        this.consumers.put( "JavaClass", JavaClassCodeGenerator.INSTANCE );
        this.consumers.put( "JavaConstructor", JavaConstructorCodeGenerator.INSTANCE );
        this.consumers.put( "JavaField", JavaFieldCodeGenerator.INSTANCE );
        this.consumers.put( "JavaMethod", JavaMethodCodeGenerator.INSTANCE );
        this.consumers.put( "JavaParameter", JavaParameterCodeGenerator.INSTANCE );
        this.consumers.put( "JavaStaticInitialization", JavaStaticInitializationCodeGenerator.INSTANCE );

    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <E extends IJavaModelElement> IJavaModelConsumerService<E, CodeWriter> build( Class<? extends IJavaModelElement> elementType ) {

        String elementTypeName = elementType.getSimpleName();

        if ( !this.consumers.containsKey( elementTypeName ) ) {
            throw new IllegalArgumentException( "Unhandled Java model element type: " + elementTypeName );
        }

        return (IJavaModelConsumerService<E, CodeWriter>) this.consumers.get( elementTypeName );

    }

    /**
     * The one and only Java code generator (factory).
     */
    public static final JavaCodeGenerator INSTANCE = new JavaCodeGenerator();

    /**
     * Map of code generators by concrete class name.
     */
    private final Map<String, IJavaModelConsumerService<? extends IJavaModelElement, CodeWriter>> consumers;

}
