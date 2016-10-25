//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.domain.javamodel.api.statements;

import java.util.Optional;

/**
 * Interface to a return statement.
 */
public interface IJavaReturnStatement
    extends IJavaStatement {

    /**
     * @return the code for the value returned by the statement.
     */
    Optional<String> getReturnValue();

}
