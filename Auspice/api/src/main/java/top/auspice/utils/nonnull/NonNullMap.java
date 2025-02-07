package top.auspice.utils.nonnull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NonNullMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;

    public NonNullMap(Map<K, V> map) {
        this.map = Objects.requireNonNull(map);
    }

    public NonNullMap(int size) {
        this.map = new HashMap<>(size);
    }

    public NonNullMap() {
        this.map = new HashMap<>();
    }

    public static <K, V> NonNullMap<K, V> of(Map<K, V> map) {
        return map instanceof NonNullMap<K, V> ? (NonNullMap<K, V>) map : new NonNullMap<>(map);
    }

    public static <K, V> NonNullMap<K, V> checked(Map<K, V> map) {
        if (map instanceof NonNullMap) {
            return (NonNullMap<K, V>) map;
        } else {

            for (Entry<K, V> kvEntry : map.entrySet()) {
                assertNonNullKey(kvEntry.getKey());
                assertNonNullValue(kvEntry.getValue());
            }

            return new NonNullMap<>(map);
        }
    }

    public static <K, V> NonNullMap<K, V> copyOf(Map<K, V> map) {
        NonNullMap<K, V> newMap = new NonNullMap<>(map.size());

        for (Entry<K, V> kvEntry : map.entrySet()) {
            assertNonNullKey(kvEntry.getKey());
            assertNonNullValue(kvEntry.getValue());
            newMap.put(kvEntry.getKey(), kvEntry.getValue());
        }

        return newMap;
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    static void assertNonNullKey(Object key) {
        if (key == null) {
            throw new NullPointerException("Cannot contain null keys");
        }
    }

    static void assertNonNullValue(Object value) {
        if (value == null) {
            throw new NullPointerException("Cannot contain null values");
        }
    }

    public boolean containsKey(Object key) {
        assertNonNullKey(key);
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        assertNonNullValue(value);
        return this.map.containsValue(value);
    }

    public V get(Object key) {
        assertNonNullKey(key);
        return this.map.get(key);
    }

    public @Nullable V put(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return this.map.put(key, value);
    }

    public V remove(Object key) {
        assertNonNullKey(key);
        return this.map.remove(key);
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    public void clear() {
        this.map.clear();
    }

    public @NotNull Set<K> keySet() {
        return this.map.keySet();
    }

    public @NotNull Collection<V> values() {
        return this.map.values();
    }

    public @NotNull Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    public V getOrDefault(Object key, V defaultValue) {
        assertNonNullKey(key);
        assertNonNullValue(defaultValue);
        return this.map.getOrDefault(key, defaultValue);
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.map.forEach(action);
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.map.replaceAll(function);
    }

    public @Nullable V putIfAbsent(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return this.map.putIfAbsent(key, value);
    }

    public boolean remove(Object key, Object value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return this.map.remove(key, value);
    }

    public boolean replace(K key, V oldValue, V newValue) {
        assertNonNullKey(key);
        assertNonNullValue(oldValue);
        assertNonNullValue(newValue);
        return this.map.replace(key, oldValue, newValue);
    }

    public @Nullable V replace(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return this.map.replace(key, value);
    }

    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        assertNonNullKey(key);
        return this.map.computeIfAbsent(key, mappingFunction);
    }

    public V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return this.map.computeIfPresent(key, remappingFunction);
    }

    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return this.map.compute(key, remappingFunction);
    }

    public V merge(K key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return this.map.merge(key, value, remappingFunction);
    }
}
