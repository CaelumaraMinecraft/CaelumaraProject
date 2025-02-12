package top.auspice.utils.unsafe.map;

import net.aurika.util.array.UnsafeArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

public class IndexedHashMap<K, V> {
    private final Map<K, V> map = new UnsafeHashMap<>();
    private final UnsafeArrayList<K> list;

    public IndexedHashMap(K[] keys) {
        this.list = UnsafeArrayList.withSize(keys);
    }

    public K[] asArray() {
        return this.list.getArray();
    }

    public K[] iterator() {
        return this.list.getArray();
    }

    public V get(K key) {
        return this.map.get(key);
    }

    public K at(int index) {
        return index < this.list.size() ? this.list.getArray()[index] : null;
    }

    public V get(K key, V value) {
        return this.map.getOrDefault(key, value);
    }

    public int size() {
        return this.list.size();
    }

    public void add(K key, V value) {
        this.map.put(key, value);
        this.list.add(key);
    }

    public void set(K[] keys, IntFunction<V> values) {
        this.clear();
        this.list.setArray(keys);

        for (int var3 = 0; var3 < keys.length; ++var3) {
            this.map.put(keys[var3], values.apply(var3));
        }
    }

    public <U> List<U> subList(int from, int to, Function<K, U> keyToSubKey) {
        if (from >= this.list.size()) {
            return new ArrayList<>();
        } else {
            ArrayList<U> var4 = new ArrayList<>(to);
            int var5 = 0;
            int var6 = 0;

            while (var5 - from <= to && var6 < this.list.size()) {
                U var7 = keyToSubKey.apply(this.list.getArray()[var6++]);
                if (var7 != null) {
                    ++var5;
                    if (var5 > from) {
                        var4.add(var7);
                    }
                }
            }

            return var4;
        }
    }

    public void clear() {
        this.map.clear();
        this.list.clear();
    }
}

