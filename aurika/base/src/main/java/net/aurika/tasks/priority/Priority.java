package net.aurika.tasks.priority;

import net.aurika.tasks.Task;
import net.aurika.tasks.TaskRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Priority {

    int UNSUPPORTED_COMPARABLE = Integer.MAX_VALUE;

    int compareTo(@NotNull Task<?> task, @NotNull TaskRegistry<?, ?> registry);

    default @Nullable String validateFor(@NotNull Task<?> task) {
        return null;
    }
}

