package net.aurika.config.placeholder.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.placeholder.Placeholder;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;

import java.util.Objects;

public abstract class AbstractPlaceholder implements Placeholder {
    @NotNull
    private final String originalString;
    @Nullable
    private final String pointer;

    public AbstractPlaceholder(@NotNull String originalString, @Nullable String pointer) {
        this.originalString = Objects.requireNonNull(originalString);
        this.pointer = Objects.requireNonNull(pointer);
    }

    @Override
    public abstract @NotNull String asString(boolean surround);

    @NotNull
    public String getOriginalString() {
        return this.originalString;
    }

    @Nullable
    public String getPointer() {
        return this.pointer;
    }

    @Override
    public @Nullable Object request(@NotNull PlaceholderProvider placeholderProvider) {
        return null;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + '{' + this.getOriginalString() + '}';
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(@Nullable Object var1) {
        throw new UnsupportedOperationException();
    }


}
