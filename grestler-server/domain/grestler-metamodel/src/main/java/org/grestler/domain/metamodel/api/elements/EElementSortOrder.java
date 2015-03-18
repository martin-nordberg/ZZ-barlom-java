//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

/**
 * Enumeration of possible sort orders for element queries.
 */
public enum EElementSortOrder {

    /** Sort by name. */
    NAME,

    /** Sort a parent before its children. */
    PARENT_BEFORE_CHILDREN,

    /** Sort by full path. */
    PATH,

    /** Sort a super type before its sub-types. */
    SUPER_TYPE_BEFORE_SUB_TYPES

}
