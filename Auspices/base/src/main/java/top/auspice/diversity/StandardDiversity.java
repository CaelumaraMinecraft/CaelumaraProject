package top.auspice.diversity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.path.ConfigEntry;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.key.NSedKey;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class StandardDiversity extends AbstractDiversity implements Diversity {

    /**
     * 简体中文
     */
    public static final StandardDiversity SIMPLIFIED_CHINESE = new StandardDiversity(NSedKey.auspice("DEFAULT_SIMPLIFIED_CHINESE"), );

    public static final StandardDiversity TRADITIONAL_CHINESE = new StandardDiversity(NSedKey.auspice("DEFAULT_TRADITIONAL_CHINESE"), )

    /**
     * 默认英语
     */
    public static final StandardDiversity ENGLISH = new StandardDiversity(NSedKey.auspice("DEFAULT_ENGLISH"), );

    protected final @NotNull Path pluginFolder;
    protected final @NotNull String folderName;
    protected final @NotNull String lowercaseName;
    protected final @NotNull String nativeName;
    protected final @Nullable Locale locale;
    protected final @NotNull TimeZone timeZone;

    public StandardDiversity(NSedKey NSedKey,
                             @NotNull Path pluginFolder,
                             @Nullable Locale locale,
                             @NotNull TimeZone timeZone,
                             @NotNull String folderName,
                             @NotNull String lowercaseName,
                             @NotNull String nativeName
    ) {
        super(NSedKey);
        this.pluginFolder = pluginFolder;
        this.folderName = folderName;
        this.lowercaseName = lowercaseName;
        this.nativeName = nativeName;
        this.locale = locale;
        this.timeZone = timeZone;
        DiversityRegistry.INSTANCE.register(this);
    }

    @Override
    public @NotNull String getFolderName() {
        return this.folderName;
    }

    @Override
    public @NotNull String getLowerCaseName() {
        return this.lowercaseName;
    }

    @Override
    public @NotNull String getNativeName() {
        return this.nativeName;
    }

    @Override
    public @Nullable Locale getLocal() {
        return this.locale;
    }

    @Override
    public @NotNull TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override
    public @Nullable MessageProvider getMessage(@NotNull ConfigEntry path, boolean useDefault) {
        return null;
    }

    @Override
    public Map<ConfigEntry, MessageProvider> getMessages() {
        return Map.of();
    }

    @Override
    public TextObject getVariableRaw(@Nullable String name) {
        return null;
    }

    @Override
    public @Nullable TextObject getVariable(@Nullable TextPlaceholderProvider placeholderProvider, @Nullable String variable, boolean noDefault) {
        return null;
    }

    public static void init() {}

}
