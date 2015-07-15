package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javacodegen.api.services.JavaCodeGenerator;
import org.grestler.domain.javamodel.api.elements.IJavaMethod;
import org.grestler.domain.javamodel.api.elements.IJavaParameter;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

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

        // Javadoc
        writer.append( "// TODO: javadoc ... " )
              .newLine();

        // Annotations
        this.writeAnnotations( method, writer );

        // Qualifiers
        this.writeQualifiers( method, writer );

        // Return type
        method.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( method.getJavaName() );

        // Parameters
        writer.append( "(" )
              .spaceOrWrapIndent();

        for ( IJavaParameter parameter : method.getParameters() ) {
            parameter.consume( JavaCodeGenerator.INSTANCE, writer );
            writer.mark()
                  .append( "," )
                  .spaceOrWrap();
        }

        writer.revertToMark()
              .spaceOrWrapUnindent()
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

            // TODO: code

            writer.unindent()
                  .append( "}" );
        }

        writer.newLine()
              .newLine();

    }

    public static final JavaMethodCodeGenerator INSTANCE = new JavaMethodCodeGenerator();

}
