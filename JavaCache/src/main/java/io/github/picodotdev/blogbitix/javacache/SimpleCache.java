package io.github.picodotdev.blogbitix.javacache;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleCache<K,V> extends LinkedHashMap<K,V> {

    private int size;

    public SimpleCache() {
        this(100);
    }

    public SimpleCache(int size) {
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > size;
    }
}
