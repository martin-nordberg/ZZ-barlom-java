package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javamodel.api.elements.IJavaField;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaFieldCodeGenerator implements IJavaModelConsumerService<IJavaField,CodeWriter> {

    private JavaFieldCodeGenerator() {

    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaField field, CodeWriter writer
    ) {
        writer.append( "// TODO: javadoc ... " )
              .newLine();

        writer.append( field.getAccessibility().getKeyWord() )
              .append( " " )
              .appendIf( field.isStatic(), "static " )
              .appendIf( field.isFinal(), "final " )
              .append( "/*TODO: Type*/ " )
              .append( field.getJavaName() )
              // TODO: initializer
              .append( ";" )
              .newLine()
              .newLine();
    }

    public static final JavaFieldCodeGenerator INSTANCE = new JavaFieldCodeGenerator();

}
