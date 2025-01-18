package top.auspice.utils.internal.map;

import top.auspice.utils.internal.arrays.UnsafeArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;

public class IndexedHashMap<K, V> {
    private final Map<K, V> map = new UnsafeHashMap<>();
    private final UnsafeArrayList<K> list;

    public IndexedHashMap(K[] ks) {
        this.list = UnsafeArrayList.withSize(ks);
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
        return index < this.list.size ? this.list.getArray()[index] : null;
    }

    public V get(K key, V value) {
        return this.map.getOrDefault(key, value);
    }

    public int size() {
        return this.list.size;
    }

    public void add(K key, V value) {
        this.map.put(key, value);
        this.list.add(key);
    }

    public void set(K[] var1, IntFunction<V> var2) {
        this.clear();
        this.list.setArray(var1);

        for (int var3 = 0; var3 < var1.length; ++var3) {
            this.map.put(var1[var3], var2.apply(var3));
        }
    }

    public <U> List<U> subList(int from, int to, Function<K, U> var3) {
        if (from >= this.list.size) {
            return new ArrayList<>();
        } else {
            ArrayList<U> var4 = new ArrayList<>(to);
            int var5 = 0;
            int var6 = 0;

            while (var5 - from <= to && var6 < this.list.size) {
                U var7;
                if ((var7 = var3.apply(this.list.getArray()[var6++])) != null) {
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

