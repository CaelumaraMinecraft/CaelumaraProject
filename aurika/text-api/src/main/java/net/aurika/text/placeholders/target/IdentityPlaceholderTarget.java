package net.aurika.text.placeholders.target;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class IdentityPlaceholderTarget implements PlaceholderTarget {
    @NotNull
    private final Object identity;

    public IdentityPlaceholderTarget(@NotNull Object identity) {
        Objects.requireNonNull(identity);
        this.identity = identity;
    }

    @NotNull
    public Object provideTo(@NotNull PlaceholderTargetProvider placeholderTargetProvider) {
        Objects.requireNonNull(placeholderTargetProvider);
        return this.identity;
    }
}
