package net.aurika.tasks.priority;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.tasks.Task;
import net.aurika.tasks.TaskRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NumberedPriority implements Priority {

  private final int order;

  public NumberedPriority(int order) {
    this.order = order;
  }

  public final int getOrder() {
    return this.order;
  }

  public int compareTo(@NotNull Task<?> other, @NotNull TaskRegistry<?, ?> registry) {
    Objects.requireNonNull(other, "other");
    Objects.requireNonNull(registry, "registry");
    Priority priority = other.priority();
    return priority instanceof NumberedPriority ? Intrinsics.compare(
        this.order, ((NumberedPriority) priority).order) : Integer.MAX_VALUE;
  }

  public @NotNull String toString() {
    return "NumberedPriority(" + this.order + ')';
  }

}