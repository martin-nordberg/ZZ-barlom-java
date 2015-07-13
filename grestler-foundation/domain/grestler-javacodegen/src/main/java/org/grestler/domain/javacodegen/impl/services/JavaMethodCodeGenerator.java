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

        // Qualifiers
        writer.append( method.getAccessibility().getKeyWord() )
              .appendIf( method.isStatic(), " static" )
              .appendIf( method.isFinal(), " final" )
              .spaceOrWrap();

        // Return type
        method.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( method.getJavaName() );

        // Parameters
        writer.append( "(" )
              .spaceOrWrapIndent();

        for ( int i=0 ; i<method.getParameters().size(); i+=1 ) {
            IJavaParameter parameter = method.getParameters().getAt( i );

            parameter.consume( JavaCodeGenerator.INSTANCE, writer );

            if ( i<method.getParameters().size()-1 ) {
                writer.append( "," )
                      .spaceOrWrap();
            }
        }

        writer.spaceOrWrapUnindent()
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
