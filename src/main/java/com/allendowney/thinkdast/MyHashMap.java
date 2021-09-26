package com.allendowney.thinkdast;

import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @param <K>
 * @param <V>
 * @author downey
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

    // average number of entries per map before we rehash
    protected static final double FACTOR = 1.0;

    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key, value);

        System.out.println("Put " + key + " in " + maps + " size now " + maps.size());

        // check if the number of elements per map exceeds the threshold
        if (size() > maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }

    @Override
    public int size() {
        return super.size();
    }

    /**
     * Doubles the number of maps and rehashes the existing entries.
     */
    protected void rehash() {
        System.out.println("rehash();");
        // 배열 크기를 두배 증가시켜 탐색속도를 O(1)로 만든다.
        List<MyLinearMap<K, V>> oldMaps = maps;
        makeMaps(maps.size() * 2);
        for (MyLinearMap<K, V> map : oldMaps) {
            for (Entry<K, V> e : map.getEntries()) {
                put(e.getKey(), e.getValue());
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(Integer.toString(i), i);
        }
        Integer value = map.get("3");
        System.out.println(value);
    }
}
