package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator;
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

        // TODO: annotations

        writer.append( field.getAccessibility().getKeyWord() )
              .appendIf( field.isStatic(), " static" )
              .appendIf( field.isFinal(), " final" )
              .spaceOrWrap();

        field.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        writer.spaceOrWrap()
              .append( field.getJavaName() );

        if ( field.getInitialValueCode().isPresent() ) {
            writer.append( " =" )
                  .spaceOrWrap()
                  .append( field.getInitialValueCode().get() );
        }

        writer.append( ";" )
              .newLine()
              .newLine();

    }

    public static final JavaFieldCodeGenerator INSTANCE = new JavaFieldCodeGenerator();

}
