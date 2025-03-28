package net.aurika.util.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public abstract class AbstractDelayedRepeatingTask extends AbstractDelayedTask implements DelayedRepeatingTask {

  private final Duration intervalDelay;

  public @NotNull Duration getIntervalDelay() {
    return this.intervalDelay;
  }

  public AbstractDelayedRepeatingTask(@NotNull Runnable var1, @NotNull Duration var2, @NotNull Duration intervalDelay, @NotNull Task.ExecutionContextType var4) {
    super(var1, var2, var4);
    Objects.requireNonNull(intervalDelay);
    this.intervalDelay = intervalDelay;
  }

}