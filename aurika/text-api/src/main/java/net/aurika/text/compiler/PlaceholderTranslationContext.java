package net.aurika.text.compiler;

import org.jetbrains.annotations.Contract;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import net.aurika.text.placeholders.transformer.PlaceholderTransformerRegistry;
import net.aurika.text.messenger.Messenger;
import net.aurika.util.Checker;

public final class PlaceholderTranslationContext {
    private final Object value;
    private final TextCompilerSettings settings;
    public static final TextCompilerSettings PLACEHOLDER_SETTINGS = new TextCompilerSettings(false, false, true, true, true, null);

    public PlaceholderTranslationContext(Object rawValue, TextCompilerSettings var2) {
        Checker.Arg.notNull(rawValue, "rawValue", "Raw value cannot be null");
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

    public static Object unwrapContextualPlaceholder(Object var0, MessagePlaceholderProvider var1) {
        if ((var0 = unwrapPlaceholder(var0)) instanceof Messenger) {
            var0 = ((Messenger) var0).getMessageObject(var1.getLanguage());
        }

        return var0;
    }
}