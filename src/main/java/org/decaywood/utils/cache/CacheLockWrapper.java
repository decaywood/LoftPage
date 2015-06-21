package org.decaywood.utils.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: decaywood
 * @date: 2015/6/21 20:38.
 */


/**
 *
 * CacheLockWrapper offer the Cache synchronized support
 * Make the Cache thread safe by readLock and writeLock
 *
 */
public class CacheLockWrapper<K, V> implements ICache<K, V> {

    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final Lock readLock = cacheLock.readLock();
    private final Lock writeLock = cacheLock.writeLock();

    private ICache<K, V> cache;

    protected interface CacheFactory<K, V> {
        ICache<K, V> newInstance();
    }

    public CacheLockWrapper(CacheFactory<K, V> factory) {
        this.cache = factory.newInstance();
    }

    @Override
    public int getCacheSize() {
        return this.cache.getCacheSize();
    }

    @Override
    public long getCacheTimeout() {
        return this.cache.getCacheTimeout();
    }

    @Override
    public void put(K key, V value) {
        writeLock.lock();
        try {
            this.cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void put(K key, V value, long timeout) {
        writeLock.lock();
        try {
            this.cache.put(key, value, timeout);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V get(K key) {
        readLock.lock();
        try {
            return this.cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void prune() {
        writeLock.lock();
        try {
            this.cache.prune();
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean isFull() {
        return this.cache.isFull();
    }

    @Override
    public void remove(K key) {
        writeLock.lock();
        try {
            this.cache.remove(key);
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clearCache() {
        writeLock.lock();
        try {
            this.cache.clearCache();
        }
        finally {
            writeLock.unlock();
        }
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }


}
