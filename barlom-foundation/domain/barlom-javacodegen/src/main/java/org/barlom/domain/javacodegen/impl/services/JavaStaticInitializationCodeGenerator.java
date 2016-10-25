package org.barlom.domain.javacodegen.impl.services;

import org.barlom.domain.javacodegen.api.services.JavaCodeGenerator;
import org.barlom.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.barlom.domain.javamodel.api.services.IJavaModelConsumerService;
import org.barlom.domain.javamodel.api.statements.IJavaStatement;
import org.barlom.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaStaticInitializationCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaStaticInitialization, CodeWriter> {

    private JavaStaticInitializationCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaStaticInitialization staticInitialization, CodeWriter writer
    ) {

        // JavaDoc
        if ( staticInitialization.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( staticInitialization.getDescription().get(), " * " )
                  .spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Concrete body
        writer.append( " {" )
              .newLine()
              .indent();

        for ( IJavaStatement statement : staticInitialization.getStatements() ) {
            statement.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        writer.unindent()
              .append( "}" )
              .newLine()
              .newLine();

    }

    public static final JavaStaticInitializationCodeGenerator INSTANCE = new JavaStaticInitializationCodeGenerator();

}
