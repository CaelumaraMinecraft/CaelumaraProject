package net.aurika.auspice.text.compiler.placeholders.functions;

import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import org.jetbrains.annotations.NotNull;

public interface PlaceholderFunctionParameters {

  @NotNull
  PlaceholderFunctionInvoker createInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function);

}
