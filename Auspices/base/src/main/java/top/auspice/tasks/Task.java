package top.auspice.tasks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.key.NSKeyed;
import top.auspice.tasks.context.TaskContext;
import top.auspice.tasks.priority.Priority;

import java.util.Objects;

public interface Task<C extends TaskContext> extends NSKeyed {
    @NotNull
    Priority getPriority();

    @Nullable
    Task<C> getParent();

    void run(@NotNull C context);

    int hashCode();

    boolean equals(@Nullable Object obj);

    default int compareTo(@NotNull Task<?> other, @NotNull TaskRegistry<?, ?> registry) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(registry);
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
