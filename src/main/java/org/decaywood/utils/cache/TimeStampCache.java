package org.decaywood.utils.cache;

/**
 * @author: decaywood
 * @date: 2015/6/20 23:16.
 */


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Time stamp cache. Not limited by size, objects are removed only when they are expired.
 * Prune is not invoked explicitly by standard Cache methods, however,
 * it is possible to schedule prunes on fined-rate delays.
 */
public class TimeStampCache<K, V> extends AbstractCache<K, V> {


    public TimeStampCache(long timeout) {

        this.cacheSize = 0;
        this.cacheTimeout = timeout;
        this.cacheMap = new HashMap<>();

    }

    /**
     * Prunes expired elements from the cache. Returns the number of removed objects.
     */
    @Override
    public void prune() {

        this.cacheMap.forEach((key, cacheEntry) -> {
            if (!cacheEntry.isExpired()) return;
            this.cacheMap.remove(key);
        });

    }
}
