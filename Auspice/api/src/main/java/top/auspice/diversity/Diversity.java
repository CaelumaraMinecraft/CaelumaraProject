package top.auspice.diversity;

import net.aurika.namespace.NamespacedKeyContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.MessageEntry;
import top.auspice.configs.messages.provider.MessageProvider;
import net.aurika.text.TextObject;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;

import java.util.Map;
import java.util.TimeZone;

public interface Diversity extends NamespacedKeyContainer {

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
     * 获取本地名称
     *
     * @return 本地名称, 如 {@code 中国大陆}, {@code English}
     */
    @NotNull String getNativeName();

    @Nullable java.util.Locale getLocal();

    @NotNull TimeZone getTimeZone();

    /**
     * 获取 {@link MessageEntry} 对应的消息
     *
     * @param path       消息路径
     * @param useDefault 当这个 {@linkplain Diversity} 没有配置对应的消息条目时是否使用默认值
     * @throws IllegalArgumentException 当没有配置对应的消息条目且 useDefault == true 时
     */
    @Contract("_, true -> !null")
    @Nullable MessageProvider getMessage(@NotNull MessageEntry path, boolean useDefault);

    Map<MessageEntry, MessageProvider> getMessages();

    @Deprecated
    TextObject getVariableRaw(@Nullable String name);

    @Deprecated
    @Nullable TextObject getVariable(@Nullable MessagePlaceholderProvider context, @Nullable String variable, boolean noDefault);

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