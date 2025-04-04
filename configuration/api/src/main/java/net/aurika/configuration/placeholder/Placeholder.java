package net.aurika.configuration.placeholder;

import net.aurika.text.placeholders.context.PlaceholderProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 占位符对象, 在配置文件中, 每个代表占位符的字符串都应被解析成一个占位符对象.
 */
public interface Placeholder {

  @NotNull String getOriginalString();

  @Nullable String getPointer();

  @Nullable Object request(@NotNull PlaceholderProvider placeholderProvider);

  @NotNull String asString(boolean surround);

  default PlaceholderTranslationException error(@NotNull String message) {
    return new PlaceholderTranslationException(this, message);
  }

}
