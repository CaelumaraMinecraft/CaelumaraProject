package net.aurika.text.placeholders;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public interface EnumKingdomsPlaceholderTranslator extends KingdomsPlaceholderTranslator {
    @NotNull Function<KingdomsPlaceholderTranslationContext, Object> getTranslator();

    @NotNull String name();

    @SuppressWarnings("PatternValidation")
    @Pattern(ALLOWED_NAME)
    @NotNull
    default String getName() {
        String name = this.name().toLowerCase(Locale.ENGLISH);
        if (!(NAME_PATTERN.matcher(name).matches())) {
            new IllegalArgumentException("The placeholder translator name isn't well: " + name + ", it should matches: " + ALLOWED_NAME).printStackTrace();
        }
        return name;
    }

    @Nullable
    default Object translate(@NotNull KingdomsPlaceholderTranslationContext context) {
        Objects.requireNonNull(context);
        return this.getTranslator().apply(context);
    }

    @Nullable
    default Map<String, FunctionalPlaceholder.CompiledFunction> getFunctions() {
        Function<KingdomsPlaceholderTranslationContext, Object> var1 = this.getTranslator();
        FunctionalPlaceholder var10000 = var1 instanceof FunctionalPlaceholder ? (FunctionalPlaceholder) var1 : null;
        return var10000 != null ? var10000.getFunctions() : null;
    }
}
