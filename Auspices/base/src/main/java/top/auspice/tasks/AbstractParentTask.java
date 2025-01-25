package top.auspice.tasks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.namespace.NSedKey;
import top.auspice.tasks.context.TaskContext;
import top.auspice.tasks.priority.Priority;

public abstract class AbstractParentTask<C extends TaskContext> extends AbstractTask<C> implements ParentTask<C> {
    @NotNull
    private final TaskRegistry<C, Task<C>> subTasks;

    public AbstractParentTask(@NotNull Priority priority, @NotNull NSedKey NSedKey, @Nullable Task<C> parent) {
        super(priority, NSedKey, parent);
        this.subTasks = new TaskRegistry<>(this);
    }

    @NotNull
    public TaskRegistry<C, Task<C>> getSubTasks() {
        return this.subTasks;
    }
}
