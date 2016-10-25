//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.utilities.collections;

/**
 * An iterable where the size of the collection is known without iterating through it.
 */
public interface ISizedIterable<T>
    extends Iterable<T> {

    /**
     * @return whether the iterated collection is empty.
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @return whether the iterated collection is non-empty.
     */
    default boolean isNotEmpty() {
        return this.size() > 0;
    }

    /**
     * @return the number of items in the iteration.
     */
    int size();

}
