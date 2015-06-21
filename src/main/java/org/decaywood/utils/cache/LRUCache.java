package org.decaywood.utils.cache;

/**
 * @author: decaywood
 * @date: 2015/6/21 21:33.
 */


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU (least recently used) cache.
 *
 *
 * When the cache is full, the least recently used item will be removed.
 * Assumed a item in cache, when it is accessed again,
 * it can be moved back up to the head of the queue;
 *
 */
public class LRUCache<K, V> extends AbstractCache<K, V> {



    public LRUCache(int cacheSize) {
        this(cacheSize, 0);
    }

    /**
     * Creates a new LRU cache.
     * Cache timeout zero is recommended
     */
    public LRUCache(int cacheSize, long cacheTimeout) {
        this.cacheSize = cacheSize;
        this.cacheTimeout = cacheTimeout;
        cache = new LinkedHashMap<K, CacheEntry>(cacheSize + 1, 1.0f, true) {
            /**
             * Returns true if this map should remove its eldest entry.
             * This method is invoked by put and putAll after
             * inserting a new entry into the map.  It provides the implementor
             * with the opportunity to remove the eldest entry each time a new one
             * is added.  This is useful if the map represents a cache: it allows
             * the map to reduce memory consumption by deleting stale entries.
             */
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return isFull();
            }
        };
    }


    @Override
    public void prune() {

        if(!isPruneExpiredActive()) return;

        this.cache.forEach((key, cacheEntry) -> {
            if(!cacheEntry.isExpired()) return;
            this.cache.remove(key);
        });

    }


}
