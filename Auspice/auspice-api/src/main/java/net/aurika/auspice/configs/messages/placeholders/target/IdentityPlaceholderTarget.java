package net.aurika.auspice.configs.messages.placeholders.target;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class IdentityPlaceholderTarget implements PlaceholderTarget {

    private final @NotNull Object identity;

    public IdentityPlaceholderTarget(@NotNull Object identity) {
        Objects.requireNonNull(identity, "identity");
        this.identity = identity;
    }

    @NotNull
    public Object provideTo(@NotNull PlaceholderTargetProvider placeholderTargetProvider) {
        Objects.requireNonNull(placeholderTargetProvider);
        return this.identity;
    }
}
