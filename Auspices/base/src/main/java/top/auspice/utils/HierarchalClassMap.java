package top.auspice.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.internal.reflection.ClassHierarchyWalker;
import top.auspice.utils.nonnull.NonNullMap;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("OptionalAssignedToNull")
public class HierarchalClassMap<V> implements Map<Class<?>, V> {
    @NotNull
    private final Map<Class<?>, Optional<V>> original;
    @NotNull
    private final HierarchalClassMap<V>.HierarchyWalker hierarchyWalker;

    public HierarchalClassMap() {
        this(NonNullMap.of(new IdentityHashMap<>()));
    }

    public HierarchalClassMap(@NotNull Map<Class<?>, Optional<V>> original) {
        Objects.requireNonNull(original);
        this.original = original;
        this.hierarchyWalker = new HierarchyWalker();
    }

    @Nullable
    public V get(@NotNull Class<?> key) {
        Objects.requireNonNull(key);
        Optional<V> found = this.original.get(key);
        if (found == null) {
            found = ClassHierarchyWalker.walk(key, this.hierarchyWalker);
            if (found == null) {
                found = Optional.empty();
            }

            this.original.put(key, found);
        }

        return found.orElse(null);
    }

    public boolean containsKey(@NotNull Class<?> key) {
        Objects.requireNonNull(key);
        return this.get((Object) key) != null;
    }

    @NotNull
    public Set<Map.Entry<Class<?>, V>> getEntries() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @NotNull
    public Set<Class<?>> getKeys() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int getSize() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @NotNull
    public Collection<V> getValues() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void clear() {
        this.original.clear();
    }

    public @NotNull Set<Class<?>> keySet() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public @NotNull Collection<V> values() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public @NotNull Set<Map.Entry<Class<?>, V>> entrySet() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int size() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    public V remove(@NotNull Class<?> key) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void putAll(@NotNull Map<? extends Class<?>, ? extends V> from) {
        Objects.requireNonNull(from);

        for (Map.Entry<? extends Class<?>, ? extends V> entry : from.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }

    }

    @Nullable
    public V put(@NotNull Class<?> key, @NotNull V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        @Nullable Optional<V> var3 = this.original.put(key, Optional.of(value));
        return var3 != null ? var3.orElse(null) : null;
    }

    public V remove(Object key) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean containsValue(@Nullable Object value) {
        if (value == null) {
            return false;
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    public V get(Object key) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final class HierarchyWalker implements Function<Class<?>, Optional<V>> {
        public HierarchyWalker() {
        }

        @Nullable
        public Optional<V> apply(@NotNull Class<?> clazz) {
            Objects.requireNonNull(clazz);
            return HierarchalClassMap.this.original.get(clazz);
        }
    }
}
