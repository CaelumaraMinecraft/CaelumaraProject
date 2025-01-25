package top.auspice.configs.texts;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.path.ConfigEntry;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;

import java.util.Map;
import java.util.TimeZone;

@Deprecated
public interface Locale extends MessageGroupingStrategy {  //Message segmentation strategy   mince
    @NotNull
    String getName();

    @NotNull
    String getLowerCaseName();

    @NotNull
    String getNativeName();

    @NotNull
    java.util.Locale getLocal();

    @NotNull
    TimeZone getTimeZone();

    @Contract("_, true -> !null")
    @Nullable
    MessageProvider getMessage(@NotNull ConfigEntry path, boolean useDefault);

    Map<ConfigEntry, MessageProvider> getMessages();


    TextObject getVariableRaw(@Nullable String name);

    @Nullable
    TextObject getVariable(@Nullable TextPlaceholderProvider placeholderProvider, @Nullable String variable, boolean noDefault);

    static Locale getDefault() {
        return SupportedLocale.EN;
    }   //texts

}
