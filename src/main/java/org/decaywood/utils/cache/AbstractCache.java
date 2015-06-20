package org.decaywood.utils.cache;

import java.util.Map;

/**
 * @author: Administrator
 * @date: 2015/6/20 20:53.
 */
public abstract class AbstractCache<K, V> implements ICache<K, V> {



    class CacheEntry<Key, Val> {

        CacheEntry(Key key, Val value, long ttl) {
            this.key = key;
            this.cachedObject = value;
            this.ttl = ttl;
            this.lastAccess = System.currentTimeMillis();
        }

        final Key key;
        final Val cachedObject;
        long lastAccess;		// time of last access
        long accessCount;		// number of accesses
        long ttl;				// objects timeout (time-to-live), 0 = no timeout

        boolean isExpired() {
            if (ttl == 0) {
                return false;
            }
            return lastAccess + ttl < System.currentTimeMillis();
        }
        Val getObject() {
            lastAccess = System.currentTimeMillis();
            accessCount++;
            return cachedObject;
        }
    }

    protected Map<K, CacheEntry<K,V>> cacheMap;

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

        CacheEntry<K,V> co = new CacheEntry<K,V>(key, value, timeout);
        if (timeout != 0) {
            existCustomTimeout = true;
        }
        if (isFull()) {
            prune();
        }
        cacheMap.put(key, co);

    }




    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    @Override
    public V get(K key) {
        CacheEntry<K,V> co = cacheMap.get(key);
        if (co == null) {
            missCount++;
            return null;
        }
        if (co.isExpired() == true) {
            // remove(key);		// can't upgrade the lock
            cacheMap.remove(key);

            missCount++;
            return null;
        }

        hitCount++;
        return co.getObject();
    }



    @Override
    public boolean isFull() {
        return cacheMap.size() >= cacheSize;
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    @Override
    public void clearCache() {
        cacheMap.clear();
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected boolean isPruneExpiredActive() {
        return (cacheTimeout != 0) || existCustomTimeout;
    }
}
