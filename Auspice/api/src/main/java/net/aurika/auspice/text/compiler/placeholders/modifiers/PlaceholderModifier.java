package net.aurika.auspice.text.compiler.placeholders.modifiers;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.text.compiler.placeholders.Placeholder;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.config.sections.ConfigSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public interface PlaceholderModifier {
    @NotNull String getName();

    @NotNull Object apply(@NotNull Placeholder placeholder, @Nullable Object object);

    boolean isSupported(@NotNull Class<?> type);

    @NotNull Class<?> getOutputType();

    default @NotNull Compatibility compareCompatibilityWith(@NotNull PlaceholderModifier other) {
        Objects.requireNonNull(other, "");
        Class<?> var2 = this.getOutputType();
        Class<?> var3 = other.getOutputType();
        if (Intrinsics.areEqual(var2, var3)) {
            return Compatibility.COMPATIBLE;
        } else {
            boolean var5 = this.isSupported(var3);
            boolean var4 = other.isSupported(var2);
            if (var5 && var4) {
                return Compatibility.COMPATIBLE;
            } else if (var5) {
                return Compatibility.AFTER;
            } else {
                return var4 ? Compatibility.BEFORE : Compatibility.INCOMPATIBLE;
            }
        }
    }

    static @NotNull Map<String, PlaceholderModifier> getRegistered() {
        return Registry.INSTANCE.registered;
    }

    static void register(@NotNull PlaceholderModifier modifier) {
        Registry.INSTANCE.register(modifier);
    }

    @Nullable
    static PlaceholderModifier get(@NotNull String name) {
        return Registry.INSTANCE.get(name);
    }

    static void registerDefaults() {
        Registry.INSTANCE.registerDefaults();
    }

    final class Registry {

        public static final Registry INSTANCE = new Registry();

        @NotNull
        private final Map<String, PlaceholderModifier> registered = new HashMap<>();

        private Registry() {
            this.registerDefaults();
        }

        public void register(@NotNull PlaceholderModifier modifier) {
            Objects.requireNonNull(modifier, "Can not register null modifier");
            String name = modifier.getName();
            String lowerCaseName = name.toLowerCase(Locale.ENGLISH);
            Objects.requireNonNull(lowerCaseName);
            if (registered.put(lowerCaseName, modifier) != null) {
                throw new IllegalArgumentException("Modifier already registered: " + modifier);
            }
        }

        @Nullable
        public PlaceholderModifier get(@NotNull String name) {
            Objects.requireNonNull(name);
            return registered.get(name);
        }

        public void registerDefaults() {

            register(PlaceholderModifierFancy.INSTANCE);
            register(PlaceholderModifierRoman.INSTANCE);
            register(PlaceholderModifierShort.INSTANCE);
            register(PlaceholderModifierDate.INSTANCE);
            register(PlaceholderModifierTime.INSTANCE);
            register(PlaceholderModifierBool.INSTANCE);

            try {
                ConfigSection section = AuspiceGlobalConfig.PLACEHOLDERS_FORMATS.getManager().getSection().noDefault().getSection();
                Objects.requireNonNull(section);

                for (String customModifierName : section.getKeys(false)) {
                    Objects.requireNonNull(customModifierName);
                    register(new PlaceholderModifierCustom(customModifierName));
                }
            } catch (Throwable throwable) {
                AuspiceLogger.error("Failed to parse placeholder custom modifiers (placeholder formats)");
                throwable.printStackTrace();
            }
        }
    }

    enum Compatibility {
        INCOMPATIBLE,   //
        COMPATIBLE,     //
        BEFORE,         //
        AFTER           //
    }
}

