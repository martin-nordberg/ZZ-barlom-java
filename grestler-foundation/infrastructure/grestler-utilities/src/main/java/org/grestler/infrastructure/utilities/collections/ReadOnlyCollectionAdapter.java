package org.grestler.infrastructure.utilities.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * Adapter for a collection that provides a read-only interface.
 */
public class ReadOnlyCollectionAdapter<T>
    implements ISizedIterable<T> {

    /**
     * Class providing a read-only iterator that delegates to an arbitrary iterator.
     */
    public static class ReadOnlyIterator<T>
        implements Iterator<T> {

        /**
         * Constructs a new iterator wrapping the given one.
         *
         * @param iterator the iterator to make read-only.
         */
        ReadOnlyIterator( Iterator<T> iterator ) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public T next() {
            return this.iterator.next();
        }

        /** The array version to iterate over. */
        private final Iterator<T> iterator;

    }

    @SuppressWarnings( "AssignmentToCollectionOrArrayFieldFromParameter" )
    public ReadOnlyCollectionAdapter( Collection<T> collection ) {
        this.collection = collection;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<>( this.collection.iterator() );
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    private final Collection<T> collection;
}
