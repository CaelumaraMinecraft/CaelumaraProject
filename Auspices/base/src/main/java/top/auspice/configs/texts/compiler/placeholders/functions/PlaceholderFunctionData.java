package top.auspice.configs.texts.compiler.placeholders.functions;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.utils.Checker;

public class PlaceholderFunctionData {
    private final @NotNull String functionName;
    private final @NotNull PlaceholderFunctionParameters parameters;

    public PlaceholderFunctionData(@NotNull String functionName, @NotNull PlaceholderFunctionParameters parameters) {
        Checker.Argument.checkNotNull(functionName, "functionName");
        Checker.Argument.checkNotNull(parameters, "parameters");
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
        Checker.Argument.checkNotNull(placeholder, "placeholder");
        return this.parameters.createInvoker(placeholder, this);
    }

    public @NotNull String toString() {
        return this.functionName + '(' + this.parameters + ')';
    }
}
