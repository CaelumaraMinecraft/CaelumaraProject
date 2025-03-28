package net.aurika.tasks.container;

import net.aurika.common.key.namespace.NSKeyMap;
import net.aurika.tasks.context.TaskContext;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TaskSession {

  @NotNull NSKeyMap<TaskContext> getResults();

  @NotNull Map<Class<? extends LocalTaskSession>, LocalTaskSession> getInstances();

}
