package net.aurika.tasks.container;

import net.aurika.namespace.NSKeyMap;
import org.jetbrains.annotations.NotNull;
import net.aurika.tasks.context.TaskContext;

import java.util.Map;

public interface TaskSession {
    @NotNull NSKeyMap<TaskContext> getResults();

    @NotNull Map<Class<? extends LocalTaskSession>, LocalTaskSession> getInstances();
}
