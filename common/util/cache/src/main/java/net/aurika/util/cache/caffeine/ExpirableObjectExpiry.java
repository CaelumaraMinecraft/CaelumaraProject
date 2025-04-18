package net.aurika.util.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public final class ExpirableObjectExpiry<K, V extends ExpirableObject> implements Expiry<K, V> {

  public ExpirableObjectExpiry() {
  }

  public long expireAfterCreate(@NotNull K key, @NotNull V value, long currentTime) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(value, "value");
    return value.expirationStrategy().getExpiryAfterCreate().toNanos();
  }

  public long expireAfterUpdate(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(value, "value");
    Duration var10000 = value.expirationStrategy().getExpiryAfterUpdate();
    return var10000 != null ? var10000.toNanos() : currentDuration;
  }

  public long expireAfterRead(@NotNull K key, @NotNull V value, long currentTime, long currentDuration) {
    Validate.Arg.notNull(key, "key");
    Validate.Arg.notNull(value, "value");
    Duration var10000 = value.expirationStrategy().getExpiryAfterRead();
    return var10000 != null ? var10000.toNanos() : currentDuration;
  }

}
