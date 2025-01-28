package top.auspice.configs.texts.compiler.placeholders.functions;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.placeholders.types.KingdomsPlaceholder;
import top.auspice.configs.texts.compiler.placeholders.types.PlaceholderTranslationException;
import net.aurika.utils.Checker;

import java.util.Map;
import java.util.Objects;

public final class PlaceholderNamedFunctionInvoker implements PlaceholderFunctionInvoker {

    private final @NotNull KingdomsPlaceholder placeholder;
    private final @NotNull PlaceholderFunctionData function;
    private final @NotNull Map<String, String> paramValues;

    public PlaceholderNamedFunctionInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function, @NotNull Map<String, String> paramValues) {
        Checker.Arg.notNull(placeholder, "placeholder");
        Checker.Arg.notNull(function, "function");
        Checker.Arg.notNull(paramValues, "paramValues");
        this.placeholder = placeholder;
        this.function = function;
        this.paramValues = paramValues;
    }

    @NotNull
    public KingdomsPlaceholder getPlaceholder() {
        return this.placeholder;
    }

    @NotNull
    public PlaceholderFunctionData getFunction() {
        return this.function;
    }

    public boolean has(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.paramValues.containsKey(var1);
    }

    @NotNull
    public String next(@NotNull String var1) {
        Objects.requireNonNull(var1);
        String var10000 = this.paramValues.get(var1);
        if (var10000 == null) {
            throw new IllegalStateException(("Missing function parameter '" + var1 + '\''));
        } else {
            return var10000;
        }
    }

    public boolean getBool(@NotNull String var1) {
        Objects.requireNonNull(var1);
        Boolean var10000 = StringsKt.toBooleanStrictOrNull(this.next(var1));
        if (var10000 != null) {
            return var10000;
        } else {
            throw new PlaceholderTranslationException(this.getPlaceholder(), "Argument '" + var1 + "' is not a boolean");
        }
    }

    public int getInt(@NotNull String var1) {
        Objects.requireNonNull(var1);
        Integer var10000 = StringsKt.toIntOrNull(this.next(var1));
        if (var10000 != null) {
            return var10000;
        } else {
            throw new PlaceholderTranslationException(this.getPlaceholder(), "Argument '" + var1 + "' is not a number");
        }
    }

    @NotNull
    public String getString(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.next(var1);
    }
}

