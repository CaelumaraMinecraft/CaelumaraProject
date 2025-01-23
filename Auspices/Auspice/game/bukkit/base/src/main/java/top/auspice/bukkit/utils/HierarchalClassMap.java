package top.auspice.bukkit.utils;

import kotlin.NotImplementedError;
import kotlin.jvm.internal.markers.KMutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.unsafe.reflection.ClassHierarchyWalker;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("OptionalAssignedToNull")
public final class HierarchalClassMap<V> implements Map<Class<?>, Optional<V>>, KMutableMap {
    @NotNull
    private final Map<Class<?>, Optional<V>> original;
    @NotNull
    private final HierarchyWalker hierarchyWalker;

    public HierarchalClassMap(@NotNull Map<Class<?>, Optional<V>> original) {
        Objects.requireNonNull(original);
        this.original = original;
        this.hierarchyWalker = new HierarchyWalker();
    }

    @Nullable
    public V get(@NotNull Class<?> key) {
        Objects.requireNonNull(key, "key");
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
        Objects.requireNonNull(key, "key");
        return this.get((Object) key) != null;
    }

    @NotNull
    public Set<Map.Entry<Class<?>, V>> getEntries() {
        String var1 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var1);
    }

    @NotNull
    public Set<Class<?>> getKeys() {
        String var1 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var1);
    }

    public int getSize() {
        String var1 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var1);
    }

    @NotNull
    public Collection<V> getValues() {
        String var1 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var1);
    }

    public void clear() {
        this.original.clear();
    }

    @NotNull
    public Set<Class<?>> keySet() {
        return this.original.keySet();
    }

    @NotNull
    public Collection<Optional<V>> values() {
        return this.original.values();
    }

    @NotNull
    public Set<Map.Entry<Class<?>, Optional<V>>> entrySet() {
        return this.original.entrySet();
    }

    public int size() {
        return this.original.size();
    }

    public boolean isEmpty() {
        String var1 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var1);
    }

    public boolean containsKey(Object key) {
        return this.original.containsKey(key);
    }

    @Nullable
    public V remove(@NotNull Class<?> key) {
        Objects.requireNonNull(key);
        String var2 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var2);
    }

    public void putAll(@NotNull Map<? extends Class<?>, ? extends Optional<V>> from) {
        Objects.requireNonNull(from, "from");
        for (Map.Entry<? extends Class<?>, ? extends Optional<V>> entry : from.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }

    }

    @Nullable
    public V put(@NotNull Class<?> key, @NotNull V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Optional<V> var10002 = Optional.of(value);
        Optional<V> var3 = this.original.put(key, var10002);
        return var3 != null ? var3.orElse(null) : null;
    }

    @Nullable
    public Optional<V> put(Class<?> key, Optional<V> value) {
        return this.original.put(key, value);
    }

    public Optional<V> remove(Object key) {
        return this.original.remove(key);
    }

    public boolean containsValue(@Nullable Object value) {
        if (value == null) {
            return false;
        } else {
            String var2 = "Not yet implemented";
            throw new NotImplementedError("An operation is not implemented: " + var2);
        }
    }

    public Optional<V> get(Object key) {
        return null;
    }

    public HierarchalClassMap() {
        this(new HashMap<>());
    }

    private final class HierarchyWalker implements Function<Class<?>, Optional<V>> {
        public HierarchyWalker() {
        }

        @Nullable
        public Optional<V> apply(@NotNull Class<?> clazz) {
            Objects.requireNonNull(clazz, "clazz");
            return HierarchalClassMap.this.original.get(clazz);
        }
    }
}
