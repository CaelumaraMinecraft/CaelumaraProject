package net.aurika.config.placeholders.target;

import org.jetbrains.annotations.Nullable;

final class AbstractBasePlaceholderTargetProvider implements BasePlaceholderTargetProvider {
    @Nullable
    private final Object primaryTarget;
    @Nullable
    private final Object secondaryTarget;

    public AbstractBasePlaceholderTargetProvider(@Nullable Object primaryTarget, @Nullable Object secondaryTarget) {
        this.primaryTarget = primaryTarget;
        this.secondaryTarget = secondaryTarget;
    }

    @Nullable
    public Object getPrimaryTarget() {
        return this.primaryTarget;
    }

    @Nullable
    public Object getSecondaryTarget() {
        return this.secondaryTarget;
    }
}
