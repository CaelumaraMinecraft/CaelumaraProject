package top.auspice.configs.texts.placeholders;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.config.accessor.ClearlyConfigAccessor;
import top.auspice.configs.texts.compiler.container.TextContainer;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.SupportedLocale;
import top.auspice.configs.texts.compiler.TextObject;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
public enum StandardKingdomsPlaceholder implements EnumKingdomsPlaceholderTranslator {

    LANG("", (context) -> {
        return SupportedLocale.CH.getName();
    }),
    IS_SPY(Boolean.FALSE, (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$9())),
    IS_ADMIN(Boolean.FALSE, (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$10())),
    IS_FLYING(Boolean.FALSE, (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$11())),
    IS_PVP(Boolean.FALSE, (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$12())),
    IN_SNEAK_MODE(Boolean.FALSE, (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$13())),
    CHAT_CHANNEL("", (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$15())),
    CHAT_CHANNEL_NAME("", (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$16())),
    CHAT_CHANNEL_SHORT("", (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$17())),
    CHAT_CHANNEL_COLOR("", (Function1) (new StandardKingdomsPlaceholder$special$$inlined$ofPlayer$18())),

    ;
    @NotNull
    private final Object defaultValue;
    @NotNull
    private final Function<KingdomsPlaceholderTranslationContext, Object> translator;
    @Nullable
    private Object configuredDefaultValue;
    @NotNull
    public static final String IDENTIFIER = "kingdoms";
    @NotNull
    public static final String OTHER_IDENTIFIER = "other_";
    @NotNull
    private static final Map<String, TextContainer> GLOBAL_MACROS = new HashMap<>();


    StandardKingdomsPlaceholder(@NotNull Serializable defaultValue, @NotNull Function<KingdomsPlaceholderTranslationContext, Object> translator) {
        this.defaultValue = defaultValue;
        this.translator = translator;
        KingdomsPlaceholderTranslator.register(this);
    }

    @NotNull
    public final Object getDefault() {
        return this.defaultValue;
    }

    @NotNull
    public final Function<KingdomsPlaceholderTranslationContext, Object> getTranslator() {
        return this.translator;
    }

    @Nullable
    public final Object getConfiguredDefaultValue() {
        return this.configuredDefaultValue;
    }

    public final void setConfiguredDefaultValue(@Nullable Object defaultValue) {
        this.configuredDefaultValue = defaultValue;
    }

    public static void init() {
        ClearlyConfigAccessor accessor = AuspiceGlobalConfig.PLACEHOLDERS_DEFAULTS.getSection();
        Iterator<String> var2 = accessor.getKeys().iterator();

        String var3;
        while (var2.hasNext()) {
            var3 = var2.next();
            Objects.requireNonNull(var3);
            KingdomsPlaceholderTranslator var4;
            if ((var4 = KingdomsPlaceholderTranslator.getByName(var3)) != null) {
                var4.setConfiguredDefaultValue(accessor.get(new String[]{var3}).getSection().getSection().getParsedValue());
            }
        }

        var2 = AuspiceGlobalConfig.PLACEHOLDERS_VARIABLES.getSection().getKeys().iterator();

        while (var2.hasNext()) {
            var3 = var2.next();
            TextContainer var6 = TextContainer.parse(AuspiceGlobalConfig.PLACEHOLDERS_VARIABLES.getManager().withProperty(var3));
            Objects.requireNonNull(var3);
            Objects.requireNonNull(var6);
            StandardKingdomsPlaceholder.GLOBAL_MACROS.put(var3, var6);
        }

    }

    @Nullable
    public static TextObject getMacro(@NotNull String var1, @Nullable TextPlaceholderProvider var2) {
        Objects.requireNonNull(var1);
        TextContainer var10000 = StandardKingdomsPlaceholder.GLOBAL_MACROS.get(var1);
        return var10000 == null ? null : var10000.get(var2);
    }

    @Nullable
    public static TextContainer getRawMacro(@NotNull String var0) {
        return StandardKingdomsPlaceholder.GLOBAL_MACROS.get(Objects.requireNonNull(var0));
    }

    @NotNull
    public static Map<String, TextContainer> getGlobalMacros() {
        return Collections.unmodifiableMap(StandardKingdomsPlaceholder.GLOBAL_MACROS);
    }


}
