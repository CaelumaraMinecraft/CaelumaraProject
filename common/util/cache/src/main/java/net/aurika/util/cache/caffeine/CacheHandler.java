package net.aurika.util.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;

public final class CacheHandler {

  private static final ForkJoinPool POOL = new ForkJoinPool();
  private static final Scheduler CACHE_SCHEDULER = Scheduler.forScheduledExecutorService(newScheduler());

  public static @NotNull Caffeine<Object, Object> newBuilder() {
    return Caffeine.newBuilder().executor(POOL);
  }

  @SuppressWarnings("unchecked")
  public static <K, V> @NotNull Caffeine<K, V> typedBuilder() {
    return (Caffeine<K, V>) newBuilder();
  }

  public static ForkJoinPool getPool() {
    return POOL;
  }

  public static Scheduler getCacheScheduler() {
    return CACHE_SCHEDULER;
  }

  public static @NotNull ScheduledExecutorService newScheduler() {
    return Executors.newSingleThreadScheduledExecutor(Executors.defaultThreadFactory());
  }

}
