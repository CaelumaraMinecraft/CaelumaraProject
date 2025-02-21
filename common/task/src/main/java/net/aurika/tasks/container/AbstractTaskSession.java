package net.aurika.tasks.container;

import net.aurika.common.key.namespace.NSKeyMap;
import org.jetbrains.annotations.NotNull;
import net.aurika.tasks.context.TaskContext;

import java.util.IdentityHashMap;
import java.util.Map;

public class AbstractTaskSession implements TaskSession {

    private final @NotNull NSKeyMap<TaskContext> results = new NSKeyMap<>();

    private final @NotNull Map<Class<? extends LocalTaskSession>, LocalTaskSession> instances = new IdentityHashMap<>();

    public @NotNull NSKeyMap<TaskContext> getResults() {
        return this.results;
    }

    public @NotNull Map<Class<? extends LocalTaskSession>, LocalTaskSession> getInstances() {
        return this.instances;
    }
}