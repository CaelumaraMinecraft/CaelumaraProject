package net.aurika.auspice.utils.compiler.base;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public interface Expression<VT extends VariableTranslator<?>, OUT> {
    OUT eval(@NotNull VT variableTranslator);

    boolean isDefault();

    public @Nullable Expression<VT, OUT> nullIfDefault();

    public @Nullable String getOriginalString();

    public @NotNull String asString(boolean b);
}
