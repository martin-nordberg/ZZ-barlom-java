package org.barlom.domain.javacodegen.impl.services;

import org.barlom.domain.javacodegen.api.services.JavaCodeGenerator;
import org.barlom.domain.javamodel.api.elements.IJavaField;
import org.barlom.domain.javamodel.api.services.IJavaModelConsumerService;
import org.barlom.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaFieldCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaField, CodeWriter> {

    private JavaFieldCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaField field, CodeWriter writer
    ) {

        // JavaDoc
        if ( field.getDescription().isPresent() ) {
            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( field.getDescription().get(), " * " )
                  .spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();
        }

        // Annotations
        this.writeAnnotations( field, writer, true );

        // Qualifiers
        this.writeQualifiers( field, writer );

        // Type
        field.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( field.getJavaName() );

        // Initial value
        if ( field.getInitialValueCode().isPresent() ) {
            writer.append( " =" )
                  .spaceOrWrap()
                  .append( field.getInitialValueCode().get() );
        }

        // Ending punctuation
        writer.append( ";" )
              .newLine()
              .newLine();

    }

    public static final JavaFieldCodeGenerator INSTANCE = new JavaFieldCodeGenerator();

}
