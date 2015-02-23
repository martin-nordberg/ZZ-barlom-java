//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.metamodel.api.elements;

/**
 * Base interface for an abstract attribute type.
 */
public interface IAttributeType
    extends IPackagedElement {

    /**
     * @return the fundamental data type for attributes of this type.
     */
    EDataType getDataType();

}
