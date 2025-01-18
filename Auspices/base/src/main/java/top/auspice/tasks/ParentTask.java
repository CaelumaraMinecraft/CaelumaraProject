package top.auspice.tasks;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.context.TaskContext;

public interface ParentTask<C extends TaskContext> extends Task<C> {
    @NotNull
    TaskRegistry<C, Task<C>> getSubTasks();
}
