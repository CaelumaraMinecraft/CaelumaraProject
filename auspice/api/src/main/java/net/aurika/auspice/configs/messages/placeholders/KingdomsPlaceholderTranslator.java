package net.aurika.auspice.configs.messages.placeholders;

import net.aurika.auspice.constants.player.AuspicePlayer;
import net.aurika.common.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  static @Nullable KingdomsPlaceholderTranslator getByName(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    String var2 = name.toUpperCase(Locale.ENGLISH);
    return Companion.b.get(var2);
  }

  static @NotNull Function<KingdomsPlaceholderTranslationContext, Object> ofPlayer(@NotNull Function<? super AuspicePlayer, ?> translator) {
    Validate.Arg.notNull(translator, "translator");
    return (context) -> {
      Validate.Arg.notNull(context, "context");
      AuspicePlayer player = context.getPlayer();
      if (player != null) {
        return translator.apply(player);
      } else {
        return null;
      }
    };
  }

  final class Companion {

    @NotNull
    private static final Map<String, KingdomsPlaceholderTranslator> b = new HashMap<>(50);

  }

}

