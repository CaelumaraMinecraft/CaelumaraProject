package net.aurika.auspice.utils.cache.single;

import net.aurika.auspice.platform.Platform;
import net.aurika.util.cache.single.CacheableObject;

public class TickedCache<T> implements CacheableObject<T> {

  private T value;
  private final int expirationTicks;
  private int lastUpdateTicks;

  public TickedCache(int expirationTicks) {
    if (expirationTicks <= 0) {
      throw new IllegalArgumentException("Expiration ticks cannot be less than 1: " + expirationTicks);
    } else {
      this.expirationTicks = expirationTicks;
    }
  }

  public boolean hasExpired() {
    return this.value == null || Platform.get().ticks() - this.lastUpdateTicks >= this.expirationTicks;
  }

  public boolean isCached() {
    return !this.hasExpired();
  }

  public void invalidate() {
    this.set(null);
  }

  public T get() {
    if (this.hasExpired()) {
      throw new IllegalStateException("Cannot access expired value: " + this.value);
    } else {
      return this.value;
    }
  }

  public void set(T value) {
    this.value = value;
    this.lastUpdateTicks = Platform.get().ticks();
  }

}
