//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.attributes;

import org.grestler.metamodel.api.elements.IElement;

/**
 * Base interface for an abstract attribute type.
 */
public interface IAttributeType
    extends IElement {

    /**
     * @return the fundamental data type for attributes of this type.
     */
    EDataType getDataType();

}
