package top.auspice.tasks.priority;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.Task;
import top.auspice.tasks.TaskRegistry;

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
        Objects.requireNonNull(other);
        Objects.requireNonNull(registry);
        Priority priority = other.getPriority();
        return priority instanceof NumberedPriority ? Intrinsics.compare(this.order, ((NumberedPriority) priority).order) : Integer.MAX_VALUE;
    }

    @NotNull
    public String toString() {
        return "NumberedPriority(" + this.order + ')';
    }
}