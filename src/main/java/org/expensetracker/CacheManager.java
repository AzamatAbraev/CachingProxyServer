package org.expensetracker;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private Map<String, String> cache;

    public CacheManager() {
        this.cache = new HashMap<>();
    }

    public boolean isCached(String url) {
        return cache.containsKey(url);
    }

    public String getFromCache(String url) {
        return cache.get(url);
    }

    public void addToCache(String url, String response) {
        cache.put(url, response);
    }

    public void clearCache() {
        cache.clear();
    }
}
