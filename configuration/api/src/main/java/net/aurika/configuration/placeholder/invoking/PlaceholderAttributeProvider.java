package net.aurika.configuration.placeholder.invoking;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 实现了这个接口的类可以提供占位符属性
 */
public interface PlaceholderAttributeProvider {

  @Language("RegExp")
  String ALLOWED_ATTRIBUTE_NAME = "[a-zA-Z_]";

  java.util.regex.Pattern ATTRIBUTE_NAME_PATTERN = java.util.regex.Pattern.compile(
      PlaceholderAttributeProvider.ALLOWED_ATTRIBUTE_NAME);

  @Nullable Object providePlaceholderAttribute(@NotNull String attributeName);

}
