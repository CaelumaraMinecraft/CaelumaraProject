package net.aurika.utils.number;

import org.jetbrains.annotations.Nullable;

public final class NonFiniteNumberException extends RuntimeException {
    public NonFiniteNumberException(@Nullable String message) {
        super(message);
    }
}
