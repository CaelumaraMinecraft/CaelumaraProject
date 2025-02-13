package net.aurika.util.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.google.errorprone.annotations.CompatibleWith;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public final class ExpirableMap<K, V> implements Cache<K, V> {

    private final @Nullable ExpirationStrategy defaultExpirationStrategy;
    private final @NotNull Cache<K, ReferencedExpirableObject<V>> cache;

    @Nullable
    public ExpirationStrategy getDefaultExpirationStrategy() {
        return this.defaultExpirationStrategy;
    }

    public ExpirableMap() {
        this((ExpirationStrategy) null);
    }

    public ExpirableMap(@NotNull Caffeine<K, ReferencedExpirableObject<V>> builder) {
        this(builder, null);
    }

    public ExpirableMap(@Nullable ExpirationStrategy defaultExpirationStrategy) {
        this.defaultExpirationStrategy = defaultExpirationStrategy;
        Cache<K, ReferencedExpirableObject<V>> var10001 = CacheHandler.typedBuilder().expireAfter(new ExpirableObjectExpiry<>()).build();
        Objects.requireNonNull(var10001);
        this.cache = var10001;
    }

    public ExpirableMap(@NotNull Caffeine<K, ReferencedExpirableObject<V>> builder, @Nullable ExpirationStrategy defaultExpirationStrategy) {
        Objects.requireNonNull(builder);
        this.defaultExpirationStrategy = defaultExpirationStrategy;
        Cache<K, ReferencedExpirableObject<V>> var10001 = builder.build();
        Objects.requireNonNull(var10001);
        this.cache = var10001;
        if (defaultExpirationStrategy != null) {
            builder.expireAfter(new ExpirableObjectExpiry<>());
        }
    }

    public long estimatedSize() {
        return this.cache.estimatedSize();
    }

    @NotNull
    public CacheStats stats() {
        CacheStats var10000 = this.cache.stats();
        Objects.requireNonNull(var10000);
        return var10000;
    }

    @NotNull
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }

    public void cleanUp() {
        this.cache.cleanUp();
    }

    @NotNull
    public Policy<K, V> policy() {
        throw new UnsupportedOperationException();
    }

    public void invalidate(@CompatibleWith("K") @NotNull K key) {
        Objects.requireNonNull(key, "key");
        this.cache.invalidate(key);
    }

    public void invalidateAll(@NotNull Iterable<? extends K> keys) {
        Objects.requireNonNull(keys, "keys");
        this.cache.invalidateAll(keys);
    }

    public void put(@NotNull K key, @NotNull V value, @NotNull ExpirationStrategy expiry) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(expiry, "expiry");
        this.cache.put(key, new ReferencedExpirableObject<>(value, expiry));
    }

    private ExpirationStrategy getAssertDefaultStrategy() {
        ExpirationStrategy var10000 = this.defaultExpirationStrategy;
        if (var10000 == null) {
            throw new UnsupportedOperationException("No default expiration strategy was set");
        } else {
            return var10000;
        }
    }

    public void put(@NotNull K key, @NotNull V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        this.cache.put(key, new ReferencedExpirableObject<>(value, this.getAssertDefaultStrategy()));
    }

    public @Nullable V getIfPresent(@CompatibleWith("K") @NotNull K key) {
        Objects.requireNonNull(key);
        ReferencedExpirableObject<V> var10000 = cache.getIfPresent(key);
        return var10000 != null ? var10000.getReference() : null;
    }

    public boolean contains(@NotNull K key) {
        Objects.requireNonNull(key);
        return cache.getIfPresent(key) != null;
    }

    @NotNull
    public V get(@NotNull K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(mappingFunction);
        ReferencedExpirableObject<V> var10000 = cache.get(key, (dwa) -> {
            return new ReferencedExpirableObject<>(mappingFunction.apply(key), ExpirableMap.this.getAssertDefaultStrategy());
        });
        Objects.requireNonNull(var10000);
        return var10000.getReference();
    }

    public @NotNull Map<K, V> getAllPresent(@NotNull Iterable<? extends K> keys) {
        Objects.requireNonNull(keys, "keys");
        Map<K, ReferencedExpirableObject<V>> var10000 = cache.getAllPresent(keys);

        Set<Map.Entry<K, ReferencedExpirableObject<V>>> var10 = var10000 != null ? var10000.entrySet() : null;
        Objects.requireNonNull(var10);
        Map<K, V> destination$iv = (new HashMap<>());

        for (Map.Entry<K, ReferencedExpirableObject<V>> it : var10) {
            destination$iv.put(it.getKey(), it.getValue().getReference());
        }

        return destination$iv;
    }

    @NotNull
    public Map<K, V> getAll(@NotNull Iterable<? extends K> keys, @NotNull Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    public void invalidateAll() {
        cache.invalidateAll();
    }
}
