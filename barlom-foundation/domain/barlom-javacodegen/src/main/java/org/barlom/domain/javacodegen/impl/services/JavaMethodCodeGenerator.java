package org.barlom.domain.javacodegen.impl.services;

import org.barlom.domain.javacodegen.api.services.JavaCodeGenerator;
import org.barlom.domain.javamodel.api.elements.IJavaMethod;
import org.barlom.domain.javamodel.api.elements.IJavaParameter;
import org.barlom.domain.javamodel.api.services.IJavaModelConsumerService;
import org.barlom.domain.javamodel.api.statements.IJavaStatement;
import org.barlom.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaMethodCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaMethod, CodeWriter> {

    private JavaMethodCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaMethod method, CodeWriter writer
    ) {

        // JavaDoc
        if ( method.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( method.getDescription().get(), " * " );

            if ( !method.getParameters().isEmpty() ) {

                for ( IJavaParameter parameter : method.getParameters() ) {

                    writer.alwaysWrap( " * @param " )
                          .append( parameter.getJavaName() );

                    if ( parameter.getDescription().isPresent() ) {
                        writer.append( " " )
                              .appendProse( parameter.getDescription().get(), " *     " );
                    }

                }

            }

            // TODO: need a return value description

            // TODO: throws declaration(s)

            writer.spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Annotations
        this.writeAnnotations( method, writer, true );

        // Qualifiers
        this.writeQualifiers( method, writer );

        // Return type
        method.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( method.getJavaName() );

        // Parameters
        writer.append( "(" )
              .spaceOrWrapIndentIf( !method.getParameters().isEmpty() )
              .mark();

        for ( IJavaParameter parameter : method.getParameters() ) {
            parameter.consume( JavaCodeGenerator.INSTANCE, writer );
            writer.mark()
                  .append( "," )
                  .spaceOrWrap();
        }

        writer.revertToMark()
              .spaceOrWrapUnindentIf( !method.getParameters().isEmpty() )
              .append( ")" );

        // Throws
        // TODO

        if ( method.isAbstract() ) {
            // Abstract empty body
            writer.append( ";" );
        }
        else {
            // Concrete body
            writer.append( " {" )
                  .newLine()
                  .indent();

            for ( IJavaStatement statement : method.getStatements() ) {
                statement.consume( JavaCodeGenerator.INSTANCE, writer );
            }

            writer.unindent()
                  .append( "}" );
        }

        writer.newLine()
              .newLine();

    }

    public static final JavaMethodCodeGenerator INSTANCE = new JavaMethodCodeGenerator();

}
