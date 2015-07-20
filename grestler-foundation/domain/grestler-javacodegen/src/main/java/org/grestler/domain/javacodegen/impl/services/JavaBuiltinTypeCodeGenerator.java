package org.grestler.domain.javacodegen.impl.services;

import org.grestler.domain.javamodel.api.elements.IJavaBuiltinType;
import org.grestler.domain.javamodel.api.services.IJavaModelConsumerService;
import org.grestler.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaBuiltinTypeCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaBuiltinType, CodeWriter> {

    private JavaBuiltinTypeCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaBuiltinType type, CodeWriter writer
    ) {
        writer.append( type.getJavaName() );
    }

    public static final JavaBuiltinTypeCodeGenerator INSTANCE = new JavaBuiltinTypeCodeGenerator();

}
