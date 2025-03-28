package net.aurika.util.cache.caffeine;

import java.time.Duration;
import java.util.Objects;

public class ExpirableSet<K> {

  private final ExpirableMap<K, Long> map;
  private final long duration;

  public ExpirableSet(ExpirationStrategy expirationStrategy) {
    Objects.requireNonNull(expirationStrategy, "Expiration etrategies cannot be null");
    this.map = new ExpirableMap<>(expirationStrategy);
    this.duration = this.map.getDefaultExpirationStrategy().getExpiryAfterCreate().toMillis();
  }

  public void add(K key) {
    this.map.put(key, System.currentTimeMillis());
  }

  public Duration getTimeLeft(K key) {
    Long added = (Long) this.map.getIfPresent(key);
    if (added == null) {
      return Duration.ZERO;
    } else {
      long passed = System.currentTimeMillis() - added;
      long left = this.duration - passed;
      return left <= 0L ? Duration.ZERO : Duration.ofMillis(left);
    }
  }

  public void clear() {
    this.map.invalidateAll();
  }

  public boolean contains(K key) {
    return this.map.getIfPresent(key) != null;
  }

  public void remove(K key) {
    this.map.invalidate(key);
  }

  public void cleanUp() {
    this.map.cleanUp();
  }

}

