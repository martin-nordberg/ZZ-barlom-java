//
// (C) Copyright 2014-2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import org.grestler.utilities.collections.IIndexable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A versioned item that is an array of items.
 * <p>
 * This class works something like ArrayList. Newly added values always go on the end of the list and increment a size
 * field that is itself versioned, so prior revisions are not affected by such additions so long as they honor their own
 * size value. When the array is filled, the new revision allocates for itself a new array double the size of the prior
 * revision's (like ArrayList except that prior revisions will hold on to the old data as long as they are live.
 */
public final class VArray<T>
    implements IIndexable<T> {

    /**
     * Constructs a new empty versioned array with given starting value for the current transaction's revision.
     */
    public VArray() {

        this.size = new VInt( 0 );
        this.data = new V<>( VArray.EMPTY );

    }

    /**
     * Adds an item to the array.
     *
     * @param value The new value to add at the end of the array in the next revision of this item.
     */
    public void add( T value ) {

        // Reference the old data.
        int oldSize = this.size.get();

        // Expand the capacity of the underlying array if needed.
        if ( oldSize == 0 ) {
            this.data.set( Arrays.copyOf( this.data.get(), VArray.INITIAL_CAPACITY ) );
        }
        else if ( oldSize == this.data.get().length ) {
            this.data.set( Arrays.copyOf( this.data.get(), oldSize * 2 ) );
        }

        // Append the value and increment the size.
        this.data.get()[oldSize] = value;
        this.size.set( oldSize + 1 );

    }

    @SuppressWarnings( "unchecked" )
    @Override
    public T getAt( int index ) {

        // Reference the data.
        int arraySize = this.size.get();
        Object[] arrayData = this.data.get();

        if ( index >= arraySize || index < 0 ) {
            throw new IndexOutOfBoundsException( "VList indexed out of bounds. Size = " + arraySize + " ; index = " + index + "." );
        }

        return (T) arrayData[index];

    }

    @Override
    public Iterator<T> iterator() {
        return new VArrayIterator<>( this.size.get(), this.data.get() );
    }

    /**
     * Removes an item from the array.
     *
     * @param value The item to be removed from this revision of the array.
     */
    public boolean remove( T value ) {

        // Reference the old data.
        int arraySize = this.size.get();
        Object[] arrayData = this.data.get();

        // Find the value.
        int index;
        for ( index = 0; index < arraySize; index += 1 ) {
            if ( arrayData[index].equals( value ) ) {
                break;
            }
        }

        // If not found, done.
        if ( index == arraySize ) {
            return false;
        }

        // do the removal
        this.removeAt( index );

        return true;

    }

    /**
     * Removes an item from the array.
     *
     * @param index The index of the item to be removed from this revision of the array.
     */
    public void removeAt( int index ) {

        // Reference the old data.
        int oldSize = this.size.get();
        Object[] oldData = this.data.get();

        // Ensure in-bounds
        if ( index >= oldSize || index < 0 ) {
            throw new IndexOutOfBoundsException( "VList indexed out of bounds. Size = " + oldSize + " ; index = " + index + "." );
        }

        // Shrink the size by one.
        int newSize = oldSize - 1;

        // Shrink the capacity for the smaller size if appropriate.
        int newCapacity = oldData.length;
        if ( newSize == 0 ) {
            newCapacity = 0;
        }
        else if ( newSize == oldData.length / 4 ) {
            newCapacity = oldData.length / 2;

            if ( newCapacity <= VArray.INITIAL_CAPACITY ) {
                newCapacity = VArray.INITIAL_CAPACITY;
            }
        }


        // TODO: avoid needless copy when already written by the current transaction

        // Allocate a new array.
        Object[] newData;
        if ( newCapacity == 0 ) {
            newData = VArray.EMPTY;
        }
        else {
            newData = Arrays.copyOf( VArray.EMPTY, newCapacity );

            // Copy the remaining values into the new array.
            System.arraycopy( oldData, 0, newData, 0, index );
            System.arraycopy( oldData, index + 1, newData, index, newSize - index );
        }

        // Update the versioned fields for the new array.
        this.size.set( newSize );
        this.data.set( newData );

    }

    @Override
    public int size() {
        return this.size.get();
    }

    /** Empty starting point for all arrays. */
    private static final Object[] EMPTY = { };

    /** The minimum size for a non-empty array. */
    private static final int INITIAL_CAPACITY = 8;

    /** The versioned data of the array. */
    private final V<Object[]> data;

    /** The versioned size of the array. */
    private final VInt size;

    /**
     * Class providing a read-only iterator over a version of the array.
     */
    public static class VArrayIterator<T>
        implements Iterator<T> {

        /**
         * Constructs a new iterator given the array data from the parent VArray.
         *
         * @param size the size of the versioned array.
         * @param data the data to iterate over.
         */
        @SuppressWarnings( "AssignmentToCollectionOrArrayFieldFromParameter" )
        VArrayIterator( int size, Object[] data ) {
            this.iterSize = size;
            this.iterData = data;
        }

        @Override
        public boolean hasNext() {
            return this.position < this.iterSize;
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public T next() {
            if ( this.hasNext() ) {
                T result = (T) this.iterData[this.position];
                this.position += 1;
                return result;
            }
            else {
                throw new NoSuchElementException();
            }
        }

        /** The array version to iterate over. */
        private final Object[] iterData;

        /** The size of the data as of the iterated version. */
        private final int iterSize;

        /** The position of the iterator in the array. */
        private int position = 0;

    }
}
