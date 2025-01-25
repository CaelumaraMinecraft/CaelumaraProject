package top.auspice.tasks.container;

import org.jetbrains.annotations.NotNull;
import net.aurika.namespace.NamespacedMap;
import top.auspice.tasks.context.TaskContext;

import java.util.Map;

public interface TaskSession {
    @NotNull
    NamespacedMap<TaskContext> getResults();
    @NotNull
    Map<Class<? extends LocalTaskSession>, LocalTaskSession> getInstances();
}
