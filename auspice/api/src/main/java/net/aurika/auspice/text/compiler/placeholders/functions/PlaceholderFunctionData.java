package net.aurika.auspice.text.compiler.placeholders.functions;

import net.aurika.auspice.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

public class PlaceholderFunctionData {

  private final @NotNull String functionName;
  private final @NotNull PlaceholderFunctionParameters parameters;

  public PlaceholderFunctionData(@NotNull String functionName, @NotNull PlaceholderFunctionParameters parameters) {
    Checker.Arg.notNull(functionName, "functionName");
    Checker.Arg.notNull(parameters, "parameters");
    this.functionName = functionName;
    this.parameters = parameters;
  }

  public @NotNull String getFunctionName() {
    return this.functionName;
  }

  public @NotNull PlaceholderFunctionParameters getParameters() {
    return this.parameters;
  }

  public @NotNull PlaceholderFunctionInvoker newSession(@NotNull KingdomsPlaceholder placeholder) {
    Checker.Arg.notNull(placeholder, "placeholder");
    return this.parameters.createInvoker(placeholder, this);
  }

  public @NotNull String toString() {
    return this.functionName + '(' + this.parameters + ')';
  }

}
