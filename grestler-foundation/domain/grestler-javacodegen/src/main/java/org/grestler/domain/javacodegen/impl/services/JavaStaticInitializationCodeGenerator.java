package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.domain.javamodel.api.statements.IJavaStatement;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

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
