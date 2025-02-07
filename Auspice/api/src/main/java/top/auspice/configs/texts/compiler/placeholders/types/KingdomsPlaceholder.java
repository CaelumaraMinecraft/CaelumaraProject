package top.auspice.configs.texts.compiler.placeholders.types;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.compiler.placeholders.functions.PlaceholderFunctionData;
import top.auspice.configs.texts.compiler.placeholders.functions.PlaceholderFunctionInvoker;
import top.auspice.configs.texts.compiler.placeholders.modifiers.PlaceholderModifier;
import top.auspice.configs.texts.placeholders.KingdomsPlaceholderTranslationContext;
import top.auspice.configs.texts.placeholders.KingdomsPlaceholderTranslator;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;
import top.auspice.configs.texts.placeholders.target.BasePlaceholderTargetProvider;
import top.auspice.configs.texts.placeholders.target.PlaceholderTargetProvider;

import java.util.Arrays;
import java.util.List;

/**
 * 高级占位符
 * AdvancePlaceholder
 */
public class KingdomsPlaceholder extends AbstractPlaceholder {

    public final @NotNull KingdomsPlaceholderTranslator identifier;
    private final @Nullable PlaceholderFunctionData function;

    public KingdomsPlaceholder(String originalString, @NotNull String pointer, @NotNull List<PlaceholderModifier> modifiers, @NonNull KingdomsPlaceholderTranslator identifier, @Nullable PlaceholderFunctionData function) {
        super(originalString, pointer, modifiers);
        this.identifier = identifier;
        this.function = function;
    }

    public @Nullable PlaceholderFunctionData getFunction() {
        return this.function;
    }

    public @NotNull PlaceholderFunctionInvoker requireFunction(String... names) {
        if (this.function == null) {
            throw this.error("Function '" + Arrays.toString(names) + "' required but none was provided for '" + this.asString(true) + '\'');
        }

        if (Arrays.stream(names).noneMatch((x) -> this.function.getFunctionName().equalsIgnoreCase(x))) {
            throw this.error("Function '" + Arrays.toString(names) + "' required but none was provided for '" + this.asString(true) + '\'');
        }

        return this.function.newSession(this);
    }

    public @Nullable Object request(@NotNull PlaceholderProvider provider) {
        if (!(provider instanceof PlaceholderTargetProvider)) {
            throw this.error("Cannot use kingdoms placeholder on a non-target provider " + provider);
        }

        BasePlaceholderTargetProvider var3 = ((PlaceholderTargetProvider) provider).getTargetProviderFor(this.getPointer());
        Object var4 = this.identifier.translate(new KingdomsPlaceholderTranslationContext(var3, this));
        if (var4 == null && this.getModifiers().isEmpty()) {
            var4 = this.identifier.getDefault();
        }

        if (var4 != null) {
            var4 = wrapWithDefaultContextProvider(var4);
        }

        return var4;
    }

    @NotNull
    public String asString(boolean surround) {
        return this.getCommonString(surround, this.getOriginalString());
    }

    @NotNull
    public String toString() {
        return "KingdomsPlaceholder{originalString='" + this.getOriginalString() + "', pointer=" + this.getPointer() + ", identifier=" + this.identifier + ", modifiers=" + this.getModifiers().stream().map(PlaceholderModifier::getName).toList() + ", function=" + this.function + '}';
    }
}