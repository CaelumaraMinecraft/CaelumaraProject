package top.auspice.tasks.context;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.TaskState;
import top.auspice.tasks.container.TaskSession;

public interface TaskContext {
    @NotNull
    TaskState getState();

    void setState(@NotNull TaskState var1);

    @NotNull
    TaskSession getSession();

    @NotNull
    TaskContext createNew();
}
