package net.aurika.text.compiler.placeholders.functions;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.compiler.placeholders.types.KingdomsPlaceholder;

import java.util.Objects;

public interface PlaceholderFunctionInvoker {
    boolean has(@NotNull String var1);

    boolean getBool(@NotNull String var1);

    int getInt(@NotNull String var1);

    @NotNull String getString(@NotNull String var1);

    @NotNull PlaceholderFunctionData getFunction();

    @NotNull KingdomsPlaceholder getPlaceholder();

    default boolean isFn(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return StringsKt.equals(this.getFunction().getFunctionName(), var1, true);
    }

    default void invalidArg(@NotNull String var1, @Nullable String var2) {
        Objects.requireNonNull(var1);
        throw this.getPlaceholder().error("Invalid value for parameter '" + var1 + "' with value '" + this.getString(var1) + "' for placeholder function. " + var2);
    }
}

