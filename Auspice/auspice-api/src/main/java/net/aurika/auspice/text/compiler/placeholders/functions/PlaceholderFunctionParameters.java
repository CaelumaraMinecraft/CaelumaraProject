package net.aurika.auspice.text.compiler.placeholders.functions;

import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;

public interface PlaceholderFunctionParameters {
    @NotNull
    PlaceholderFunctionInvoker createInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function);
}
