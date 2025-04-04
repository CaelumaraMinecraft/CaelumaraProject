package net.aurika.config.placeholder.types;

import net.aurika.text.placeholders.KingdomsPlaceholderTranslator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 占位符主体部分:
 * <p>
 * 命名: 允许英文大小写字符, 数字0-9以及下划线
 *
 *
 * <p>
 * .attr-name:: 访问属性
 * <p>
 * ?.attr-name:: 安全访问属性
 * <p>
 * .func-name(params):: 调用方法
 * <p>
 * ?.meth-name(params):: 安全访问方法
 * <p>
 * &#064;func-name(params):: 访问函数
 */
public class AuspicePlaceholder extends AbstractPlaceholder {

  protected final @NonNull KingdomsPlaceholderTranslator identifier;

  public AuspicePlaceholder(@NotNull String originalString, @Nullable String pointer, @NonNull KingdomsPlaceholderTranslator identifier) {
    super(originalString, pointer);
    this.identifier = identifier;
  }

}
