package net.aurika.auspice.platform.scheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public interface TaskExecutor {

  @NotNull Task run(@NotNull Runnable runnable);

  @NotNull Task runDelayed(@NotNull Runnable runnable, long delay, @Nullable TimeUnit timeUnit);

  @NotNull Task repeat(@NotNull Runnable runnable, long initialDelay, long repeatInterval, @Nullable TimeUnit timeUnit);

  static long ticksToMillis(long ticks) { return ticks / (long) 20 * (long) 1000; }

}
