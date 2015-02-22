//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.grestler.utilities.revisions;

import java.util.Objects;
import java.util.Optional;

/**
 * Versioned hash map.
 * <p>
 * TODO: currently there is no resizing of the hash table as it fills up.
 */
public class VHashMap<K, V> {

    /**
     * Constructs a new versioned hash map.
     *
     * @param expectedSize the expected size of the map.
     */
    public VHashMap( int expectedSize ) {

        this.size = new VInt( 0 );

        // Determine the size of table to use.
        int hashSize = VHashMap.determineHashSize( expectedSize );

        // Fill the table with empty lists.
        this.entryLists = new Object[hashSize];
        for ( int i = 0; i < hashSize; i += 1 ) {
            this.entryLists[i] = new VArray<>();
        }

    }

    /**
     * Finds the value in the map for the given key.
     *
     * @param key the key to look up.
     *
     * @return the value found if present.
     */
    @SuppressWarnings( "unchecked" )
    public Optional<V> get( K key ) {

        // Compute the hash code.
        int hash = this.getHashIndex( key );

        // Index into the table for the list of entries with that hash.
        Iterable<KeyValue<K, V>> entryList = (Iterable<KeyValue<K, V>>) this.entryLists[hash];

        // Look for an entry with the right key.
        for ( KeyValue<K, V> entry : entryList ) {
            if ( entry.key.equals( key ) ) {
                return Optional.of( entry.value );
            }
        }

        // Otherwise an empty result.
        return Optional.empty();

    }

    /**
     * Sets a new value for the given key.
     *
     * @param key   the key.
     * @param value the new value for that key.
     *
     * @return the old value for the key if any.
     */
    @SuppressWarnings( "unchecked" )
    public Optional<V> put( K key, V value ) {

        Objects.requireNonNull( value );

        // Compute the hash code.
        int hash = this.getHashIndex( key );

        // Index into the table for the list of entries with that hash.
        VArray<KeyValue<K, V>> entryList = (VArray<KeyValue<K, V>>) this.entryLists[hash];

        // If not found, the old value is empty.
        Optional<V> result = Optional.empty();

        // Remove the old value for the key if present.
        for ( int i = 0; i < entryList.size(); i += 1 ) {
            KeyValue<K, V> entry = entryList.getAt( i );
            if ( entry.key.equals( key ) ) {
                result = Optional.of( entry.value );
                entryList.removeAt( i );
                break;
            }
        }

        // Add the new value to the list.
        entryList.add( new KeyValue<>( key, value ) );

        // Track the size of the map.
        if ( !result.isPresent() ) {
            this.size.increment();
        }

        return result;

    }

    /**
     * Removes the value for the given key.
     *
     * @param key the key.
     *
     * @return the old value for the key if any.
     */
    @SuppressWarnings( "unchecked" )
    public Optional<V> remove( K key ) {

        // Compute the hash code.
        int hash = this.getHashIndex( key );

        // Index into the table for the list of entries with that hash.
        VArray<KeyValue<K, V>> entryList = (VArray<KeyValue<K, V>>) this.entryLists[hash];

        // If not found, the old value is empty.
        Optional<V> result = Optional.empty();

        // Remove the old value for the key if present.
        for ( int i = 0; i < entryList.size(); i += 1 ) {
            KeyValue<K, V> entry = entryList.getAt( i );
            if ( entry.key.equals( key ) ) {
                result = Optional.of( entry.value );
                entryList.removeAt( i );
                this.size.decrement();
                break;
            }
        }

        return result;

    }

    /**
     * @return the number of entries in this map.
     */
    public int size() {
        return this.size.get();
    }

    /**
     * Determines the size of the hash table from an estimate of the maximum size.
     *
     * @param expectedSize the expected maximum size of the map.
     *
     * @return the size to use for the hash table.
     */
    private static int determineHashSize( int expectedSize ) {

        int result = 512;
        while ( result <= expectedSize ) {
            result *= 2;
        }

        return result;

    }

    /**
     * Get the hash table index from the key.
     *
     * @param key the key to be hashed into the table.
     *
     * @return the corresponding hash table index.
     */
    private int getHashIndex( K key ) {
        int result = key.hashCode() % this.entryLists.length;
        if ( result < 0 ) {
            return result + this.entryLists.length;
        }
        return result;
    }

    /** Table of lists of entries in the map indexed by key hash code. */
    private final /*VArray<KeyValue<K, V>>[]*/ Object[] entryLists;

    /** The number of entries in this map. */
    private final VInt size;

    /**
     * One entry in a list of entries by hash code.
     *
     * @param <K>
     * @param <V>
     */
    public static class KeyValue<K, V> {

        public KeyValue( K key, V value ) {
            this.key = key;
            this.value = value;
        }

        public final K key;

        public final V value;
    }

}
