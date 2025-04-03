package net.aurika.auspice.folia.server;

import net.aurika.auspice.platform.scheduler.Task;
import net.aurika.auspice.platform.scheduler.TaskExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class FoliaAsyncTaskExecutor implements TaskExecutor {

  @NotNull
  private final Plugin plugin;

  public FoliaAsyncTaskExecutor(@NotNull Plugin plugin) {
    Objects.requireNonNull(plugin);
    this.plugin = plugin;
  }

  @NotNull
  public Task run(@NotNull Runnable runnable) {
    Objects.requireNonNull(runnable);
    return new FoliaTask(Bukkit.getAsyncScheduler().runNow(this.plugin, (__) -> runnable.run()));
  }

  @NotNull
  public Task runDelayed(@NotNull Runnable runnable, long delay, @Nullable TimeUnit timeUnit) {
    Objects.requireNonNull(runnable);
    long millis = timeUnit != null ? timeUnit.toMillis(delay) : TaskExecutor.ticksToMillis(delay);
    return new FoliaTask(
        Bukkit.getAsyncScheduler().runDelayed(this.plugin, (__) -> runnable.run(), millis, TimeUnit.MILLISECONDS));
  }

  @NotNull
  public Task repeat(@NotNull Runnable runnable, long initialDelay, long repeatInterval, @Nullable TimeUnit timeUnit) {
    Objects.requireNonNull(runnable);
    long initialMillis = timeUnit != null ? timeUnit.toMillis(initialDelay) : TaskExecutor.ticksToMillis(initialDelay);
    long intervalMillis = timeUnit != null ? timeUnit.toMillis(repeatInterval) : TaskExecutor.ticksToMillis(
        repeatInterval);
    return new FoliaTask(
        Bukkit.getAsyncScheduler().runAtFixedRate(
            this.plugin, (__) -> runnable.run(), initialMillis, intervalMillis,
            TimeUnit.MILLISECONDS
        ));
  }

}
