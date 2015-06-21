package org.decaywood.utils.cache;

import java.util.Iterator;

/**
 * @author: decaywood
 * @date: 2015/6/20 20:33.
 */
public interface ICache<K, V> {


    int getCacheSize();

    long getCacheTimeout();

    void put(K key, V value);

    /**
     * Adds an value to the cache with specified timeout after which it becomes expired.
     * If cache is full, method prune() is invoked to make room for new value.
     */
    void put(K key, V value, long timeout);

    /**
     * Retrieves an value from the cache. Returns null if value
     * is not longer in cache or if it is expired.
     */
    V get(K key);

    /**
     * Prunes values from cache and returns the number of removed values.
     * Used strategy depends on cache implementation.
     */
    void prune();

    boolean isFull();

    void remove(K key);

    void clearCache();

    int size();

    boolean isEmpty();

    boolean containsKey(K key);
    
}
