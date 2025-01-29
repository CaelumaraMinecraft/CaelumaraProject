package net.aurika.tasks;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.namespace.NSedKey;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTask<C extends TaskContext> implements Task<C> {

    private final @NotNull Priority priority;
    private final @NotNull NSedKey id;
    private final @Nullable Task<C> parent;

    public AbstractTask(@NotNull Priority priority, @NotNull NSedKey id, @Nullable Task<C> parent) {
        Checker.Arg.notNull(priority, "priority");
        Checker.Arg.notNull(id, "id");
        this.priority = priority;
        this.id = id;
        this.parent = parent;
        String var10000 = this.priority.validateFor(this);
        if (var10000 != null) {
            throw new IllegalArgumentException(var10000 + " for task " + this);
        }
    }

    public final @NotNull Priority getPriority() {
        return this.priority;
    }

    public final @Nullable Task<C> getParent() {
        return this.parent;
    }

    public final int hashCode() {
        return this.id.hashCode();
    }

    public final boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof Task && Intrinsics.areEqual(this.id, ((Task<?>) other).getNamespacedKey());
        }
    }

    public final @NotNull NSedKey getNamespacedKey() {
        return this.id;
    }

    public @NotNull String toString() {
        return this.getClass().getSimpleName() + "(name=" + this.id.asString() + ", priority=" + this.priority + ", parent=" + this.parent + ')';
    }
}

