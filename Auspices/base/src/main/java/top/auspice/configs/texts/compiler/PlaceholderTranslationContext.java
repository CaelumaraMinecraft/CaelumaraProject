package top.auspice.configs.texts.compiler;

import org.jetbrains.annotations.Contract;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.placeholders.transformer.PlaceholderTransformerRegistry;
import top.auspice.configs.texts.messenger.Messenger;
import top.auspice.utils.Checker;

public final class PlaceholderTranslationContext {
    private final Object value;
    private final TextCompilerSettings settings;
    public static final TextCompilerSettings PLACEHOLDER_SETTINGS = new TextCompilerSettings(false, false, true, true, true, null);

    public PlaceholderTranslationContext(Object rawValue, TextCompilerSettings var2) {
        Checker.Argument.checkNotNull(rawValue, "rawValue", "Raw value cannot be null");
        this.value = rawValue;
        this.settings = var2;
    }

    public static PlaceholderTranslationContext withDefaultContext(Object var0) {
        return new PlaceholderTranslationContext(var0, PLACEHOLDER_SETTINGS);
    }

    @Contract("_ -> new")
    public PlaceholderTranslationContext copyTo(Object rawValue) {
        return new PlaceholderTranslationContext(rawValue, this.settings);
    }

    public TextCompilerSettings getSettings() {
        return this.settings;
    }

    public String toString() {
        return "PlaceholderTranslationContext{" + this.value + '}';
    }

    public Object getValue() {
        return unwrapPlaceholder(this.value);
    }

    public static Object unwrapPlaceholder(Object obj) {
        return PlaceholderTransformerRegistry.INSTANCE.applyTransformation(obj);
    }

    public static Object unwrapContextualPlaceholder(Object var0, TextPlaceholderProvider var1) {
        if ((var0 = unwrapPlaceholder(var0)) instanceof Messenger) {
            var0 = ((Messenger) var0).getMessageObject(var1.getLanguage());
        }

        return var0;
    }
}