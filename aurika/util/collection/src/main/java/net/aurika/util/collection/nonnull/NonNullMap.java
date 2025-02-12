package net.aurika.util.collection.nonnull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * No one uses maps to store null shit. Like what the fuck java.
 * Note: put()'ing a null value with a valid key causes values() to return that null
 */
public final class NonNullMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;

    private NonNullMap(@NotNull Map<K, V> map) {
        Objects.requireNonNull(map, "map");
        this.map = map;
    }

    public NonNullMap(int size) {
        this.map = new HashMap<>(size);
    }

    public NonNullMap() {
        this(new HashMap<>());
    }

    public static <K, V> @NotNull NonNullMap<K, V> of(@NotNull Map<K, V> map) {
        if (map instanceof NonNullMap) return (NonNullMap<K, V>) map;
        return new NonNullMap<>(map);
    }

    public static <K, V> NonNullMap<K, V> ofChecked(@NotNull Map<K, V> map) {
        if (map instanceof NonNullMap) return (NonNullMap<K, V>) map;
        for (Entry<K, V> entry : map.entrySet()) {
            assertNonNullKey(entry.getKey());
            assertNonNullValue(entry.getValue());
        }
        return new NonNullMap<>(map);
    }

    /**
     * Copies the contents of the map if null security is important
     * since the passed instance be modified by the caller.
     */
    public static <K, V> NonNullMap<K, V> copyOf(Map<K, V> map) {
        NonNullMap<K, V> newMap = new NonNullMap<>(map.size());
        for (Entry<K, V> entry : map.entrySet()) {
            assertNonNullKey(entry.getKey());
            assertNonNullValue(entry.getValue());
            newMap.put(entry.getKey(), entry.getValue());
        }
        return newMap;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    static void assertNonNullKey(Object key) {
        if (key == null) throw new NullPointerException("Cannot contain null keys");
    }

    static void assertNonNullValue(Object value) {
        if (value == null) throw new NullPointerException("Cannot contain null values");
    }

    @Override
    public boolean containsKey(Object key) {
        assertNonNullKey(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        assertNonNullValue(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        assertNonNullKey(key);
        return map.get(key);
    }

    @Override
    public @Nullable V put(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        assertNonNullKey(key);
        return map.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public @NotNull Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public @NotNull Collection<V> values() {
        return map.values();
    }

    @Override
    public @NotNull Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        assertNonNullKey(key);
        assertNonNullValue(defaultValue);
        return map.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        map.replaceAll(function);
    }

    @Override
    public @Nullable V putIfAbsent(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return map.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return map.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        assertNonNullKey(key);
        assertNonNullValue(oldValue);
        assertNonNullValue(newValue);
        return map.replace(key, oldValue, newValue);
    }

    @Nullable
    @Override
    public V replace(K key, V value) {
        assertNonNullKey(key);
        assertNonNullValue(value);
        return map.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        assertNonNullKey(key);
        return map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return map.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        assertNonNullKey(key);
        return map.merge(key, value, remappingFunction);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Hashcodes are not supported for this implementation");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '(' + this.map.toString() + ')';
    }
}
