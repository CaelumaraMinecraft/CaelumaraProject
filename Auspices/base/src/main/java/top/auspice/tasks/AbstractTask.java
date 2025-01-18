package top.auspice.tasks;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.key.NSedKey;
import top.auspice.tasks.context.TaskContext;
import top.auspice.tasks.priority.Priority;

import java.util.Objects;

public abstract class AbstractTask<C extends TaskContext> implements Task<C> {
    @NotNull
    private final Priority priority;
    @NotNull
    private final NSedKey NSedKey;
    @Nullable
    private final Task<C> parent;

    public AbstractTask(@NotNull Priority priority, @NotNull NSedKey NSedKey, @Nullable Task<C> parent) {
        Objects.requireNonNull(priority);
        Objects.requireNonNull(NSedKey);
        this.priority = priority;
        this.NSedKey = NSedKey;
        this.parent = parent;
        String var10000 = this.priority.validateFor(this);
        if (var10000 != null) {
            throw new IllegalArgumentException(var10000 + " for task " + this);
        }
    }

    @NotNull
    public final Priority getPriority() {
        return this.priority;
    }

    @Nullable
    public final Task<C> getParent() {
        return this.parent;
    }

    public final int hashCode() {
        return this.NSedKey.hashCode();
    }

    public final boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof Task && Intrinsics.areEqual(this.NSedKey, ((Task<?>) other).getNamespacedKey());
        }
    }

    @NotNull
    public final NSedKey getNamespacedKey() {
        return this.NSedKey;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + "(name=" + this.NSedKey.asString() + ", priority=" + this.priority + ", parent=" + this.parent + ')';
    }
}

