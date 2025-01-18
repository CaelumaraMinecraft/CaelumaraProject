package top.auspice.tasks.priority;

import org.jetbrains.annotations.NotNull;

public class EnumPriority extends NumberedPriority {
    @NotNull
    private final PriorityPhase type;

    public EnumPriority(@NotNull PriorityPhase type) {
        super(type.ordinal());
        this.type = type;
    }

    @NotNull
    public final PriorityPhase getType() {
        return this.type;
    }

    @NotNull
    public String toString() {
        return "EnumPriority(" + this.type + ')';
    }
}