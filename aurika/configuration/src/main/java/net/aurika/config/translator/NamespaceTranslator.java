package net.aurika.config.translator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.common.key.namespace.NSedKey;
import net.aurika.common.key.namespace.NSKedRegistry;
import top.auspice.utils.string.Strings;

public class NamespaceTranslator implements Translator<NSedKey> {
    public static final NamespaceTranslator INSTANCE = new NamespaceTranslator();

    protected NamespaceTranslator() {
    }

    public @Nullable NSedKey translate(@Nullable String string) {
        return NSedKey.fromConfigString(string);
    }

    public @Nullable NSedKey translate(@Nullable String string, @Nullable Object... settings) {
        if (Strings.isNullOrEmpty(string)) {
            return null;
        }
        if (settings != null && settings.length > 0 && settings[0] instanceof NSKedRegistry<?> NSKedRegistry) {
            return NSKedRegistry.getRegisteredNamespaceFromConfigString(string);
        }
        return null;
    }

    public @NotNull Class<? super NSedKey> getOutputType() {
        return NSedKey.class;
    }
}
