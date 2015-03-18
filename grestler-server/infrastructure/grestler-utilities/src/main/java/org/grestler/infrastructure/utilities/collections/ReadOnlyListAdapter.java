package org.grestler.infrastructure.utilities.collections;

import java.util.Iterator;
import java.util.List;

/**
 * Adapter for a list that provides a read-only interface.
 */
public class ReadOnlyListAdapter<T>
    implements IIndexable<T> {

    @SuppressWarnings( "AssignmentToCollectionOrArrayFieldFromParameter" )
    public ReadOnlyListAdapter( List<T> list ) {
        this.list = list;
    }

    @Override
    public T getAt( int index ) {
        return this.list.get( index );
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<>( this.list.iterator() );
    }

    @Override
    public int size() {
        return this.list.size();
    }

    private final List<T> list;


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
}
