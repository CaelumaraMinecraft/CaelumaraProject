package net.aurika.configuration.part;

import net.aurika.configuration.part.annotation.NamedPart;
import net.aurika.configuration.part.exception.ConfigPartAlreadyExistException;
import net.aurika.configuration.part.exception.ConfigPartTypeNotSupportedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigSection extends ConfigPart {

  boolean hasSubPart(@NotNull String name);

  /**
   * Gets the sub config part by the {@code name}. The returned part must have the name.
   *
   * @param name the name
   * @return the sub config part
   */
  @NamedPart
  @Nullable ConfigPart getSubPart(@NotNull String name);

  @NamedPart
  @Nullable ConfigPart removeSubPart(@NotNull String name);

  /**
   * Adds a sub config part.
   *
   * @param name the sub name
   * @param sub  the subordinate
   * @throws ConfigPartAlreadyExistException     when there has a sub that already exist with the {@code name}
   * @throws ConfigPartTypeNotSupportedException when the sub config part type is unsupported
   */
  void addSubPart(@NotNull String name, @NamedPart final @NotNull ConfigPart sub) throws ConfigPartAlreadyExistException, ConfigPartTypeNotSupportedException;

  /**
   * Force add a sub config part.
   *
   * @param name the sub name
   * @param sub  the subordinate
   * @throws ConfigPartTypeNotSupportedException when the sub config part type is unsupported
   */
  void forceAddSubPart(@NotNull String name, @NamedPart final @NotNull ConfigPart sub) throws ConfigPartTypeNotSupportedException;

}
