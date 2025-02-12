package net.aurika.text.compiler.placeholders.functions;

import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import net.aurika.text.compiler.placeholders.types.KingdomsPlaceholder;
import net.aurika.util.Checker;

import java.util.List;
import java.util.Objects;

public final class PlaceholderPositionalFunctionParameters implements PlaceholderFunctionParameters {

    private final @NotNull List<String> parameters;

    public PlaceholderPositionalFunctionParameters(@NotNull List<String> parameters) {
        Checker.Arg.notNull(parameters, "parameters");
        this.parameters = parameters;
    }


    public @NotNull List<String> getParameters() {
        return this.parameters;
    }


    public @NotNull PlaceholderFunctionInvoker createInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function) {
        Objects.requireNonNull(placeholder);
        Objects.requireNonNull(function);
        return new PlaceholderPositionalFunctionInvoker(placeholder, function, this.parameters);
    }


    public @NotNull String toString() {
        return CollectionsKt.joinToString(this.parameters, ", ", "", "", -1, "", null);
    }
}
