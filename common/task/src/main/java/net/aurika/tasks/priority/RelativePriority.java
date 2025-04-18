package net.aurika.tasks.priority;

import net.aurika.common.ident.Ident;
import net.aurika.tasks.Task;
import net.aurika.tasks.TaskRegistry;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RelativePriority implements Priority {

  private final @NotNull Type type;
  private final @NotNull Ident targetTaskID;

  public RelativePriority(@NotNull Type type, @NotNull Ident targetTaskID) {
    Validate.Arg.notNull(type, "type");
    Validate.Arg.notNull(targetTaskID, "targetTaskID");
    this.type = type;
    this.targetTaskID = targetTaskID;
  }

  public final @NotNull Type getType() {
    return this.type;
  }

  public final @NotNull Ident targetTaskKey() {
    return this.targetTaskID;
  }

  public @Nullable String validateFor(@NotNull Task<?> task) {
    Validate.Arg.notNull(task, "task");
    return Objects.equals(task.ident(), this.targetTaskID) ? "Circular task priority" : null;
  }

  public int compareTo(@NotNull Task<?> second, @NotNull TaskRegistry<?, ?> registry) {
    Validate.Arg.notNull(second, "second");
    Validate.Arg.notNull(registry, "registry");
    int var10000;
    if (Objects.equals(second.ident(), this.targetTaskID)) {
      var10000 = switch (this.type) {
        case BEFORE -> -1;
        case REPLACE -> 0;
        case AFTER -> 1;
      };
    } else {
      Task<?> registeredTask = registry.getRegistered(this.targetTaskID);
      if (registeredTask != null) {
        int compared = registeredTask.compareTo(second, registry);
        if (compared == Integer.MAX_VALUE) {
          var10000 = Integer.MAX_VALUE;
        } else {
          var10000 = switch (this.type) {
            case BEFORE, AFTER -> Integer.compare(compared, 0);
            case REPLACE -> 0;
          };
        }
      } else {
        var10000 = 0;
      }
    }

    return var10000;
  }

  public @NotNull String toString() {
    return "RelativePriority(" + type + ' ' + targetTaskID.asDataString() + ')';
  }

  public enum Type {
    BEFORE,
    AFTER,
    REPLACE
  }

}