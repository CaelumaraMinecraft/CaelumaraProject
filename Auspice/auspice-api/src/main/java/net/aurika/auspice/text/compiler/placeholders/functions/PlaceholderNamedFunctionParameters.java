package net.aurika.auspice.text.compiler.placeholders.functions;

import kotlin.collections.CollectionsKt;
import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class PlaceholderNamedFunctionParameters implements PlaceholderFunctionParameters {

  private final @NotNull LinkedHashMap<String, String> parameters;

  public PlaceholderNamedFunctionParameters(@NotNull LinkedHashMap<String, String> var1) {
    Objects.requireNonNull(var1);
    this.parameters = var1;
  }

  public @NotNull LinkedHashMap<String, String> getParameters() {
    return this.parameters;
  }

  public @NotNull PlaceholderFunctionInvoker createInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function) {
    Checker.Arg.notNull(placeholder, "placeholder");
    Checker.Arg.notNull(function, "function");
    return new PlaceholderNamedFunctionInvoker(placeholder, function, this.parameters);
  }

  public @NotNull String toString() {
    Set<Map.Entry<String, String>> var10000 = this.parameters.entrySet();
    return CollectionsKt.joinToString(var10000, ", ", "", "", -1, "", (var1) -> var1.getKey() + '=' + var1.getValue());
  }

}
