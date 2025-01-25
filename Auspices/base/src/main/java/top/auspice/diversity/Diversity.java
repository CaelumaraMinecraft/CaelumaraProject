package top.auspice.diversity;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.path.ConfigEntry;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;
import net.aurika.namespace.NSKeyed;

import java.util.Map;
import java.util.TimeZone;

public interface Diversity extends NSKeyed {

    /**
     * 获取文件夹命名
     *
     * @return 文件夹命名, 命名较为随意, 但必须能够作为文件名, 例如 {@code zh_CN_colorful}, {@code en_US_plain}
     */
    @NotNull String getFolderName();

    /**
     * 获取小写名称
     *
     * @return lowercase name
     */
    @NotNull String getLowerCaseName();

    /**
     * 获取本地名称, "本地" 指地区
     *
     * @return 本地名称, 如 {@code 中国大陆}, {@code English}
     */
    @NotNull String getNativeName();

    @Nullable java.util.Locale getLocal();

    @NotNull TimeZone getTimeZone();

    @Contract("_, true -> !null")
    @Nullable MessageProvider getMessage(@NotNull ConfigEntry path, boolean useDefault);

    Map<ConfigEntry, MessageProvider> getMessages();

    TextObject getVariableRaw(@Nullable String name);

    @Nullable TextObject getVariable(@Nullable TextPlaceholderProvider placeholderProvider, @Nullable String variable, boolean noDefault);

    /**
     * 获取全局的默认语言
     * <p>
     * "全局" 指所有使用 AuspiceAPI 的语言的地方. 当无法明确某对象的 AuspiceAPI 语言时将会使用这个默认值
     *
     * @return 全局默认值语言
     */
    static Diversity getDefault() {
        return StandardDiversity.SIMPLIFIED_CHINESE;
    }

}