//
// (C) Copyright 2014 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A versioned item that is a list of items.
 */
@SuppressWarnings( { "unchecked", "SuspiciousArrayCast" } )
public final class VList<T> {

    /**
     * Constructs a new empty versioned list with given starting value for the current transaction's revision.
     */
    public VList() {

        this.size = new V<>( 0 );
        this.data = new V<>( (T[]) VList.EMPTY );

    }

    /**
     * Adds an item to the list.
     *
     * @param value The new raw value to become the next revision of this item.
     */
    public void add( T value ) {

        // Reference the old data.
        int oldSize = this.size.get();

        // Expand the capacity of the underlying array if needed.
        if ( oldSize == 0 ) {
            this.data.set( Arrays.copyOf( this.data.get(), VList.INITIAL_CAPACITY ) );
        }
        else if ( oldSize == this.data.get().length ) {
            this.data.set( Arrays.copyOf( this.data.get(), oldSize * 2 ) );
        }

        // Append the value and increment the size.
        this.data.get()[oldSize] = value;
        this.size.set( oldSize + 1 );

    }

    /**
     * Reads the version of the item list relevant for the transaction active in the currently running thread.
     *
     * @return a copy of the list of items as of the start of the transaction or else as written by the transaction.
     */
    @SuppressWarnings( "ManualArrayToCollectionCopy" )
    public List<T> get() {

        // Reference the old data.
        int oldSize = this.size.get();
        T[] oldData = this.data.get();

        // Copy the array into the list.
        List<T> result = new ArrayList<>( oldSize );
        for ( int i = 0; i < oldSize; i += 1 ) {
            result.add( oldData[i] );
        }

        return result;

    }

    /**
     * Removes an item from the list.
     *
     * @param value The item to be removed from this revision of the list.
     */
    public boolean remove( T value ) {

        // Reference the old data.
        int oldSize = this.size.get();
        T[] oldData = this.data.get();

        // Find the value.
        int index;
        for ( index = 0; index < oldSize; index += 1 ) {
            if ( oldData[index].equals( value ) ) {
                break;
            }
        }

        // If not found, done.
        if ( index == oldSize ) {
            return false;
        }

        // If half the size, shrink the new capacity.
        int newSize = oldSize - 1;
        int newCapacity = oldData.length;
        if ( newSize == 0 ) {
            newCapacity = 0;
        }
        else if ( newSize <= VList.INITIAL_CAPACITY ) {
            newCapacity = VList.INITIAL_CAPACITY;
        }
        else if ( newSize == oldData.length / 2 ) {
            newCapacity = oldData.length;
        }

        // TODO: avoid needless copy when already written by the current transaction

        // Allocate a new array.
        T[] newData;
        if ( newCapacity == 0 ) {
            newData = (T[]) VList.EMPTY;
        }
        else {
            newData = (T[]) new Object[newCapacity];
        }

        // Copy the remaining values into the new array.
        System.arraycopy( oldData, 0, newData, 0, index );
        System.arraycopy( oldData, index + 1, newData, index, newSize - index );

        // Update the versioned fields for the new array.
        this.size.set( newSize );
        this.data.set( newData );

        return true;

    }

    /** Empty starting point for the underlying data. */
    private static final Object[] EMPTY = { };

    /** The minimum size for a non-empty array. */
    private static final int INITIAL_CAPACITY = 8;

    /** The versioned data of the list. */
    private final V<T[]> data;

    /** The versioned size of the list. */
    private final V<Integer> size;

}
