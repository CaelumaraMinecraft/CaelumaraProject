package top.auspice.configs.texts.placeholders;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.player.AuspicePlayer;
import top.auspice.utils.Checker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface KingdomsPlaceholderTranslator {
    @Language("RegExp")
    @NotNull String ALLOWED_NAME = "[a-z0-9_]";
    java.util.regex.Pattern NAME_PATTERN = java.util.regex.Pattern.compile(ALLOWED_NAME);

    @Pattern(ALLOWED_NAME)
    @NotNull String getName();

    @NotNull Object getDefault();

    @Nullable Object getConfiguredDefaultValue();

    void setConfiguredDefaultValue(@Nullable Object defaultValue);

    @Nullable Object translate(@NotNull KingdomsPlaceholderTranslationContext context);

    @Nullable Map<String, FunctionalPlaceholder.CompiledFunction> getFunctions();

    static @NotNull Map<String, KingdomsPlaceholderTranslator> names() {
        return Companion.b;
    }

    static void register(@NotNull KingdomsPlaceholderTranslator var1) {
        Objects.requireNonNull(var1);
        String name = var1.getName();
        name = name.toUpperCase(Locale.ENGLISH);
        if (Companion.b.containsKey(name)) {
            IllegalArgumentException var4 = new IllegalArgumentException("Previously registered: " + name);
            throw new IllegalArgumentException(var4.toString());
        } else {
            Companion.b.put(name, var1);
        }
    }

    @Nullable
    static KingdomsPlaceholderTranslator getByName(@NotNull String name) {
        Checker.Argument.checkNotNull(name, "name");
        String var2 = name.toUpperCase(Locale.ENGLISH);
        return Companion.b.get(var2);
    }

    @NotNull
    static Function<KingdomsPlaceholderTranslationContext, Object> ofPlayer(@NotNull Function<? super AuspicePlayer, ?> translator) {
        Checker.Argument.checkNotNull(translator, "translator");

        final class NamelessClass_4 implements Function<KingdomsPlaceholderTranslationContext, Object> {
            public NamelessClass_4() {
                super();
            }

            public static final Map<Object, Object> k = new HashMap<>();
            public Object apply(KingdomsPlaceholderTranslationContext var1x) {
                Objects.requireNonNull(var1x, "");
                AuspicePlayer player = var1x.getPlayer();
                if (player != null) {
                    return  translator.apply(player);
                } else {
                    return null;
                }
            }
        }

        return new NamelessClass_4();
    }


    final class Companion {
        @NotNull
        private static final Map<String, KingdomsPlaceholderTranslator> b = new HashMap<>(50);

    }
}

