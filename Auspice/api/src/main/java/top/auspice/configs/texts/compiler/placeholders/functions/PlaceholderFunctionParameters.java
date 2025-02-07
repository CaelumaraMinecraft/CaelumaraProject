package top.auspice.configs.texts.compiler.placeholders.functions;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.placeholders.types.KingdomsPlaceholder;

public interface PlaceholderFunctionParameters {
    @NotNull
    PlaceholderFunctionInvoker createInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function);
}
