package org.decaywood.utils.cache;

import java.util.Map;

/**
 * @author: decaywood
 * @date: 2015/6/20 20:53.
 */
public abstract class AbstractCache<K, V> implements ICache<K, V> {

/*
 * ============================== Internal Entry ====================================
 */

    protected class CacheEntry {

        CacheEntry(V value, long ttl) {
            this.cachedObject = value;
            this.ttl = ttl;
            this.lastAccess = System.currentTimeMillis();
        }

        final V cachedObject;
        long lastAccess;		// time of last access
        long accessCount;		// number of accesses
        long ttl;				// objects timeout (time-to-live), 0 = no timeout

        boolean isExpired() {
            if (ttl == 0) {
                return false;
            }
            return lastAccess + ttl < System.currentTimeMillis();
        }
        V getValue() {
            lastAccess = System.currentTimeMillis();
            accessCount++;
            return cachedObject;
        }
    }

/*
 * ============================== Internal Entry End ====================================
 */
    protected Map<K, CacheEntry> cache;

    protected int cacheSize;      // max cache size, 0 = no limit
    protected long cacheTimeout;     // default timeout, 0 = no timeout
    protected boolean existCustomTimeout;
    protected int hitCount;
    protected int missCount;

    @Override
    public int getCacheSize() {
        return cacheSize;
    }

    @Override
    public long getCacheTimeout() {
        return cacheTimeout;
    }

    @Override
    public void put(K key, V value) {
        put(key, value, cacheTimeout);
    }

    @Override
    public void put(K key, V value, long timeout) {

        CacheEntry co = new CacheEntry(value, timeout);
        if (timeout != 0) {
            existCustomTimeout = true;
        }
        if (isFull()) {
            prune();
        }
        cache.put(key, co);

    }




    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    @Override
    public V get(K key) {
        CacheEntry co = cache.get(key);
        if (co == null) {
            missCount++;
            return null;
        }
        if (co.isExpired() == true) {
            // remove(key);		// can't upgrade the lock
            cache.remove(key);

            missCount++;
            return null;
        }

        hitCount++;
        return co.getValue();
    }



    @Override
    public boolean isFull() {
        return cache.size() >= cacheSize;
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void clearCache() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected boolean isPruneExpiredActive() {
        return (cacheTimeout != 0) || existCustomTimeout;
    }

    @Override
    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }
}
