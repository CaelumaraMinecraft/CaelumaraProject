package top.auspice.tasks.container;

import org.jetbrains.annotations.NotNull;
import net.aurika.namespace.NamespacedMap;
import top.auspice.tasks.context.TaskContext;

import java.util.IdentityHashMap;
import java.util.Map;

public class AbstractTaskSession implements TaskSession {
    @NotNull
    private final NamespacedMap<TaskContext> results = new NamespacedMap<>();
    @NotNull
    private final Map<Class<? extends LocalTaskSession>, LocalTaskSession> instances = new IdentityHashMap<>();

    @NotNull
    public NamespacedMap<TaskContext> getResults() {
        return this.results;
    }

    @NotNull
    public Map<Class<? extends LocalTaskSession>, LocalTaskSession> getInstances() {
        return this.instances;
    }
}