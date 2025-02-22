package net.aurika.util.enumeration;

import org.checkerframework.checker.nullness.qual.NonNull;
import net.aurika.auspice.utils.Pair;

import java.util.*;

public class QuickEnumMap<K extends Enum<K>, V> implements Map<K, V> {
    private final transient V[] vals;
    private transient int size = 0;
    private final transient K[] universe;

    public QuickEnumMap(K[] universe) {
        this.universe = universe;
        this.vals = (V[]) new Object[universe.length];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean containsValue(Object value) {
        Object[] var2 = this.vals;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Object val = var2[var4];
            if (value.equals(val)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    public V get(Object key) {
        Objects.requireNonNull(key, "QuickEnumMap may not contain null keys");
        return this.vals[((Enum) key).ordinal()];
    }

    public V put(K key, V value) {
        Objects.requireNonNull(key, "QuickEnumMap may not contain null keys");
        Objects.requireNonNull(value, "QuickEnumMap may not contain null values");
        int index = key.ordinal();
        V oldValue = this.vals[index];
        this.vals[index] = value;
        if (oldValue == null) {
            ++this.size;
        }

        return oldValue;
    }

    public V remove(Object key) {
        Objects.requireNonNull(key, "QuickEnumMap may not contain null keys");
        int index = ((Enum) key).ordinal();
        V oldValue = this.vals[index];
        this.vals[index] = null;
        if (oldValue != null) {
            --this.size;
        }

        return oldValue;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        Arrays.fill(this.vals, (Object) null);
        this.size = 0;
    }

    public @NonNull Set<K> keySet() {
        Set<K> keys = new QuickEnumSet(this.universe);

        for (int i = 0; i < this.vals.length; ++i) {
            if (this.vals[i] != null) {
                keys.add(this.universe[i]);
            }
        }

        return Collections.unmodifiableSet(keys);
    }

    public @NonNull Collection<V> values() {
        List<V> values = new ArrayList<>(this.size);

        for (V val : this.vals) {
            if (val != null) {
                values.add(val);
            }
        }

        return Collections.unmodifiableList(values);
    }

    public @NonNull Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet(this.size);

        for (int i = 0; i < this.vals.length; ++i) {
            V val = this.vals[i];
            if (val != null) {
                entries.add(Pair.of(this.universe[i], val));
            }
        }

        return Collections.unmodifiableSet(entries);
    }

    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Cannot check equality between QuickEnumMap");
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Cannot genereate hashcode for QuickEnumMap");
    }
}
