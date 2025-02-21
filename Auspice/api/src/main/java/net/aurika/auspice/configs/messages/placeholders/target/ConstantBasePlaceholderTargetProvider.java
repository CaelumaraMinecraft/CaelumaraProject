package net.aurika.auspice.configs.messages.placeholders.target;

import org.jetbrains.annotations.Nullable;

final class ConstantBasePlaceholderTargetProvider implements BasePlaceholderTargetProvider {

    private final @Nullable Object primaryTarget;
    private final @Nullable Object secondaryTarget;

    public ConstantBasePlaceholderTargetProvider(@Nullable Object primaryTarget, @Nullable Object secondaryTarget) {
        this.primaryTarget = primaryTarget;
        this.secondaryTarget = secondaryTarget;
    }

    public @Nullable Object primaryTarget() {
        return this.primaryTarget;
    }

    public @Nullable Object secondaryTarget() {
        return this.secondaryTarget;
    }
}
