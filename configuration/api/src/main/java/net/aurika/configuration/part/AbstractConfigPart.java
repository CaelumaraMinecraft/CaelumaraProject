package net.aurika.configuration.part;

import net.aurika.common.lazy.Lazy;
import net.aurika.configuration.part.exception.ConfigPartIsRootException;
import net.aurika.configuration.part.exception.ConfigPartNotAbsolutePathException;
import net.aurika.configuration.part.exception.ConfigPartNotNamedException;
import net.aurika.configuration.path.ConfigEntry;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The default implementation of {@link ConfigPart}.
 */
public class AbstractConfigPart implements ConfigPart {

  // 两个均为 null, 则为 root
  // parent 为 notnull, name 为 null, 则为 sequence element
  // parent 为 notnull, name 为 notnull, 则为 map entry
  protected final @Nullable ConfigPart parent;
  protected final @Nullable String name;
  protected final @NotNull Lazy<@NotNull ConfigEntry> absolutePath;
  protected final @NotNull Lazy<@NotNull ConfigEntry> accessiblePath;

  /**
   * Creates a root config part.
   */
  protected AbstractConfigPart() {
    this((ConfigPart) null, null);
  }

  /**
   * Creates a config sequence element part.
   *
   * @param parentSequence tha parent config sequence
   */
  protected AbstractConfigPart(@NotNull ConfigSequence parentSequence) {
    this(
        Validate.Arg.notNull(parentSequence, "parentSequence"),
        null
    );
  }

  /**
   * Creates a config section subordinate part.
   *
   * @param parentSection the parent config section.
   * @param name          the part name
   */
  protected AbstractConfigPart(@NotNull ConfigSection parentSection, @NotNull String name) {
    this(
        Validate.Arg.notNull((ConfigPart) parentSection, "parentSection"),
        Validate.Arg.notNull(name, "name")
    );
  }

  private AbstractConfigPart(@Nullable ConfigPart parentPart, @Nullable String name) {
    this.parent = parentPart;
    this.name = name;

    this.absolutePath = new Lazy<ConfigEntry>() {
      @Override
      protected ConfigEntry init() {
        if (hasAbsolutePath()) return parent == null ? ConfigEntry.empty() : parent.absolutePath().append(name());
        else throw new ConfigPartNotAbsolutePathException();
      }
    };
    this.accessiblePath = new Lazy<ConfigEntry>() {
      @Override
      protected ConfigEntry init() {
        if (parent == null || AbstractConfigPart.this.name == null) return ConfigEntry.empty();
        else return parent.accessiblePath().append(AbstractConfigPart.this.name);
      }
    };
  }

  @Override
  public boolean hasParent() {
    return parent != null;
  }

  @Override
  public @NotNull ConfigPart parent() throws ConfigPartIsRootException {
    if (parent != null) return parent;
    else throw new ConfigPartIsRootException();
  }

  @Override
  public boolean hasAbsolutePath() {
    return parent == null || (parent.hasAbsolutePath() && name != null);
  }

  @Override
  public @NotNull ConfigEntry absolutePath() throws ConfigPartNotAbsolutePathException {
    return absolutePath.get();
  }

  @Override
  public @NotNull ConfigEntry accessiblePath() {
    return accessiblePath.get();
  }

  @Override
  public boolean isNamed() {
    return name != null;
  }

  @Override
  public @NotNull String name() throws ConfigPartNotNamedException {
    if (name != null) return name;
    else throw new ConfigPartNotNamedException();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public String toString() {
    return super.toString();
  }

}