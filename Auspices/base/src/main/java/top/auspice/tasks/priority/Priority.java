package top.auspice.tasks.priority;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.tasks.Task;
import top.auspice.tasks.TaskRegistry;

public interface Priority {

    int UNSUPPORTED_COMPARABLE = Integer.MAX_VALUE;

    int compareTo(@NotNull Task<?> task, @NotNull TaskRegistry<?, ?> registry);

    @Nullable
    default String validateFor(@NotNull Task<?> task) {
        return null;
    }

}

