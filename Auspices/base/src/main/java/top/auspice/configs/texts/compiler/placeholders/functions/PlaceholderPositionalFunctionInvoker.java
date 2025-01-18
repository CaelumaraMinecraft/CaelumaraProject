package top.auspice.configs.texts.compiler.placeholders.functions;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.placeholders.types.KingdomsPlaceholder;
import top.auspice.utils.Checker;

import java.util.List;

public final class PlaceholderPositionalFunctionInvoker implements PlaceholderFunctionInvoker {

    private final @NotNull KingdomsPlaceholder placeholder;
    private final @NotNull PlaceholderFunctionData function;
    private final @NotNull List<String> c;
    private int d;

    public PlaceholderPositionalFunctionInvoker(@NotNull KingdomsPlaceholder placeholder, @NotNull PlaceholderFunctionData function, @NotNull List<String> var3) {
        Checker.Argument.checkNotNull(placeholder, "placeholder");
        Checker.Argument.checkNotNull(function, "function");
        Intrinsics.checkNotNullParameter(var3, "");
        this.placeholder = placeholder;
        this.function = function;
        this.c = var3;
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
        Intrinsics.checkNotNullParameter(var1, "");
        return this.c.size() >= this.d + 1;
    }

    @NotNull
    public String next(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        if (this.has("")) {
            int var2 = this.d++;
            return this.c.get(var2);
        } else {
            throw new IllegalStateException(("Missing function parameter '" + var1 + '\''));
        }
    }

    public boolean getBool(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Boolean var10000 = StringsKt.toBooleanStrictOrNull(this.next(var1));
        if (var10000 != null) {
            return var10000;
        } else {
            throw this.getPlaceholder().error("Argument '" + var1 + "' is not a boolean");
        }
    }

    public int getInt(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Integer var10000 = StringsKt.toIntOrNull(this.next(var1));
        if (var10000 != null) {
            return var10000;
        } else {
            throw this.getPlaceholder().error("Argument '" + var1 + "' is not a number");
        }
    }

    @NotNull
    public String getString(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        return this.next(var1);
    }
}

