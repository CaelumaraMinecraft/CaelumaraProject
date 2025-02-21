package net.aurika.tasks.priority;

import org.jetbrains.annotations.NotNull;

public class EnumPriority extends NumberedPriority {

    private final @NotNull PriorityPhase type;

    public EnumPriority(@NotNull PriorityPhase type) {
        super(type.ordinal());
        this.type = type;
    }

    public final @NotNull PriorityPhase getType() {
        return type;
    }

    public @NotNull String toString() {
        return "EnumPriority(" + type + ')';
    }
}