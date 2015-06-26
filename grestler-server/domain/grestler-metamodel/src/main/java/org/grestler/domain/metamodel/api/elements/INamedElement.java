//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.domain.metamodel.api.elements;

import java.util.UUID;

/**
 * Shared base interface for metadata elements with names.
 */
public interface INamedElement
    extends IDocumentedElement {

    /**
     * Data structure for named element records.
     */
    class Record
        extends IDocumentedElement.Record {

        protected Record( UUID id, String name ) {
            super( id );
            this.name = name;
        }

        public final String name;

    }

    /**
     * @return the name of this element.
     */
    String getName();

    /**
     * @return the fully qualified path to this element.
     */
    default String getPath() {

        String result = this.getParent().getPath();

        if ( !result.isEmpty() ) {
            return result + "." + this.getName();
        }

        return this.getName();

    }

}
