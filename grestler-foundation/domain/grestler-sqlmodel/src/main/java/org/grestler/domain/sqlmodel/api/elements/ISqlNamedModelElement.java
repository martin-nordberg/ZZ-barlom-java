//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.sqlmodel.api.elements;

import java.util.List;

/**
 * An abstract model element with a name.
 */
public interface ISqlNamedModelElement
    extends ISqlModelElement {

    /** @return the children. */
    List<ISqlModelElement> getChildren();

    /** @return the name. */
    String getName();

    /** @return the name of this element for SQL purposes. */
    String getSqlName();

}
