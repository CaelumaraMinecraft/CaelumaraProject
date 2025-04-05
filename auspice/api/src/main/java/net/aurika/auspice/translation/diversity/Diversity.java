package net.aurika.auspice.translation.diversity;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.translation.TranslationEntry;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.common.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.TimeZone;

public interface Diversity extends Keyed {

  @Override
  @NotNull Key key();

  /**
   * Gets the folder name.
   *
   * @return 文件夹命名, 命名较为随意, 但必须能够作为文件名, 例如 {@code zh-CN_colorful}, {@code en-US_plain}
   */
  @NotNull String folderName();

  /**
   * 获取小写名称
   *
   * @return lowercase name
   */
  @NotNull String lowerCaseName();

  /**
   * Get the native, human-readable name.
   *
   * @return The native name, such as {@code 中国大陆}, {@code English}
   */
  @NotNull String nativeName();

  @Nullable java.util.Locale getLocal();

  @NotNull TimeZone getTimeZone();

  /**
   * 获取 {@link TranslationEntry} 对应的消息
   *
   * @param path       消息路径
   * @param useDefault 当这个 {@linkplain Diversity} 没有配置对应的消息条目时是否使用默认值
   * @throws IllegalArgumentException 当没有配置对应的消息条目且 useDefault == true 时
   */
  @Deprecated
  @Contract("_, true -> !null")
  default @Nullable MessageProvider getMessage(@NotNull TranslationEntry path, boolean useDefault) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  default Map<TranslationEntry, MessageProvider> getMessages() {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  default TextObject getVariableRaw(@Nullable String name) {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  default @Nullable TextObject getVariable(@Nullable MessageContextImpl context, @Nullable String variable, boolean noDefault) {
    throw new UnsupportedOperationException();
  }

  /**
   * 获取全局的默认语言.
   * <p>"全局" 指所有使用 AuspiceAPI 的语言的地方. 当无法明确某对象的 AuspiceAPI 语言时将会使用这个默认值.
   *
   * @return 全局默认值语言
   */
  static Diversity globalDefault() {
    return StandardDiversity.SIMPLIFIED_CHINESE;
  }

}