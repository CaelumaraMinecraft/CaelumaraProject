package top.auspice.utils.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public final class ExpirableObjectExpiry<K, V extends ExpirableObject> implements Expiry<K, V> {
    public ExpirableObjectExpiry() {
    }

    public long expireAfterCreate(@NotNull K key, @NotNull V value, long currentTime) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        return value.getExpirationStrategy().getExpiryAfterCreate().toNanos();
    }

    public long expireAfterUpdate(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        Duration var10000 = value.getExpirationStrategy().getExpiryAfterUpdate();
        return var10000 != null ? var10000.toNanos() : currentDuration;
    }

    public long expireAfterRead(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        Duration var10000 = value.getExpirationStrategy().getExpiryAfterRead();
        return var10000 != null ? var10000.toNanos() : currentDuration;
    }
}