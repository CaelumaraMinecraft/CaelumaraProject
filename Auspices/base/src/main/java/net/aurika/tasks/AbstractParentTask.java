package net.aurika.tasks;

import net.aurika.namespace.NSedKey;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractParentTask<C extends TaskContext> extends AbstractTask<C> implements ParentTask<C> {

    private final @NotNull TaskRegistry<C, Task<C>> subTasks;

    public AbstractParentTask(@NotNull Priority priority, @NotNull NSedKey id, @Nullable Task<C> parent) {
        super(priority, id, parent);
        this.subTasks = new TaskRegistry<>(this);
    }

    public @NotNull TaskRegistry<C, Task<C>> getSubTasks() {
        return subTasks;
    }
}
