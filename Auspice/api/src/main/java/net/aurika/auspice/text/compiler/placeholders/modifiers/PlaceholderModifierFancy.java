package net.aurika.auspice.text.compiler.placeholders.modifiers;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import top.auspice.utils.string.Strings;

public final class PlaceholderModifierFancy implements PlaceholderModifier {
    @NotNull
    public static final PlaceholderModifierFancy INSTANCE = new PlaceholderModifierFancy();
    private PlaceholderModifierFancy() {
    }

    @NotNull
    public String getName() {
        return "fancy";
    }

    @NotNull
    public Object apply(@NotNull Placeholder placeholder, @Nullable Object object) {
        Intrinsics.checkNotNullParameter(placeholder, "");
        if (!(object instanceof Number)) {
            PlaceholderModifier var3 = this;
            throw new PlaceholderModifierException("Unsupported placeholder value for modifier '" + var3.getName() + "' with value (" + (object != null ? object.getClass() : null) + ") " + object + " for placeholder: " + placeholder);
        } else {
            String var10000 = Strings.toFancyNumber(((Number) object).doubleValue());
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            return var10000;
        }
    }

    public boolean isSupported(@NotNull Class<?> type) {
        Intrinsics.checkNotNullParameter(type, "");
        return Number.class.isAssignableFrom(type);
    }

    @NotNull
    public Class<?> getOutputType() {
        return String.class;
    }
}
