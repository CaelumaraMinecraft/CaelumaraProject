package top.auspice.tasks.priority;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.namespace.NSedKey;
import top.auspice.tasks.Task;
import top.auspice.tasks.TaskRegistry;

import java.util.Objects;

public class RelativePriority implements Priority {
    @NotNull
    private final Type type;
    @NotNull
    private final NSedKey targetTaskNS;

    public RelativePriority(@NotNull Type type, @NotNull NSedKey targetTaskNS) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(targetTaskNS);
        this.type = type;
        this.targetTaskNS = targetTaskNS;
    }

    @NotNull
    public final Type getType() {
        return this.type;
    }

    @NotNull
    public final NSedKey getTargetTaskNS() {
        return this.targetTaskNS;
    }

    @Nullable
    public String validateFor(@NotNull Task<?> task) {
        Objects.requireNonNull(task, "task");
        return Intrinsics.areEqual(task.getNamespacedKey(), this.targetTaskNS) ? "Circular task priority" : null;
    }

    public int compareTo(@NotNull Task<?> second, @NotNull TaskRegistry<?, ?> registry) {
        Objects.requireNonNull(second);
        Objects.requireNonNull(registry);
        int var10000;
        if (Intrinsics.areEqual(second.getNamespacedKey(), this.targetTaskNS)) {
            var10000 = switch (this.type) {
                case BEFORE -> -1;
                case REPLACE -> 0;
                case AFTER -> 1;
            };
        } else {
            Task<?> registeredTask = registry.getRegistered(this.targetTaskNS);
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

    @NotNull
    public String toString() {
        return "RelativePriority(" + this.type + ' ' + this.targetTaskNS.asString() + ')';
    }


    public enum Type {
        BEFORE,
        AFTER,
        REPLACE
    }
}