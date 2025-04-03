package net.aurika.auspice.folia.server;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.aurika.auspice.platform.scheduler.Task;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FoliaTask implements Task {

  private final @NotNull ScheduledTask scheduledTask;

  public FoliaTask(@NotNull ScheduledTask scheduledTask) {
    Objects.requireNonNull(scheduledTask, "scheduledTask");
    this.scheduledTask = scheduledTask;
  }

  public @NotNull Task.CancelledState cancel() {
    return CancelledState.valueOf(this.scheduledTask.cancel().name());
  }

  public @NotNull Task.ExecutionState getExecutionState() {
    return ExecutionState.valueOf(this.scheduledTask.getExecutionState().name());
  }

}