package top.auspice.diversity;

import net.aurika.namespace.NSedKey;
import net.aurika.utils.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.MessageEntry;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AbstractDiversity implements Diversity {
    protected final @NotNull NSedKey id;
    protected final @NotNull String folderName;
    protected final @NotNull String lowercaseName;
    protected final @NotNull String nativeName;
    protected final @NotNull Locale locale;
    protected final @NotNull TimeZone timeZone;

    public AbstractDiversity(@NotNull NSedKey id) {
        Checker.Arg.notNull(id, "id");
        this.id = id;
    }

    @Override
    public @NotNull NSedKey getNamespacedKey() {
        return this.id;
    }

    @Override
    public @NotNull String getFolderName() {
        return folderName;
    }

    @Override
    public @NotNull String getLowerCaseName() {
        return lowercaseName;
    }

    @Override
    public @NotNull String getNativeName() {
        return nativeName;
    }

    @Override
    public @Nullable Locale getLocal() {
        return locale;
    }

    @Override
    public @NotNull TimeZone getTimeZone() {
        return timeZone;
    }

    @Override
    public @Nullable MessageProvider getMessage(@NotNull MessageEntry path, boolean useDefault) {
        return null;
    }

    @Override
    public Map<MessageEntry, MessageProvider> getMessages() {
        Map<MessageEntry, MessageProvider>
    }

    @Override
    public TextObject getVariableRaw(@Nullable String name) {
        return null;
    }

    @Override
    public @Nullable TextObject getVariable(@Nullable TextPlaceholderProvider context, @Nullable String variable, boolean noDefault) {
        return null;
    }
}
