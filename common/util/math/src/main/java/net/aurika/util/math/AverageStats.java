package net.aurika.util.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AverageStats<K, V extends Number> {

  private final @NotNull Map<K, Avg> data = new ConcurrentHashMap<>();

  public AverageStats() {
  }

  public final void addData(@NotNull K key, @NotNull V data) {
    Objects.requireNonNull(key, "key");
    Objects.requireNonNull(data, "data");
    Map<K, Avg> $this$getOrPut$iv = this.data;
    Object value$iv = $this$getOrPut$iv.get(key);
    Object var10000;
    if (value$iv == null) {
      Avg answer$iv = new Avg(0.0, 0L);
      $this$getOrPut$iv.put(key, answer$iv);
      var10000 = answer$iv;
    } else {
      var10000 = value$iv;
    }

    Avg avg = (Avg) var10000;
    avg.sum(avg.sum() + data.doubleValue());
    long var11 = avg.count();
    avg.count(var11 + 1L);
    if (avg.sum() >= Double.MAX_VALUE) {
      avg.sum(Double.MAX_VALUE / (double) avg.count());
      avg.count(1L);
    }
  }

  @Nullable
  public final V getAverage(@NotNull K key) {
    Objects.requireNonNull(key);
    Avg var10000 = this.data.get(key);
    if (var10000 == null) {
      return null;
    } else {
      return (V) (Number) var10000.average();
    }
  }

}

