package net.aurika.config.part;

import net.aurika.config.part.exception.ConfigPartIsRootException;
import net.aurika.config.part.exception.ConfigPartNotAbsolutePathException;
import net.aurika.config.part.exception.ConfigPartNotNamedException;
import net.aurika.config.path.ConfigEntry;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

/**
 * 配置片段
 * <p>这是一个示例配置文件 (Yaml): </p>
 * <blockquote><pre>
 *   cat:
 *     things:
 *       - eat
 *       - dump
 *     did:
 *       evening: sleep
 *       afternoon: eat
 * </pre></blockquote>
 * 其中,
 */
public interface ConfigPart {

  /**
   * Returns this config part has the parent part.
   *
   * @return whether this part has parent part
   */
  boolean hasParent();

  /**
   * Gets the parent part of this part.
   * <p>It follows the following conventions:</p>
   * <li>When this part is a subordinate of a {@linkplain ConfigSection config section}, returns the config section.</li>
   * <li>When this part is an element of a {@linkplain ConfigSequence config sequence}, returns the config sequence.</li>
   * <li>When this part is the root of a profile, throws {@linkplain ConfigPartIsRootException}.</li>
   *
   * @return the parent
   */
  @NotNull ConfigPart parent() throws ConfigPartIsRootException;

  /**
   * Returns this config part has the absolute path to the root.
   *
   * @return is this config part having the absolute path
   */
  boolean hasAbsolutePath();

  /**
   * Gets the absolute path of this config part.
   *
   * @return the absolute path
   */
  @NotNull ConfigEntry absolutePath() throws ConfigPartNotAbsolutePathException;

  /**
   * 获取配置片段的可访问路径. 当配置片段为一个 {@link ConfigSequence} 的下游配置片段时,
   * 可访问路径为从这个配置数列的根元素开始到这个配置片段的路径.
   *
   * @return 可访问路径
   */
  @NotNull ConfigEntry accessiblePath();

  /**
   * Gets the config part is named. When a config part is a config sequence element, it has no name.
   *
   * @return the config part is named
   */
  boolean isNamed();

  @NotNull String name() throws ConfigPartNotNamedException;

  enum State {
    ROOT,
    SECTION_SUB,
    SEQUENCE_ELEMENT;

    public static @NotNull State state(@NotNull ConfigPart config) {
      Validate.Arg.notNull(config, "config");
      if (!config.hasParent()) {
        return ROOT;
      }
      if (config.hasParent()) {
        if (config.isNamed()) {
          return SECTION_SUB;
        } else {
          return SEQUENCE_ELEMENT;
        }
      }
      throw new IllegalStateException();
    }
  }

}
