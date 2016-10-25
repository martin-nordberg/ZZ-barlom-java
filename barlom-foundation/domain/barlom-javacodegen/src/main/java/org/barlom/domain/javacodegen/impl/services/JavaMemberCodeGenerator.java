package org.barlom.domain.javacodegen.impl.services;

import org.barlom.domain.javamodel.api.elements.IJavaMember;
import org.barlom.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
abstract class JavaMemberCodeGenerator
    extends JavaAnnotatableModelElementCodeGenerator {

    protected JavaMemberCodeGenerator() {
    }

    /**
     * Writes out the accessibility and static/final qualifiers for a member.
     *
     * @param member the member to be written.
     * @param writer the output writer.
     */
    protected void writeQualifiers(
        IJavaMember member, CodeWriter writer
    ) {

        writer.append( member.getAccessibility().getKeyWord() )
              .appendIf( member.isStatic(), " static" )
              .appendIf( member.isFinal(), " final" )
              .spaceOrWrap();

    }

}
