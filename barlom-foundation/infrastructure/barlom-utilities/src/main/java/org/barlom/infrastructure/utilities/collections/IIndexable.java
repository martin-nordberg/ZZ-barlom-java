package org.barlom.infrastructure.utilities.collections;

/**
 * Interface defining an iterable with direct indexing of values.
 */
public interface IIndexable<T>
    extends ISizedIterable<T> {

    /**
     * Returns the value at the given index in the collection.
     *
     * @param index the zero-based position of the value needed.
     *
     * @return the value.
     *
     * @throws IndexOutOfBoundsException if the index is out-of-bounds.
     */
    T getAt( int index );

}
