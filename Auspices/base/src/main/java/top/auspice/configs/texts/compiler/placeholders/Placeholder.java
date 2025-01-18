package top.auspice.configs.texts.compiler.placeholders;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.compiler.PlaceholderTranslationContext;
import top.auspice.configs.texts.compiler.placeholders.modifiers.PlaceholderModifier;
import top.auspice.configs.texts.compiler.placeholders.types.KingdomsPlaceholder;
import top.auspice.configs.texts.compiler.placeholders.types.PlaceholderTranslationException;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Placeholder {
    @NotNull String asString(boolean surround);

    @NotNull String getOriginalString();

    @Nullable String getPointer();

    @NotNull List<PlaceholderModifier> getModifiers();

    @Nullable Object request(@NotNull PlaceholderProvider placeholderProvider);

    default @Nullable Set<PlaceholderModifier> getExpectedModifiers() {
        return null;
    }

    @Nullable
    default Object applyModifiers(@Nullable Object context) {
        Object var2 = context;
        context = context instanceof PlaceholderTranslationContext ? ((PlaceholderTranslationContext)context).getValue() : context;

        for (PlaceholderModifier placeholderModifier : this.getModifiers()) {
            if ((context = placeholderModifier.apply(this, context)) instanceof PlaceholderTranslationContext) {
                var2 = context;
                context = ((PlaceholderTranslationContext) context).getValue();
            }
        }

        if ((context == null || ((context instanceof String ? (String) context : null) != null && (context instanceof String ? (String) context : null).length() == 0)) && this instanceof KingdomsPlaceholder && (context = ((KingdomsPlaceholder)this).identifier.getConfiguredDefaultValue()) == null) {
            context = ((KingdomsPlaceholder)this).identifier.getDefault();
        }

        return var2 instanceof PlaceholderTranslationContext ? ((PlaceholderTranslationContext)var2).copyTo(context) : context;
    }


    default PlaceholderTranslationException error(@NotNull String message) {
        return new PlaceholderTranslationException(this, message);
    }
}

