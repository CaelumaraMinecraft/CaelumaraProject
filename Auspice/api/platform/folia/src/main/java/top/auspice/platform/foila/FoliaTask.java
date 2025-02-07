package top.auspice.platform.foila;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.jetbrains.annotations.NotNull;
import top.auspice.platform.Task;

import java.util.Objects;

public final class FoliaTask implements Task {
    @NotNull
    private final ScheduledTask scheduledTask;

    public FoliaTask(@NotNull ScheduledTask scheduledTask) {
        Objects.requireNonNull(scheduledTask);
        this.scheduledTask = scheduledTask;
    }

    @NotNull
    public Task.CancelledState cancel() {
        return CancelledState.valueOf(this.scheduledTask.cancel().name());
    }

    @NotNull
    public Task.ExecutionState getExecutionState() {
        return ExecutionState.valueOf(this.scheduledTask.getExecutionState().name());
    }
}