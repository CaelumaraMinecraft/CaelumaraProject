package net.aurika.text.compiler.placeholders.modifiers;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.text.compiler.builders.MessageObjectBuilder;
import net.aurika.text.compiler.placeholders.Placeholder;
import top.auspice.utils.time.TimeFormatter;

import java.time.Duration;

public final class PlaceholderModifierTime implements PlaceholderModifier {
    @NotNull
    public static final PlaceholderModifierTime INSTANCE = new PlaceholderModifierTime();
    @NotNull
    private static final String a = "time";

    private PlaceholderModifierTime() {
    }

    @NotNull
    public String getName() {
        return a;
    }

    @NotNull
    public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
        Intrinsics.checkNotNullParameter(placeholder, "");
        MessageObjectBuilder var4;
        if (object instanceof Number) {
            var4 = TimeFormatter.of(((Number) object).longValue());
            Intrinsics.checkNotNullExpressionValue(var4, "");
            return var4;
        } else if (object instanceof Duration) {
            var4 = TimeFormatter.of(((Duration) object).toMillis());
            Intrinsics.checkNotNullExpressionValue(var4, "");
            return var4;
        } else {
            PlaceholderModifier var3 = this;
            throw new PlaceholderModifierException("Unsupported placeholder value for modifier '" + var3.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
        }
    }

    public boolean isSupported(@NotNull Class<?> type) {
        Intrinsics.checkNotNullParameter(type, "");
        return Number.class.isAssignableFrom(type) || Duration.class.isAssignableFrom(type);
    }

    @NotNull
    public Class<?> getOutputType() {
        return MessageObjectBuilder.class;
    }
}
