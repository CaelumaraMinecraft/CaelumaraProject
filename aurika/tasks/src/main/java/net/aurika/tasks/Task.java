package net.aurika.tasks;

import net.aurika.validate.Validate;
import net.aurika.namespace.NamespacedKeyContainer;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Task<C extends TaskContext> extends NamespacedKeyContainer {
    @NotNull Priority getPriority();

    @Nullable Task<C> getParent();

    void run(@NotNull C context);

    int hashCode();

    boolean equals(@Nullable Object obj);

    default int compareTo(@NotNull Task<?> other, @NotNull TaskRegistry<?, ?> registry) {
        Validate.Arg.notNull(other, "other");
        Validate.Arg.notNull(registry, "registry");
        int compared = this.getPriority().compareTo(other, registry);
        if (compared == Integer.MAX_VALUE) {
            compared = other.getPriority().compareTo(this, registry);
            if (compared == Integer.MAX_VALUE) {
                compared = 0;
            } else {
                compared = -compared;
            }
        }

        return compared;
    }
}
