package net.aurika.util.scheduler;

import net.aurika.util.scheduler.Task.ExecutionContextType;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.Executor;

public class InstantTaskScheduler implements TaskScheduler {

  public static final InstantTaskScheduler INSTANCE = new InstantTaskScheduler();

  protected InstantTaskScheduler() {
  }

  @NotNull
  public ExecutionContextType getExecutionContextType() {
    return ExecutionContextType.SYNC;
  }

  @NotNull
  public Executor getExecutor() {
    throw new UnsupportedOperationException();
  }

  private static void requireZero(Duration duration, String name) {
    if (!duration.isZero()) {
      throw new IllegalArgumentException("Instant task scheduler cannot run task with " + name + " of " + duration);
    }
  }

  public @NotNull Task execute(@NotNull Runnable var1) {
    return new AbstractTask(this.getExecutionContextType(), var1);
  }

  public @NotNull DelayedTask delayed(@NotNull Duration delay, @NotNull Runnable runnable) {
    requireZero(delay, "delay");
    return new AbstractDelayedTask(runnable, delay, this.getExecutionContextType()) {
      public boolean cancel() {
        return super.cancel();
      }
    };
  }

  public @NotNull DelayedRepeatingTask repeating(@NotNull Duration initialDelay, @NotNull Duration intervalDelays, @NotNull Runnable runnable) {
    requireZero(initialDelay, "initialDelay");
    requireZero(intervalDelays, "intervalDelays");
    return new AbstractDelayedRepeatingTask(runnable, initialDelay, intervalDelays, this.getExecutionContextType()) {
      public boolean cancel() {
        return super.cancel();
      }
    };
  }

}

